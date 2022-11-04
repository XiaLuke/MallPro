package cn.xf.order.dao;

import cn.xf.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 22:30:13
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    /**
     * 订单支付成功修改状态
     *
     * @param orderSn 订单sn
     * @param code    代码
     * @param payType 支付类型
     */
    void updateOrderStatus(@Param("orderSn") String orderSn, @Param("code") Integer code, @Param("payType") Integer payType);
}
