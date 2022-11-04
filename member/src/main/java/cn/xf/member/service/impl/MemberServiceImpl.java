package cn.xf.member.service.impl;

import cn.xf.member.dao.MemberLevelDao;
import cn.xf.member.entity.MemberLevelEntity;
import cn.xf.member.exception.PhoneExistException;
import cn.xf.member.exception.UserNameExistException;
import cn.xf.member.vo.UserLoginVo;
import cn.xf.member.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.Query;

import cn.xf.member.dao.MemberDao;
import cn.xf.member.entity.MemberEntity;
import cn.xf.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberLevelDao memberLevel;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(UserRegisterVo registerVo) {
        MemberEntity regisMember = new MemberEntity();
        // 设置默认等级
        MemberLevelEntity defaultMemberLevel = memberLevel.selectOne(new QueryWrapper<MemberLevelEntity>().eq("default_status",1));
        regisMember.setLevelId(defaultMemberLevel.getId());

        // 设置手机号和用户名之前需要判断数据库中是否有相同的数据
        queryMemberInfoByUserName(registerVo.getUserName());
        queryMemberInfoByPhone(registerVo.getPhone());

        // 校验通过后设置用户名和手机号
        regisMember.setMobile(registerVo.getPhone());
        regisMember.setUsername(registerVo.getUserName());
        regisMember.setNickname(registerVo.getUserName());

        // 密码加密
        BCryptPasswordEncoder cryptPassword = new BCryptPasswordEncoder();
        String storePassword = cryptPassword.encode(registerVo.getPassword());
        regisMember.setPassword(storePassword);

        baseMapper.insert(regisMember);
    }

    @Override
    public void queryMemberInfoByUserName(String userName) {
        Long count = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName));
        if (count > 0) {
            throw new UserNameExistException();
        }
    }

    @Override
    public void queryMemberInfoByPhone(String phone) {
        Long count = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (count > 0) {
            throw new PhoneExistException();
        }
    }

    @Override
    public MemberEntity login(UserLoginVo userLoginVo) {
        String loginAccount = userLoginVo.getLoginAccount();
        String password = userLoginVo.getPassword();

        //1、去数据库查询 SELECT * FROM ums_member WHERE username = ? OR mobile = ?
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>()
                .eq("username", loginAccount).or().eq("mobile", loginAccount));

        if (memberEntity == null) {
            //登录失败
            return null;
        } else {
            //获取到数据库里的password
            String password1 = memberEntity.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //进行密码匹配
            boolean matches = passwordEncoder.matches(password, password1);
            if (matches) {
                //登录成功
                return memberEntity;
            }
        }

        return null;
    }
}