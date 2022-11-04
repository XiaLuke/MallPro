package cn.xf.product.service.impl;

import cn.xf.product.vo.SkuItemSaleAttrVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.Query;

import cn.xf.product.dao.SkuSaleAttrValueDao;
import cn.xf.product.entity.SkuSaleAttrValueEntity;
import cn.xf.product.service.SkuSaleAttrValueService;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据商品id查询商品属性集合
     *
     * @param productId 商品id
     * @return {@link List}<{@link SkuItemSaleAttrVo}>
     */
    @Override
    public List<SkuItemSaleAttrVo> queryAttrInfoByProductId(Long productId) {
        return baseMapper.queryAttrInfoByProductId(productId);
    }

    /**
     * 获取商品销售属性组合
     *
     * @param skuId sku id
     * @return {@link List}<{@link String}>
     */
    @Override
    public List<String> getSaleAttributeConcatList(Long skuId) {
        return baseMapper.getSaleAttributeConcatList(skuId);
    }

}