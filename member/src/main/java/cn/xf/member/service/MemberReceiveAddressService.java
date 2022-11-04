package cn.xf.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.member.entity.MemberReceiveAddressEntity;

import java.util.List;
import java.util.Map;

/**
 * 会员收货地址
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 22:27:34
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取收货地址
     *
     * @param memberId 成员身份
     * @return {@link List}<{@link MemberReceiveAddressEntity}>
     */
    List<MemberReceiveAddressEntity> getMemberReceiveAddress(Long memberId);

}

