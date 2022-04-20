package cn.xf.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 跨服务传递的积分信息
 *
 * @author XF
 * @date 2022/04/20
 */
@Data
public class SpuBoundTo {

    private Long spuId;
    /**
     * 购买积分
     */
    private BigDecimal buyBounds;
    /**
     * 成长积分
     */
    private BigDecimal growBounds;
}