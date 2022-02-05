package cn.xf.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.member.entity.UmsMemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:58
 */
public interface UmsMemberStatisticsInfoService extends IService<UmsMemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

