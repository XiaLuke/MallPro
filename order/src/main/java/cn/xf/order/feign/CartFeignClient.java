package cn.xf.order.feign;

import cn.xf.order.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("cart")
public interface CartFeignClient {
    /**
     * 获取当前用户购物车中选中的商品信息
     *
     * @return {@link List}<{@link OrderItemVo}>
     */
    @GetMapping("/getCartWhichAreCheckedList")
    List<OrderItemVo> getCurrentUserCartListWhichAreChecked();

}
