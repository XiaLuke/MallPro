package cn.xf.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.order.entity.OmsOrderReturnApplyEntity;

import java.util.Map;

/**
 * 订单退货申请
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:07
 */
public interface OmsOrderReturnApplyService extends IService<OmsOrderReturnApplyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

