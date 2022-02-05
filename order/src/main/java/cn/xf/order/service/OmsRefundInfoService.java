package cn.xf.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.order.entity.OmsRefundInfoEntity;

import java.util.Map;

/**
 * 退款信息
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:07
 */
public interface OmsRefundInfoService extends IService<OmsRefundInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

