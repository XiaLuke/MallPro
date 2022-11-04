package cn.xf.auth.feign;

import cn.xf.auth.vo.UserLoginVo;
import cn.xf.auth.vo.UserRegisterVo;
import cn.xf.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 成员frign
 *
 * @author XF
 * @date 2022/09/15
 */
@FeignClient("member")
public interface MemberFeign {
    /**
     * 远程会员模块注册
     *
     * @param registerVo 注册签证官
     * @return {@link R}
     */
    @PostMapping("/member/member/register")
    R register(@RequestBody UserRegisterVo registerVo);

    /**
     * 远程会员模块登录
     *
     * @param vo 签证官
     * @return {@link R}
     */
    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo vo);
}
