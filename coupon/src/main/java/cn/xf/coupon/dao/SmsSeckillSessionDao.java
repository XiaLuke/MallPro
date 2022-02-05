package cn.xf.coupon.dao;

import cn.xf.coupon.entity.SmsSeckillSessionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动场次
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:41:07
 */
@Mapper
public interface SmsSeckillSessionDao extends BaseMapper<SmsSeckillSessionEntity> {
	
}
