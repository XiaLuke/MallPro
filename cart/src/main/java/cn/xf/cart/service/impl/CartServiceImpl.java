package cn.xf.cart.service.impl;

import cn.xf.cart.feign.ProductFeign;
import cn.xf.cart.interceptor.CartInterceptor;
import cn.xf.cart.service.CartService;
import cn.xf.cart.to.UserInfoTo;
import cn.xf.cart.vo.CartItemVo;
import cn.xf.cart.vo.CartVo;
import cn.xf.cart.vo.SkuInfoVo;
import cn.xf.common.utils.R;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ProductFeign productFeign;

    @Autowired
    ThreadPoolExecutor poolExecutor;

    private final String CART_PREFIX = "mall:cart";

    @Override
    public CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {


        // 1.判断使用离线购物车还是线上购物车
        BoundHashOperations<String, Object, Object> cartOps = getCart();
        //判断Redis是否有该商品的信息
        String productRedisValue = (String) cartOps.get(skuId.toString());
        if (StringUtils.isEmpty(productRedisValue)) {
            // 添加到购物车中
            CartItemVo cartItemVo = new CartItemVo();


            // task1：远程获取商品信息
            CompletableFuture<Void> skuInfoTask = CompletableFuture.runAsync(() -> {
                // 2.购物车添加商品，需要商品详细信息，远程查询
                R skuInfo = productFeign.getSkuInfo(skuId);
                SkuInfoVo data = skuInfo.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                });
                cartItemVo.setSkuId(data.getSkuId());
                cartItemVo.setTitle(data.getSkuTitle());
                cartItemVo.setImage(data.getSkuDefaultImg());
                cartItemVo.setPrice(data.getPrice());
                cartItemVo.setCount(num);
            }, poolExecutor);

            // task2，远程获取商品销售组合信息
            CompletableFuture<Void> saleAttrTask = CompletableFuture.runAsync(() -> {
                // 3.远程查询商品组合信息
                List<String> concatList = productFeign.getSaleAttributeConcatList(skuId);
                cartItemVo.setSkuAttrValues(concatList);
            }, poolExecutor);

            //等待所有的异步任务全部完成
            CompletableFuture.allOf(skuInfoTask, saleAttrTask).get();
            String cartItemJson = JSON.toJSONString(cartItemVo);
            cartOps.put(skuId.toString(), cartItemJson);
            return cartItemVo;
        } else {
            //购物车有此商品，修改数量即可
            CartItemVo cartItemVo = JSON.parseObject(productRedisValue, CartItemVo.class);
            cartItemVo.setCount(cartItemVo.getCount() + num);
            //修改redis的数据
            String cartItemJson = JSON.toJSONString(cartItemVo);
            cartOps.put(skuId.toString(), cartItemJson);

            return cartItemVo;
        }

    }

    /**
     * 查询购物车中数据
     *
     * @param skuId sku id
     * @return {@link CartItemVo}
     */
    @Override
    public CartItemVo getCartItem(Long skuId) {
        //拿到要操作的购物车信息
        BoundHashOperations<String, Object, Object> cartOps = getCart();

        String redisValue = (String) cartOps.get(skuId.toString());

        CartItemVo cartItemVo = JSON.parseObject(redisValue, CartItemVo.class);

        return cartItemVo;
    }

    @Override
    public CartVo integrationCart() throws ExecutionException, InterruptedException {
        CartVo cartVo = new CartVo();
        // 1.判断用户是否登录了
        // 通过HttpServletRequest中是否存在cookie判断用户是否登录
        UserInfoTo userInfo = CartInterceptor.threadLocal.get();
        if (userInfo.getUserId() != null) {
            // 2.登录了将离线购物车也整合到线上购物车中
            String onlineKey = CART_PREFIX + userInfo.getUserId();
            String withLineKey = CART_PREFIX + userInfo.getUserKey();
            // 获取离线购物车中数据，将数据添加在线购物车中
            List<CartItemVo> withLineCartItems = getProductFromCart(withLineKey);
            if (withLineCartItems != null) {
                for (CartItemVo item : withLineCartItems) {
                    addToCart(item.getSkuId(), item.getCount());
                }
                // 添加完后清空临时购物车
                clearTempCart(withLineKey);
            }

            List<CartItemVo> onLineCartItems = getProductFromCart(onlineKey);
            cartVo.setItems(onLineCartItems);

        } else {
            // 3.没登陆继续使用离线购物车
            String cartKey = CART_PREFIX + userInfo.getUserKey();
            // 获取临时购物车中数据
            List<CartItemVo> cartItemVos = getProductFromCart(cartKey);
            cartVo.setItems(cartItemVos);
        }
        return cartVo;
    }

    @Override
    public void clearTempCart(String withLineKey) {
        redisTemplate.delete(withLineKey);
    }

    @Override
    public void checkItem(Long skuId, Integer checked) {
        // 查询当前商品
        CartItemVo cartItem = getCartItem(skuId);
        // 修改商品状态
        cartItem.setCheck(checked == 1 ? true : false);
        // 序列化到redis
        String redisValue = JSON.toJSONString(cartItem);
        BoundHashOperations<String, Object, Object> cartOps = getCart();
        cartOps.put(skuId.toString(), redisValue);
    }

    /**
     * 获取购物车
     *
     * @return {@link BoundHashOperations}<{@link String}, {@link Object}, {@link Object}>
     */
    private BoundHashOperations<String, Object, Object> getCart() {
        UserInfoTo userInfo = CartInterceptor.threadLocal.get();
        String cartKey = "";
        if (userInfo.getUserId() == null) {
            cartKey = CART_PREFIX + userInfo.getUserKey();
        } else {
            cartKey = CART_PREFIX + userInfo.getUserId();
        }
        BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(cartKey);
        return boundHashOperations;
    }

    /**
     * 修改购物项数量
     *
     * @param skuId
     * @param num
     */
    @Override
    public void changeItemCount(Long skuId, Integer num) {

        //查询购物车里面的商品
        CartItemVo cartItem = getCartItem(skuId);
        cartItem.setCount(num);

        BoundHashOperations<String, Object, Object> cartOps = getCart();
        //序列化存入redis中
        String redisValue = JSON.toJSONString(cartItem);
        cartOps.put(skuId.toString(), redisValue);
    }


    /**
     * 删除购物项
     *
     * @param skuId
     */
    @Override
    public void deleteIdCartInfo(Integer skuId) {

        BoundHashOperations<String, Object, Object> cartOps = getCart();
        cartOps.delete(skuId.toString());
    }

    /**
     * 获取当前用户购物车中选中的物品信息
     *
     * @return {@link List}<{@link CartItemVo}>
     */
    @Override
    public List<CartItemVo> getCurrentUserCartListWhichAreChecked() {
        // 从拦截器中获取当前用户
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        if (userInfoTo.getUserId() != null) {
            // 获取当前用户购物车中信息
            String cartKey = CART_PREFIX + userInfoTo.getUserId();
            List<CartItemVo> cartList = getProductFromCart(cartKey);
            // 过滤数据，获取选中的数据和更新价格
            return cartList.stream()
                    .filter(item -> item.getCheck())
                    .map(item -> {
                        // 调用远程接口获取最新价格
                        BigDecimal price = productFeign.getPrice(item.getSkuId());
                        item.setPrice(price);
                        return item;
                    })
                    .collect(Collectors.toList());
        } else {
            return null;
        }

    }

    /**
     * 获取购物车中的商品信息
     *
     * @param cartKey 车钥匙
     * @return {@link List}<{@link CartItemVo}>
     */
    private List<CartItemVo> getProductFromCart(String cartKey) {
        // 获取购物车中所有商品
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(cartKey);
        List<Object> values = operations.values();
        if (values != null && values.size() > 0) {
            List<CartItemVo> cartItemVos = values.stream().map((obj) -> {
                String str = (String) obj;
                return JSON.parseObject(str, CartItemVo.class);
            }).collect(Collectors.toList());
            return cartItemVos;
        }
        return null;
    }
}
