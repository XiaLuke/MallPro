package cn.xf.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.Query;

import cn.xf.product.dao.SkuImagesDao;
import cn.xf.product.entity.SkuImagesEntity;
import cn.xf.product.service.SkuImagesService;


@Service("skuImagesService")
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesDao, SkuImagesEntity> implements SkuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuImagesEntity> page = this.page(
                new Query<SkuImagesEntity>().getPage(params),
                new QueryWrapper<SkuImagesEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据商品库存id得到商品库存图片信息
     *
     * @param skuId sku id
     * @return {@link List}<{@link SkuImagesEntity}>
     */
    @Override
    public List<SkuImagesEntity> getProductStockImageInformationByProductStockId(Long skuId) {
        return baseMapper.selectList(new QueryWrapper<SkuImagesEntity>().eq("sku_id", skuId));
    }

}