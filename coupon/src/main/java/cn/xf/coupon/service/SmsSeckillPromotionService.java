package cn.xf.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.coupon.entity.SmsSeckillPromotionEntity;

import java.util.Map;

/**
 * 秒杀活动
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:41:07
 */
public interface SmsSeckillPromotionService extends IService<SmsSeckillPromotionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

