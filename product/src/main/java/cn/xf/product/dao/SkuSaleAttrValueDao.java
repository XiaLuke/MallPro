package cn.xf.product.dao;

import cn.xf.product.entity.SkuSaleAttrValueEntity;
import cn.xf.product.vo.SkuItemSaleAttrVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    /**
     * 根据商品id查询商品属性
     *
     * @param productId 产品id
     * @return {@link List}<{@link SkuItemSaleAttrVo}>
     */
    List<SkuItemSaleAttrVo> queryAttrInfoByProductId(@Param("productId") Long productId);

    /**
     * 获取商品销售属性组合
     *
     * @param skuId sku id
     * @return {@link List}<{@link String}>
     */
    List<String> getSaleAttributeConcatList(@Param("skuId") Long skuId);
}
