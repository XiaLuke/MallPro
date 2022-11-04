package cn.xf.common.to;

import lombok.Data;

/**
 * 商品是否有库存
 *
 * @author XF
 * @date 2022/08/25
 */
@Data
public class SkuHasStockVo {

    private Long skuId;

    private Boolean hasStock;

}