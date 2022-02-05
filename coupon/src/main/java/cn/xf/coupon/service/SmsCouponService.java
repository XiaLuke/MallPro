package cn.xf.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.coupon.entity.SmsCouponEntity;

import java.util.Map;

/**
 * 优惠券信息
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:41:07
 */
public interface SmsCouponService extends IService<SmsCouponEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

