package cn.xf.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 22:27:35
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

