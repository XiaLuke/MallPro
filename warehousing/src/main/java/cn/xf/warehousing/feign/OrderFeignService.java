package cn.xf.warehousing.feign;

import cn.xf.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("order")
public interface OrderFeignService {

    /**
     * 获取订单状态
     * --
     * 订单是否取消
     *
     * @param orderSn 订单sn
     * @return {@link R}
     */
    @GetMapping(value = "/order/order/status/{orderSn}")
    R getOrderStatus(@PathVariable("orderSn") String orderSn);

}
