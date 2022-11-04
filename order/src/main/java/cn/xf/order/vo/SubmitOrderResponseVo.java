package cn.xf.order.vo;

import cn.xf.order.entity.OrderEntity;
import lombok.Data;

/**
 * 提交订单返回信息
 *
 * @author XF
 * @date 2022/10/16
 */
@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;

    /** 错误状态码 **/
    private Integer code;


}