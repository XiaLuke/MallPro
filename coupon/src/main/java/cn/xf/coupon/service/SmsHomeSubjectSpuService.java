package cn.xf.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.coupon.entity.SmsHomeSubjectSpuEntity;

import java.util.Map;

/**
 * 专题商品
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:41:07
 */
public interface SmsHomeSubjectSpuService extends IService<SmsHomeSubjectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

