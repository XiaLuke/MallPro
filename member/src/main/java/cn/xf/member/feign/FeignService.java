package cn.xf.member.feign;

import cn.xf.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("/coupon/smscoupon")
public interface FeignService {

    @RequestMapping("/one")
    public R one();
}
