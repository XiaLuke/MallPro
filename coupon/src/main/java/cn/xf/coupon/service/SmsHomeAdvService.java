package cn.xf.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.coupon.entity.SmsHomeAdvEntity;

import java.util.Map;

/**
 * 首页轮播广告
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:41:07
 */
public interface SmsHomeAdvService extends IService<SmsHomeAdvEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

