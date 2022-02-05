package cn.xf.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.order.entity.OmsOrderReturnReasonEntity;

import java.util.Map;

/**
 * 退货原因
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:07
 */
public interface OmsOrderReturnReasonService extends IService<OmsOrderReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

