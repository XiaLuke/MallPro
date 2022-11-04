package cn.xf.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.coupon.entity.SeckillSessionEntity;

import java.util.List;
import java.util.Map;

/**
 * 秒杀活动场次
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 22:25:53
 */
public interface SeckillSessionService extends IService<SeckillSessionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 得到最近三天的秒杀商品数据
     *
     * @return {@link List}<{@link SeckillSessionEntity}>
     */
    List<SeckillSessionEntity> getLasted3DaySession();

}

