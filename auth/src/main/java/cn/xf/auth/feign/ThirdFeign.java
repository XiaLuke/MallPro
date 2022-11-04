package cn.xf.auth.feign;

import cn.xf.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 第三方整合
 *
 * @author XF
 * @date 2022/09/14
 */
@FeignClient("third-party")
public interface ThirdFeign {
    /**
     * 获取验证码
     *
     * @param phone 电话
     * @return {@link R}
     */
    @GetMapping(value = "/sendMessage/getCode")
    R getVerifyCode(@RequestParam("phone") String phone);
}
