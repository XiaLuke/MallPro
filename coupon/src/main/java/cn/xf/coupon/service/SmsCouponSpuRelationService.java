package cn.xf.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.coupon.entity.SmsCouponSpuRelationEntity;

import java.util.Map;

/**
 * 优惠券与产品关联
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:41:07
 */
public interface SmsCouponSpuRelationService extends IService<SmsCouponSpuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

