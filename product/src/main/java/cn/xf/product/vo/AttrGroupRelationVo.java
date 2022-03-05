package cn.xf.product.vo;

import lombok.Data;

/**
 * 商品属性与规格参数
 *
 * @author XF
 * @date 2022/03/05
 */
@Data
public class AttrGroupRelationVo {

    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性分组id
     */
    private Long attrGroupId;
}
