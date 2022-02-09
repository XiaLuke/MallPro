package cn.xf.member.feign;

import cn.xf.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("coupon")
public interface FeignService {

    @RequestMapping("/coupon/coupon/one")
    public R test();
}
