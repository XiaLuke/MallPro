package cn.xf.coupon.dao;

import cn.xf.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 22:25:53
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
