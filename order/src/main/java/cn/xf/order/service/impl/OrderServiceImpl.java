package cn.xf.order.service.impl;

import cn.xf.common.constant.PayConstant;
import cn.xf.common.exception.NoStockException;
import cn.xf.common.to.mq.OrderTo;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.Query;
import cn.xf.common.utils.R;
import cn.xf.common.vo.MemberResponseVo;
import cn.xf.common.vo.PayVo;
import cn.xf.order.config.OrderConstant;
import cn.xf.order.dao.OrderDao;
import cn.xf.order.entity.OrderEntity;
import cn.xf.order.entity.OrderItemEntity;
import cn.xf.common.enume.OrderStatusEnum;
import cn.xf.order.entity.PaymentInfoEntity;
import cn.xf.order.feign.CartFeignClient;
import cn.xf.order.feign.MemberFeignClient;
import cn.xf.order.feign.ProductFeignClient;
import cn.xf.order.feign.WarehousingFeignClient;
import cn.xf.order.interceptor.OrderInterceptor;
import cn.xf.order.service.OrderItemService;
import cn.xf.order.service.OrderService;
import cn.xf.order.service.PaymentInfoService;
import cn.xf.order.to.OrderCreateTo;
import cn.xf.order.vo.*;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    private ThreadLocal<OrderSubmitVo> confirmVoThreadLocal = new ThreadLocal<>();
    @Autowired
    private ThreadPoolExecutor executor;
    @Autowired
    private MemberFeignClient memberFeignClient;
    @Autowired
    private CartFeignClient cartFeignClient;
    @Autowired
    private WarehousingFeignClient warehousingFeignClient;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 得到订单确认数据
     *
     * @return {@link OrderConfirmVo}
     */
    @Override
    public OrderConfirmVo getOrderConfirmData() throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        // 获取登录拦截器中信息
        MemberResponseVo memberResponseVo = OrderInterceptor.threadLocal.get();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        // 远程查询会员信息
        CompletableFuture<Void> memberAddressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<MemberAddressVo> addressList = memberFeignClient.getMemberReceiveAddress(memberResponseVo.getId());
            confirmVo.setMemberAddressVos(addressList);
        }, executor);

        // 远程查询购物车选中购物项
        CompletableFuture<Void> cartListFuture = CompletableFuture.runAsync(() -> {
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                    // 这里的远程请求不是浏览器发出的，而是服务
                    List<OrderItemVo> cartList = cartFeignClient.getCurrentUserCartListWhichAreChecked();
                    confirmVo.setItems(cartList);
                }, executor)
                // 远程查询库存信息
                .thenRunAsync(() -> {
                    List<Long> collect = confirmVo.getItems().stream().map(item -> item.getSkuId()).collect(Collectors.toList());
                    R feignRequestResult = warehousingFeignClient.getSkuHasStock(collect);
                    List<SkuStockVo> list = feignRequestResult.getData("data", new TypeReference<List<SkuStockVo>>() {
                    });
                    if (list != null && list.size() > 0) {
                        Map<Long, Boolean> map = list.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
                        confirmVo.setStocks(map);
                    }
                });

        // 积分查询
        confirmVo.setIntegration(memberResponseVo.getIntegration());

        // 价格交给实体类计算

        // 订单提交防重令牌,缓存一份，浏览器一份
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(OrderConstant.ORDER_SUBMIT_TOKEN_PREFIX + memberResponseVo.getId(), uuid, 30, TimeUnit.MINUTES);
        confirmVo.setOrderToken(uuid);

        CompletableFuture.allOf(memberAddressFuture, cartListFuture).get();
        return confirmVo;
    }

    @Override
    @Transactional
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo vo) {
        confirmVoThreadLocal.set(vo);
        SubmitOrderResponseVo result = new SubmitOrderResponseVo();
        result.setCode(0);
        // 当前用户登录信息
        MemberResponseVo memberResponseVo = OrderInterceptor.threadLocal.get();
        // 1.验证令牌
        String orderToken = vo.getOrderToken();

        // 获取redis中令牌
        String redisToken = redisTemplate.opsForValue().get(OrderConstant.ORDER_SUBMIT_TOKEN_PREFIX + memberResponseVo.getId());

        // 要保证令牌的原子性，获取到令牌后就删除
        // 原子验证令牌
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Long tokenVerify = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                Arrays.asList(OrderConstant.ORDER_SUBMIT_TOKEN_PREFIX + memberResponseVo.getId()),
                orderToken);
        if (tokenVerify == 0L) {
            // 验证失败
            result.setCode(1);
            return result;
        } else {
            // 验证成功
            // 创建订单
            OrderCreateTo order = createOrder();

            //2、验证价格
            // 应付金额
            BigDecimal payAmount = order.getOrder().getPayAmount();
            // 页面提交金额
            BigDecimal payPrice = vo.getPayPrice();

            if (Math.abs(payAmount.subtract(payPrice).doubleValue()) < 0.01) {
                //金额对比
                // 3、保存订单
                saveOrder(order);

                // 4、库存锁定,只要有异常，回滚订单数据
                // 订单号、所有订单项信息(skuId,skuNum,skuName)
                WareSkuLockVo lockVo = new WareSkuLockVo();
                lockVo.setOrderSn(order.getOrder().getOrderSn());

                //获取出要锁定的商品数据信息
                List<OrderItemVo> orderItemVos = order.getOrderItems().stream().map((item) -> {
                    OrderItemVo orderItemVo = new OrderItemVo();
                    orderItemVo.setSkuId(item.getSkuId());
                    orderItemVo.setCount(item.getSkuQuantity());
                    orderItemVo.setTitle(item.getSkuName());
                    return orderItemVo;
                }).collect(Collectors.toList());
                lockVo.setLocks(orderItemVos);

                // 调用远程锁定库存的方法
                // 出现的问题：扣减库存成功了，但是由于网络原因超时，出现异常，导致订单事务回滚，库存事务不回滚(解决方案：seata)
                // 为了保证高并发，不推荐使用seata，因为是加锁，并行化，提升不了效率,可以发消息给库存服务
                R r = warehousingFeignClient.orderLockStock(lockVo);

                if (r.getCode() == 0) {
                    //锁定成功
                    result.setOrder(order.getOrder());

                    // 订单创建成功，发送消息给MQ
                    rabbitTemplate.convertAndSend("order-exchange", "order.delay.order", order.getOrder());

                    //删除购物车里的数据
//                    redisTemplate.delete(CartConstant.CART_PREFIX + memberResponseVo.getId());
                    return result;
                } else {
                    //锁定失败
                    String msg = (String) r.get("msg");
                    throw new NoStockException(msg);
//                    result.setCode(3);
//                    return result;
                }

            } else {
                result.setCode(2);
                return result;
            }
        }

        /*if (StringUtils.isNotEmpty(orderToken) && StringUtils.isNotEmpty(redisToken) && orderToken.equals(redisToken)) {
            // 令牌通过
            // 删除令牌
        }else{

        }*/
    }

    /**
     * 根据订单号查询订单状态
     *
     * @param orderSn 订单号
     * @return {@link OrderEntity}
     */
    @Override
    public OrderEntity getOrderInfoBySn(String orderSn) {
        return this.getOne(new QueryWrapper<OrderEntity>().eq("order_sn", orderSn));
    }

    /**
     * RabbitMq订单延时队列消息过期时关闭订单方法
     *
     * @param orderEntity 订单实体
     */
    @Override
    public void closeOrder(OrderEntity orderEntity) {
        OrderEntity info = this.getById(orderEntity.getId());
        // 如果订单状态为待付款，可以关单
        if (OrderStatusEnum.CREATE_NEW.getCode().equals(orderEntity.getStatus())) {
            OrderEntity updateInfo = new OrderEntity();
            updateInfo.setId(info.getId());
            updateInfo.setStatus(OrderStatusEnum.CANCLED.getCode());
            this.updateById(updateInfo);
            // 属性对拷，传递到仓库模块
            OrderTo order = new OrderTo();
            BeanUtils.copyProperties(info, order);
            // 再将释放成功的消息发送给库存消息队列
            rabbitTemplate.convertAndSend("order-exchange", "order.release.other", order);
        }
    }

    /**
     * 获取订单支付信息
     *
     * @param orderSn 订单sn
     * @return {@link PayVo}
     */
    @Override
    public PayVo getOrderPay(String orderSn) {

        PayVo payVo = new PayVo();
        OrderEntity orderInfo = this.getOrderInfoBySn(orderSn);

        //保留两位小数点，向上取值
        // 支付总额
        BigDecimal payAmount = orderInfo.getPayAmount().setScale(2, BigDecimal.ROUND_UP);
        payVo.setTotal_amount(payAmount.toString());
        // 对外交易号
        payVo.setOut_trade_no(orderInfo.getOrderSn());

        //查询订单项的数据
        List<OrderItemEntity> orderItemInfo = orderItemService.list(
                new QueryWrapper<OrderItemEntity>().eq("order_sn", orderSn));
        OrderItemEntity orderItemEntity = orderItemInfo.get(0);
        payVo.setBody(orderItemEntity.getSkuAttrsVals());

        payVo.setSubject(orderItemEntity.getSkuName());

        return payVo;
    }

    @Override
    public PageUtils queryPageWithItem(Map<String, Object> params) {

        MemberResponseVo memberResponseVo = OrderInterceptor.threadLocal.get();

        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
                        .eq("member_id", memberResponseVo.getId()).orderByDesc("create_time")
        );

        //遍历所有订单集合
        List<OrderEntity> orderEntityList = page.getRecords().stream().map(order -> {
            //根据订单号查询订单项里的数据
            List<OrderItemEntity> orderItemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>()
                    .eq("order_sn", order.getOrderSn()));
            order.setOrderItemEntityList(orderItemEntities);
            return order;
        }).collect(Collectors.toList());

        page.setRecords(orderEntityList);

        return new PageUtils(page);
    }

    /**
     * 处理支付宝的支付结果
     *
     * @param asyncVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String handlePayResult(PayAsyncVo asyncVo) {

        //保存交易流水信息
        PaymentInfoEntity paymentInfo = new PaymentInfoEntity();
        paymentInfo.setOrderSn(asyncVo.getOut_trade_no());
        paymentInfo.setAlipayTradeNo(asyncVo.getTrade_no());
        paymentInfo.setTotalAmount(new BigDecimal(asyncVo.getBuyer_pay_amount()));
        paymentInfo.setSubject(asyncVo.getBody());
        paymentInfo.setPaymentStatus(asyncVo.getTrade_status());
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setCallbackTime(asyncVo.getNotify_time());
        //添加到数据库中
        this.paymentInfoService.save(paymentInfo);

        //修改订单状态
        //获取当前状态
        String tradeStatus = asyncVo.getTrade_status();

        if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
            //获取订单号 支付成功状态
            String orderSn = asyncVo.getOut_trade_no();
            this.updateOrderStatus(orderSn, OrderStatusEnum.PAYED.getCode(), PayConstant.ALIPAY);
        }

        return "success";
    }

    @Override
    public String asyncNotify(String notifyData) {
        return null;
    }

    /**
     * 修改订单状态
     *
     * @param orderSn
     * @param code
     */
    private void updateOrderStatus(String orderSn, Integer code, Integer payType) {
        baseMapper.updateOrderStatus(orderSn,code,payType);
    }

    /**
     * 创建订单
     *
     * @return {@link OrderCreateTo}
     */
    private OrderCreateTo createOrder() {

        OrderCreateTo createTo = new OrderCreateTo();

        //1、生成订单号
        String orderSn = IdWorker.getTimeId();
        OrderEntity orderEntity = builderOrder(orderSn);

        //2、获取到所有的订单项
        List<OrderItemEntity> orderItemEntities = builderOrderItems(orderSn);

        //3、验价(计算价格、积分等信息)
        computePrice(orderEntity, orderItemEntities);

        createTo.setOrder(orderEntity);
        createTo.setOrderItems(orderItemEntities);

        return createTo;
    }

    /**
     * 构建订单数据
     *
     * @param orderSn
     * @return
     */
    private OrderEntity builderOrder(String orderSn) {

        //获取当前用户登录信息
        MemberResponseVo memberResponseVo = OrderInterceptor.threadLocal.get();

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setMemberId(memberResponseVo.getId());
        orderEntity.setOrderSn(orderSn);
        orderEntity.setMemberUsername(memberResponseVo.getUsername());

        OrderSubmitVo orderSubmitVo = confirmVoThreadLocal.get();

        //远程获取收货地址和运费信息
        R fareAddressVo = warehousingFeignClient.getFare(orderSubmitVo.getAddrId());
        FareVo fareResp = fareAddressVo.getData("data", new TypeReference<FareVo>() {
        });

        //获取到运费信息
        BigDecimal fare = fareResp.getFare();
        orderEntity.setFreightAmount(fare);

        //获取到收货地址信息
        MemberAddressVo address = fareResp.getAddress();
        //设置收货人信息
        orderEntity.setReceiverName(address.getName());
        orderEntity.setReceiverPhone(address.getPhone());
        orderEntity.setReceiverPostCode(address.getPostCode());
        orderEntity.setReceiverProvince(address.getProvince());
        orderEntity.setReceiverCity(address.getCity());
        orderEntity.setReceiverRegion(address.getRegion());
        orderEntity.setReceiverDetailAddress(address.getDetailAddress());

        //设置订单相关的状态信息
        orderEntity.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        orderEntity.setAutoConfirmDay(7);
        orderEntity.setConfirmStatus(0);
        return orderEntity;
    }

    /**
     * 构建所有订单项数据
     *
     * @return
     */
    public List<OrderItemEntity> builderOrderItems(String orderSn) {

        List<OrderItemEntity> orderItemEntityList = new ArrayList<>();

        //最后确定每个购物项的价格
        List<OrderItemVo> currentCartItems = cartFeignClient.getCurrentUserCartListWhichAreChecked();
        if (currentCartItems != null && currentCartItems.size() > 0) {
            orderItemEntityList = currentCartItems.stream().map((items) -> {
                //构建订单项数据
                OrderItemEntity orderItemEntity = builderOrderItem(items);
                orderItemEntity.setOrderSn(orderSn);

                return orderItemEntity;
            }).collect(Collectors.toList());
        }

        return orderItemEntityList;
    }

    /**
     * 构建某一个订单项的数据
     *
     * @param items
     * @return
     */
    private OrderItemEntity builderOrderItem(OrderItemVo items) {

        OrderItemEntity orderItemEntity = new OrderItemEntity();

        //1、商品的spu信息
        Long skuId = items.getSkuId();
        //获取spu的信息
        R spuInfo = productFeignClient.getSpuInfoBySkuId(skuId);
        SpuInfoVo spuInfoData = spuInfo.getData("data", new TypeReference<SpuInfoVo>() {
        });
        orderItemEntity.setSpuId(spuInfoData.getId());
        orderItemEntity.setSpuName(spuInfoData.getSpuName());
        orderItemEntity.setSpuBrand(spuInfoData.getBrandName());
        orderItemEntity.setCategoryId(spuInfoData.getCatalogId());

        //2、商品的sku信息
        orderItemEntity.setSkuId(skuId);
        orderItemEntity.setSkuName(items.getTitle());
        orderItemEntity.setSkuPic(items.getImage());
        orderItemEntity.setSkuPrice(items.getPrice());
        orderItemEntity.setSkuQuantity(items.getCount());

        //使用StringUtils.collectionToDelimitedString将list集合转换为String
        String skuAttrValues = StringUtils.collectionToDelimitedString(items.getSkuAttrValues(), ";");
        orderItemEntity.setSkuAttrsVals(skuAttrValues);

        //3、商品的优惠信息

        //4、商品的积分信息
        orderItemEntity.setGiftGrowth(items.getPrice().multiply(new BigDecimal(items.getCount())).intValue());
        orderItemEntity.setGiftIntegration(items.getPrice().multiply(new BigDecimal(items.getCount())).intValue());

        //5、订单项的价格信息
        orderItemEntity.setPromotionAmount(BigDecimal.ZERO);
        orderItemEntity.setCouponAmount(BigDecimal.ZERO);
        orderItemEntity.setIntegrationAmount(BigDecimal.ZERO);

        //当前订单项的实际金额.总额 - 各种优惠价格
        //原来的价格
        BigDecimal origin = orderItemEntity.getSkuPrice().multiply(new BigDecimal(orderItemEntity.getSkuQuantity().toString()));
        //原价减去优惠价得到最终的价格
        BigDecimal subtract = origin.subtract(orderItemEntity.getCouponAmount())
                .subtract(orderItemEntity.getPromotionAmount())
                .subtract(orderItemEntity.getIntegrationAmount());
        orderItemEntity.setRealAmount(subtract);

        return orderItemEntity;
    }

    /**
     * 计算价格的方法
     *
     * @param orderEntity
     * @param orderItemEntities
     */
    private void computePrice(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities) {

        //总价
        BigDecimal total = BigDecimal.ZERO;
        //优惠价
        BigDecimal coupon = BigDecimal.ZERO;
        BigDecimal intergration = BigDecimal.ZERO;
        BigDecimal promotion = BigDecimal.ZERO;

        //积分、成长值
        Integer integrationTotal = 0;
        Integer growthTotal = 0;

        //订单总额，叠加每一个订单项的总额信息
        for (OrderItemEntity orderItem : orderItemEntities) {
            //优惠价格信息
            coupon = coupon.add(orderItem.getCouponAmount());
            promotion = promotion.add(orderItem.getPromotionAmount());
            intergration = intergration.add(orderItem.getIntegrationAmount());

            //总价
            total = total.add(orderItem.getRealAmount());

            //积分信息和成长值信息
            integrationTotal += orderItem.getGiftIntegration();
            growthTotal += orderItem.getGiftGrowth();

        }
        //1、订单价格相关的
        orderEntity.setTotalAmount(total);
        //设置应付总额(总额+运费)
        orderEntity.setPayAmount(total.add(orderEntity.getFreightAmount()));
        orderEntity.setCouponAmount(coupon);
        orderEntity.setPromotionAmount(promotion);
        orderEntity.setIntegrationAmount(intergration);

        //设置积分成长值信息
        orderEntity.setIntegration(integrationTotal);
        orderEntity.setGrowth(growthTotal);

        //设置删除状态(0-未删除，1-已删除)
        orderEntity.setDeleteStatus(0);

    }

    /**
     * 保存订单所有数据
     *
     * @param orderCreateTo
     */
    private void saveOrder(OrderCreateTo orderCreateTo) {

        //获取订单信息
        OrderEntity order = orderCreateTo.getOrder();
        order.setModifyTime(new Date());
        order.setCreateTime(new Date());
        //保存订单
        baseMapper.insert(order);

        //获取订单项信息
        List<OrderItemEntity> orderItems = orderCreateTo.getOrderItems();
        //批量保存订单项数据
        orderItemService.saveBatch(orderItems);
    }

}