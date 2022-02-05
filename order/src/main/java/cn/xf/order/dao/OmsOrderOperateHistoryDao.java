package cn.xf.order.dao;

import cn.xf.order.entity.OmsOrderOperateHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单操作历史记录
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:07
 */
@Mapper
public interface OmsOrderOperateHistoryDao extends BaseMapper<OmsOrderOperateHistoryEntity> {
	
}
