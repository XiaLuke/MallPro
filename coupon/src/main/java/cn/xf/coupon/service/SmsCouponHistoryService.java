package cn.xf.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.coupon.entity.SmsCouponHistoryEntity;

import java.util.Map;

/**
 * 优惠券领取历史记录
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:41:07
 */
public interface SmsCouponHistoryService extends IService<SmsCouponHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

