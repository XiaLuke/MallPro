package cn.xf.product.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 积分
 *
 * @author XF
 * @date 2022/04/20
 */
@Data
public class Bounds {

    /**
     * 购买积分
     */
    private BigDecimal buyBounds;
    /**
     * 生长积分
     */
    private BigDecimal growBounds;


}