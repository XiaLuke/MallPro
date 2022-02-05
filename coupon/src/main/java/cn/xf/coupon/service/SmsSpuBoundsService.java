package cn.xf.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.coupon.entity.SmsSpuBoundsEntity;

import java.util.Map;

/**
 * 商品spu积分设置
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:41:07
 */
public interface SmsSpuBoundsService extends IService<SmsSpuBoundsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

