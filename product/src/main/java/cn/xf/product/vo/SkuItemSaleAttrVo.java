package cn.xf.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 商品销售属性组合
 *
 * @author XF
 * @date 2022/09/06
 */
@Data
@ToString
public class SkuItemSaleAttrVo {

    private Long attrId;

    private String attrName;

    private List<AttrValueWithSkuIdVo> attrValues;

}
