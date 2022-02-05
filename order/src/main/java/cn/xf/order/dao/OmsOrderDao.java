package cn.xf.order.dao;

import cn.xf.order.entity.OmsOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:07
 */
@Mapper
public interface OmsOrderDao extends BaseMapper<OmsOrderEntity> {
	
}
