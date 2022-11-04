package cn.xf.product.service;

import cn.xf.product.vo.SkuItemSaleAttrVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.SkuSaleAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * sku销售属性&值
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据商品id查询商品属性集合
     *
     * @param productId 商品id
     * @return {@link List}<{@link SkuItemSaleAttrVo}>
     */
    List<SkuItemSaleAttrVo> queryAttrInfoByProductId(Long productId);

    /**
     * 获取商品销售属性组合
     *
     * @param skuId sku id
     * @return {@link List}<{@link String}>
     */
    List<String> getSaleAttributeConcatList(Long skuId);
}

