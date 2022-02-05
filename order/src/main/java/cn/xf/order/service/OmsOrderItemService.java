package cn.xf.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.order.entity.OmsOrderItemEntity;

import java.util.Map;

/**
 * 订单项信息
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:07
 */
public interface OmsOrderItemService extends IService<OmsOrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

