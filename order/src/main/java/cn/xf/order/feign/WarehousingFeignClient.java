package cn.xf.order.feign;

import cn.xf.common.utils.R;
import cn.xf.order.vo.WareSkuLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("warehousing")
public interface WarehousingFeignClient {

    @PostMapping(value = "/warehousing/waresku/hasStock")
    R getSkuHasStock(@RequestBody List<Long> skuIds);

    @GetMapping("/warehousing/wareinfo/fare")
    public R getFare(@RequestParam("addressId") Long addressId);

    /**
     * 锁定库存
     *
     * @param lockVo 锁签证官
     * @return {@link R}
     */
    @PostMapping(value = "/warehousing/waresku/lock/order")
    R orderLockStock(@RequestBody WareSkuLockVo lockVo);
}

