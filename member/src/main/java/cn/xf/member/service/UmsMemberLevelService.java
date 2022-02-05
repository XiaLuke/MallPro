package cn.xf.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.member.entity.UmsMemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:59
 */
public interface UmsMemberLevelService extends IService<UmsMemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

