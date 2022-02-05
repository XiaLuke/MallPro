package cn.xf.order.dao;

import cn.xf.order.entity.OmsPaymentInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:07
 */
@Mapper
public interface OmsPaymentInfoDao extends BaseMapper<OmsPaymentInfoEntity> {
	
}
