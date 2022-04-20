package cn.xf.product.vo;

import lombok.Data;

/**
 * 商品基本属性
 *
 * @author XF
 * @date 2022/04/20
 */
@Data
public class BaseAttrs {
    private Long attrId;
    private String attrValues;
    private int showDesc;
}
