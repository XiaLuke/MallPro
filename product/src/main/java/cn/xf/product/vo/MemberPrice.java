package cn.xf.product.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MemberPrice {
    /**
     * 会员等级id
     */
    private Long id;

    /**
     * 会员等级名字
     */
    private String name;
    /**
     * 会员价格
     */
    private BigDecimal price;
}
