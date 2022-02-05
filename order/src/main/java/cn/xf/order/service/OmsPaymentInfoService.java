package cn.xf.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.order.entity.OmsPaymentInfoEntity;

import java.util.Map;

/**
 * 支付信息表
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:07
 */
public interface OmsPaymentInfoService extends IService<OmsPaymentInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

