package cn.xf.order.dao;

import cn.xf.order.entity.OmsOrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:07
 */
@Mapper
public interface OmsOrderItemDao extends BaseMapper<OmsOrderItemEntity> {
	
}
