package cn.xf.product.vo;

import cn.xf.product.entity.SkuImagesEntity;
import cn.xf.product.entity.SkuInfoEntity;
import cn.xf.product.entity.SpuInfoDescEntity;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 商品库存详情
 *
 * @author XF
 * @date 2022/09/06
 */
@ToString
@Data
public class SkuItemVo {

    /**
     * 商品库存基本信息的获取  pms_sku_info
     */
    private SkuInfoEntity info;

    private boolean hasStock = true;

    /**
     * 商品库存图片信息
     * pms_sku_images
     */
    private List<SkuImagesEntity> images;

    /**
     * 商品销售属性组合
     */
    private List<SkuItemSaleAttrVo> saleAttr;

    /**
     * 商品介绍
     */
    private SpuInfoDescEntity desc;

    /**
     * 商品规格参数集合
     */
    private List<SpuItemAttrGroupVo> groupAttrs;

    /**
     * 秒杀商品的优惠信息
     */
    private SeckillSkuVo seckillSkuVo;

}