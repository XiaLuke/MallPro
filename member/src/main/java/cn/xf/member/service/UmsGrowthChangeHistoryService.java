package cn.xf.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.member.entity.UmsGrowthChangeHistoryEntity;

import java.util.Map;

/**
 * 成长值变化历史记录
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:58
 */
public interface UmsGrowthChangeHistoryService extends IService<UmsGrowthChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

