package cn.xf.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.member.entity.UmsMemberLoginLogEntity;

import java.util.Map;

/**
 * 会员登录记录
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:59
 */
public interface UmsMemberLoginLogService extends IService<UmsMemberLoginLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

