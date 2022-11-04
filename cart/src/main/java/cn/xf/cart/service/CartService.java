package cn.xf.cart.service;

import cn.xf.cart.vo.CartItemVo;
import cn.xf.cart.vo.CartVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CartService {
    /**
     * 将商品添加到购物车
     *
     * @param skuId sku id
     * @param num   全国矿工工会
     * @return {@link CartItemVo}
     * @throws ExecutionException   执行异常
     * @throws InterruptedException 中断异常
     */
    CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

    /**
     * 获取购物车中某一项数据
     *
     * @param skuId sku id
     * @return {@link CartItemVo}
     */
    CartItemVo getCartItem(Long skuId);

    /**
     * 获取购物车数据
     *
     * @return {@link CartVo}
     */
    CartVo integrationCart() throws ExecutionException, InterruptedException;

    /**
     * 清空临时购物车
     *
     * @param cartKey 车钥匙
     */
    void clearTempCart(String cartKey);

    /**
     * 记录是否选中
     *
     * @param skuId   sku id
     * @param checked 检查
     */
    void checkItem(Long skuId, Integer checked);

    /**
     * 改变商品数量
     * @param skuId
     * @param num
     */
    void changeItemCount(Long skuId, Integer num);


    /**
     * 删除购物项
     * @param skuId
     */
    void deleteIdCartInfo(Integer skuId);

    /**
     * 获取当前用户购物车中选中的物品信息
     *
     * @return {@link List}<{@link CartItemVo}>
     */
    List<CartItemVo> getCurrentUserCartListWhichAreChecked();

}
