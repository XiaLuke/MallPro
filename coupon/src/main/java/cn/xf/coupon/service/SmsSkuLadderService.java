package cn.xf.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.coupon.entity.SmsSkuLadderEntity;

import java.util.Map;

/**
 * 商品阶梯价格
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:41:07
 */
public interface SmsSkuLadderService extends IService<SmsSkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

