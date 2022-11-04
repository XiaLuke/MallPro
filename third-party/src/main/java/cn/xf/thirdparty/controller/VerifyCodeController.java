package cn.xf.thirdparty.controller;

import cn.xf.common.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证代码控制器
 *
 * @author XF
 * @date 2022/09/14
 */
@RestController
@RequestMapping(value = "/sendMessage")
public class VerifyCodeController {
    @GetMapping("/getCode")
    public R getVerifyCode(@RequestParam("phone") String phone) {
        System.out.println(phone);
        int code = (int) ((Math.random()) * 1000000);
        return R.ok().put("code", code);
    }
}
