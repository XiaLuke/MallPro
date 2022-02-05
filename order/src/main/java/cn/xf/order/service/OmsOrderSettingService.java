package cn.xf.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.order.entity.OmsOrderSettingEntity;

import java.util.Map;

/**
 * 订单配置信息
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:07
 */
public interface OmsOrderSettingService extends IService<OmsOrderSettingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

