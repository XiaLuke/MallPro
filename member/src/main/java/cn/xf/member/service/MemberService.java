package cn.xf.member.service;

import cn.xf.member.vo.UserLoginVo;
import cn.xf.member.vo.UserRegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 22:27:35
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 用户注册
     *
     * @param registerVo 注册签证官
     */
    void register(UserRegisterVo registerVo);

    /**
     * 通过用户名查询会员信息
     *
     * @param userName 用户名
     */
    void queryMemberInfoByUserName(String userName);

    /**
     * 通过电话查询会员信息
     *
     * @param phone 电话
     */
    void queryMemberInfoByPhone(String phone);

    /**
     * 登录
     *
     * @param userLoginVo 用户登录签证官
     * @return {@link MemberEntity}
     */
    MemberEntity login(UserLoginVo userLoginVo);
}

