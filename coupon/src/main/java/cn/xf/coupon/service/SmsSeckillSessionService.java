package cn.xf.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.coupon.entity.SmsSeckillSessionEntity;

import java.util.Map;

/**
 * 秒杀活动场次
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:41:07
 */
public interface SmsSeckillSessionService extends IService<SmsSeckillSessionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

