package cn.xf.coupon.dao;

import cn.xf.coupon.entity.SmsCouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:41:07
 */
@Mapper
public interface SmsCouponHistoryDao extends BaseMapper<SmsCouponHistoryEntity> {
	
}
