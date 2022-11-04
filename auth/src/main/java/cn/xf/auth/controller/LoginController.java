package cn.xf.auth.controller;

import cn.xf.auth.feign.MemberFeign;
import cn.xf.auth.feign.ThirdFeign;
import cn.xf.auth.vo.UserLoginVo;
import cn.xf.auth.vo.UserRegisterVo;
import cn.xf.common.constant.AuthVerifyConstant;
import cn.xf.common.exception.BizCodeEnum;
import cn.xf.common.utils.R;
import cn.xf.common.vo.MemberResponseVo;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cn.xf.common.constant.AuthVerifyConstant.LOGIN_USER;

@Controller
public class LoginController {
    @Autowired
    private ThirdFeign thirdFeign;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MemberFeign memberFeign;


    /**
     * 跳转登录页
     *
     * @return {@link String}
     */
    @GetMapping("/login.html")
    public String login(HttpSession session) {
        // 如果登录过，跳转到主页
        Object attribute = session.getAttribute(LOGIN_USER);
        if(attribute == null){
            return "login";
        }else{
            return "redirect:http://mall.xyz";
        }
    }

    /**
     * 跳转注册页
     *
     * @return {@link String}
     */
    @GetMapping("/reg.html")
    public String register() {
        return "reg";
    }

    /**
     * 获取验证码
     *
     * @param phone 电话
     * @return {@link R}
     */
    @GetMapping(value = "/getVerifyCode")
    @ResponseBody
    public R getVerifyCode(@RequestParam("phone") String phone) {
        // 1.接口防刷，查看redis中是否又数据，规定时间内不能再进行发送验证码
        // key：verify-code-15883860763 value：
        String redisCacheData = redisTemplate.opsForValue().get(AuthVerifyConstant.REGISTER_VERIFY_CODE + phone);
        if (!StringUtils.isEmpty(redisCacheData)) {
            // 获取redis中存入的数据时间
            long time = Long.parseLong(redisCacheData.split("_")[1]);
            if (System.currentTimeMillis() - time < 60000) {
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMessage());
            }
        }

        // 调用远程接口获取验证码
        R returnCode = thirdFeign.getVerifyCode(phone);
        Integer data = returnCode.getData("code", new TypeReference<Integer>() {
        });
        String resultCode = data + "_" + System.currentTimeMillis();
        // 2.将数据存入redis中
        redisTemplate.opsForValue().set(AuthVerifyConstant.REGISTER_VERIFY_CODE + phone, resultCode, 10, TimeUnit.MINUTES);

        return R.ok().put("code", data);
    }

    /**
     * 注册
     *
     * @param vos        vos
     * @param result     结果
     * @param attributes 使用RedirectAttributes替换Modal，防止表单的刷新重复提交，也是让重定向时保留数据
     * @return {@link String}
     */
    @PostMapping(value = "/register")
    public String register(@Valid UserRegisterVo vos, BindingResult result,
                           RedirectAttributes attributes) {
        // 校验数据
        if (result.hasErrors()) {
            Map<String, String> errorMap = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            // 重定向携带数据
            attributes.addFlashAttribute("errors", errorMap);
            return "redirect:http://auth.mall.xyz/reg.html";
        }

        // 校验验证码
        String verifyCode = vos.getCode();

        // 从redis中获取验证码
        String redisVerifyCode = redisTemplate.opsForValue().get(AuthVerifyConstant.REGISTER_VERIFY_CODE + vos.getPhone());
        if (!StringUtils.isEmpty(redisVerifyCode)) {
            if (verifyCode.equals(redisVerifyCode.split("_")[0])) {
                // 删除redis中验证码，令牌机制
                redisTemplate.delete(AuthVerifyConstant.REGISTER_VERIFY_CODE + vos.getPhone());
                // 远程注册
                R registerResult = memberFeign.register(vos);
                if (registerResult.getCode() == 0) {
                    return "redirect:http://auth.mall.xyz/login.html";
                } else {
                    Map<String, String> errors = new HashMap<>();
                    errors.put("msg", registerResult.getData("msg", new TypeReference<String>() {
                    }));
                    attributes.addFlashAttribute("errors", errors);
                    return "redirect:http://auth.mall.xyz/reg.html";
                }
            } else {
                Map<String, String> errors = new HashMap<>();
                errors.put("code", "验证码错误");
                attributes.addFlashAttribute("errors", errors);
                return "redirect:http://auth.mall.xyz/reg.html";
            }
        } else {
            //效验出错回到注册页面
            Map<String, String> errors = new HashMap<>();
            errors.put("code", "验证码错误");
            attributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.mall.xyz/reg.html";
        }
    }

    @PostMapping(value = "/login")
    public String login(UserLoginVo vo, RedirectAttributes attributes, HttpSession session) {
        R login = memberFeign.login(vo);
        if (login.getCode() == 0) {
            MemberResponseVo data = login.getData("data", new TypeReference<MemberResponseVo>() {
            });
            session.setAttribute(LOGIN_USER, data);
            return "redirect:http://mall.xyz";
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("msg", login.getData("msg", new TypeReference<String>() {
            }));
            attributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.mall.xyz/login.html";
        }
    }
}