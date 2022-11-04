package cn.xf.warehousing.service.impl;

import cn.xf.common.enume.OrderStatusEnum;
import cn.xf.common.exception.NoStockException;
import cn.xf.common.to.SkuHasStockVo;
import cn.xf.common.to.mq.OrderTo;
import cn.xf.common.to.mq.StockDetailTo;
import cn.xf.common.to.mq.StockLockedTo;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.Query;
import cn.xf.common.utils.R;
import cn.xf.warehousing.dao.WareSkuDao;
import cn.xf.warehousing.entity.WareOrderTaskDetailEntity;
import cn.xf.warehousing.entity.WareOrderTaskEntity;
import cn.xf.warehousing.entity.WareSkuEntity;
import cn.xf.warehousing.feign.OrderFeignService;
import cn.xf.warehousing.feign.ProductFeignService;
import cn.xf.warehousing.service.WareOrderTaskDetailService;
import cn.xf.warehousing.service.WareOrderTaskService;
import cn.xf.warehousing.service.WareSkuService;
import cn.xf.warehousing.vo.OrderItemVo;
import cn.xf.warehousing.vo.OrderVo;
import cn.xf.warehousing.vo.SkuWareHasStock;
import cn.xf.warehousing.vo.WareSkuLockVo;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("wareSkuService")
@Slf4j
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private WareSkuDao wareSkuDao;
    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private OrderFeignService orderFeignService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private WareOrderTaskDetailService wareOrderTaskDetailService;

    @Autowired
    private WareOrderTaskService wareOrderTaskService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();

        String skuId = (String) params.get("skuId");
        if (!StringUtils.isEmpty(skuId) && !"0".equalsIgnoreCase(skuId)) {
            queryWrapper.eq("sku_id", skuId);
        }

        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(wareId) && !"0".equalsIgnoreCase(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        //1、判读如果没有这个库存记录新增
        List<WareSkuEntity> wareSkuEntities = wareSkuDao.selectList(
                new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));

        if (wareSkuEntities == null || wareSkuEntities.size() == 0) {
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStockLocked(0);
            //TODO 远程查询sku的名字，如果失败整个事务无需回滚
            try {
                R info = productFeignService.info(skuId);
                Map<String, Object> data = (Map<String, Object>) info.get("skuInfo");
                if (info.getCode() == 0) {
                    wareSkuEntity.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception e) {

            }
            //添加库存信息
            wareSkuDao.insert(wareSkuEntity);
        } else {
            //修改库存信息
            wareSkuDao.addStock(skuId, wareId, skuNum);
        }

    }

    @Override
    public List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds) {

        List<SkuHasStockVo> skuHasStockVos = skuIds.stream().map(item -> {
            // 检索总库存量
            Long count = baseMapper.getSkuStock(item);
            SkuHasStockVo skuHasStockVo = new SkuHasStockVo();
            skuHasStockVo.setSkuId(item);
            skuHasStockVo.setHasStock(count == null ? false : count > 0);
            return skuHasStockVo;
        }).collect(Collectors.toList());
        return skuHasStockVos;
    }


    /**
     * 为某个订单锁定库存
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean orderLockStock(WareSkuLockVo vo) {

        /**
         * 保存库存工作单详情信息
         * 追溯库存信息
         */
         WareOrderTaskEntity wareOrderTaskEntity = new WareOrderTaskEntity();
        wareOrderTaskEntity.setOrderSn(vo.getOrderSn());
        wareOrderTaskEntity.setCreateTime(new Date());
        wareOrderTaskService.save(wareOrderTaskEntity);


        //1、按照下单的收货地址，找到一个就近仓库，锁定库存
        //2、找到每个商品在哪个仓库都有库存
        List<OrderItemVo> locks = vo.getLocks();

        List<SkuWareHasStock> collect = locks.stream().map((item) -> {
            SkuWareHasStock stock = new SkuWareHasStock();
            Long skuId = item.getSkuId();
            stock.setSkuId(skuId);
            stock.setNum(item.getCount());
            // 查询这个商品在哪个仓库有库存
            List<Long> wareIdList = wareSkuDao.listWareIdHasSkuStock(skuId);
            stock.setWareId(wareIdList);

            return stock;
        }).collect(Collectors.toList());

        //2、锁定库存
        for (SkuWareHasStock hasStock : collect) {
            boolean skuStocked = false;
            Long skuId = hasStock.getSkuId();
            List<Long> wareIds = hasStock.getWareId();

            if (StringUtils.isEmpty(wareIds)) {
                //没有任何仓库有这个商品的库存
                throw new NoStockException(skuId);
            }

            //1、如果每一个商品都锁定成功,将当前商品锁定了几件的工作单记录发给MQ
            //2、锁定失败。前面保存的工作单信息都回滚了。发送出去的消息，即使要解锁库存，由于在数据库查不到指定的id，所有就不用解锁
            for (Long wareId : wareIds) {
                //锁定成功就返回1，失败就返回0
                Long count = wareSkuDao.lockSkuStock(skuId, wareId, hasStock.getNum());
                if (count == 1) {
                    skuStocked = true;

                    // 库存锁定成功，告知MQ锁定成功，保存库存工作单，保存库存工作单详情
                    WareOrderTaskDetailEntity taskDetailEntity = WareOrderTaskDetailEntity.builder()
                            .skuId(skuId)
                            .skuName("")
                            .skuNum(hasStock.getNum())
                            .taskId(wareOrderTaskEntity.getId())
                            .wareId(wareId)
                            .lockStatus(1)
                            .build();
                    wareOrderTaskDetailService.save(taskDetailEntity);

                    // TODO 告诉MQ库存锁定成功
                    StockLockedTo lockedTo = new StockLockedTo();
                    lockedTo.setId(wareOrderTaskEntity.getId());
                    // 库存详情
                    StockDetailTo detailTo = new StockDetailTo();
                    BeanUtils.copyProperties(taskDetailEntity, detailTo);

                    lockedTo.setDetailTo(detailTo);
                    log.info("库存锁定成功,发送消息到延时队列中：" + lockedTo.toString());
                    rabbitTemplate.convertAndSend("stock-exchange", "stock.locked", lockedTo);
                    break;
                } else {
                    //当前仓库锁失败，重试下一个仓库
                }
            }

            if (skuStocked == false) {
                //当前商品所有仓库都没有锁住
                throw new NoStockException(skuId);
            }
        }

        //3、肯定全部都是锁定成功的
        return true;
    }

    /**
     * RabbitMq监听的方法
     * 演示队列中消息到达时间后，将消息释放并解锁
     *
     * @param to 来
     */
    @Override
    public void releaseAndUnlock(StockLockedTo to) {
        System.out.println("接收到解锁库存消息");
        // 获取库存信息
        // 库存工作单id
        Long id = to.getId();
        // 库存详情
        StockDetailTo detail = to.getDetailTo();
        Long taskDetailId = detail.getId();
        // 根据库存锁定详细信息查看
        WareOrderTaskDetailEntity info = wareOrderTaskDetailService.getById(taskDetailId);
        // 查询锁定库存有信息，解锁
        if (info != null) {
            // 查看订单情况，如果没有这个订单，必须解锁；有这个订单，查看订单状态，已取消，解锁库存、没取消，不能解锁
            WareOrderTaskEntity orderTask = wareOrderTaskService.getById(id);
            // 获取订单号
            String orderSn = orderTask.getOrderSn();
            // 根据订单号查询订单状态
            // 这里在远程请求时，openfeign返回了一个登录页，因为订单服务中有一个拦截器，所有请求需要登录
            R data = orderFeignService.getOrderStatus(orderSn);
            if (data.getCode() == 0) {
                // 订单返回成功
                OrderVo orderData = data.getData("data", new TypeReference<OrderVo>() {
                });
                // 订单取消了，解锁库存
                if (orderData == null || OrderStatusEnum.CANCLED.getCode().equals(orderData.getStatus())) {
                    // 只有工作单状态为1，才能解锁
                    if (detail.getLockStatus() == 1) {
                        unLockStock(detail.getSkuId(), detail.getWareId(), detail.getSkuNum(), taskDetailId);
                    }
                }
            } else {
                // 远程服务调用失败
                throw new RuntimeException("远程服务失败");
            }
        } else {
            // 不需要解锁
        }
    }

    /**
     * 根据订单释放来释放库存信息
     * -- 防止订单服务卡顿，导致订单状态无法改变，仓库消息到期，查询订单状态，新建状态就不处理
     * -- 如果导致订单卡顿，会让仓库一直不能解锁
     *
     * @param order 订单信息
     */
    @Override
    public void releaseAndUnlock(OrderTo order) {
        // 订单释放后将最新的订单信息发送到仓库模块
        // 根据订单号查询库存工作单
        WareOrderTaskEntity taskEntity = wareOrderTaskService.getByOrderSn(order.getOrderSn());
        // 根据仓库工作单号查询未解锁的库存
        List<WareOrderTaskDetailEntity> list = wareOrderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetailEntity>()
                .eq("task_id", taskEntity.getId()).eq("lock_status", 1));
        // 遍历关单
        for (WareOrderTaskDetailEntity item : list) {
            unLockStock(item.getSkuId(),item.getWareId(),item.getSkuNum(), item.getTaskId());
        }
    }

    /**
     * 解锁库存
     *
     * @param skuId        商品id
     * @param wareId       仓库id
     * @param num          锁定数量
     * @param taskDetailId 库存工作单id
     */
    public void unLockStock(Long skuId, Long wareId, Integer num, Long taskDetailId) {
        // UPDATE wms_ware_sku SET stock_locked=stock_locked-#{num} where sku_id = #{skuId} AND ware_id = #{wareId}
        wareSkuDao.unLockStock(skuId, wareId, num);
        // 库存解锁后，将库存工作单状态改为已解锁（哪个仓库的哪个商品）
        WareOrderTaskDetailEntity entity = new WareOrderTaskDetailEntity();
        entity.setId(taskDetailId);
        entity.setLockStatus(2);
        wareOrderTaskDetailService.updateById(entity);
    }
}