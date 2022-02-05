package cn.xf.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.member.entity.UmsIntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:58
 */
public interface UmsIntegrationChangeHistoryService extends IService<UmsIntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

