package cn.xf.product.service.impl;

import cn.xf.product.entity.SkuImagesEntity;
import cn.xf.product.entity.SkuSaleAttrValueEntity;
import cn.xf.product.entity.SpuInfoDescEntity;
import cn.xf.product.service.*;
import cn.xf.product.vo.SkuItemSaleAttrVo;
import cn.xf.product.vo.SkuItemVo;
import cn.xf.product.vo.SpuItemAttrGroupVo;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.Query;

import cn.xf.product.dao.SkuInfoDao;
import cn.xf.product.entity.SkuInfoEntity;

import javax.annotation.Resource;


/**
 * 商品库存信息服务实现类
 *
 * @author XF
 * @date 2022/09/06
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private AttrGroupService attrGroupService;

    /**
     * 商品信息销售属性
     * */
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    /**
     * 线程池
     */
    @Autowired
    ThreadPoolExecutor executor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> queryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key) && !"0".equalsIgnoreCase(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.eq("sku_id", key).or().like("sku_name", key);
            });
        }

        String catelogId = (String) params.get("catelogId");
        if (!StringUtils.isEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)) {
            queryWrapper.eq("catalog_id", catelogId);
        }

        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId) && !"0".equalsIgnoreCase(brandId)) {
            queryWrapper.eq("brand_id", brandId);
        }

        String min = (String) params.get("min");
        if (!StringUtils.isEmpty(min)) {
            queryWrapper.ge("price", min);
        }

        String max = (String) params.get("max");

        if (!StringUtils.isEmpty(max)) {
            try {
                BigDecimal bigDecimal = new BigDecimal(max);
                if (bigDecimal.compareTo(BigDecimal.ZERO) == 1) {
                    queryWrapper.le("price", max);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        return this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
    }

    /**
     * 产品库存信息
     *
     * @param skuId sku id
     * @return {@link SkuItemVo}
     * @throws Exception 异常
     */
    @Override
    public SkuItemVo productInventoryDetails(Long skuId) throws Exception {
        SkuItemVo result = new SkuItemVo();
        // 1.商品库存基本信息
        CompletableFuture<SkuInfoEntity> skuInfoEntity = CompletableFuture.supplyAsync(() -> {
            SkuInfoEntity info = getById(skuId);
            result.setInfo(info);
            return info;
        }, executor);

        CompletableFuture<Void> saleAttrValueList = skuInfoEntity.thenAcceptAsync((res) -> {
            // 3.商品信息的销售属性
            List<SkuItemSaleAttrVo> list = skuSaleAttrValueService.queryAttrInfoByProductId(res.getSpuId());
            result.setSaleAttr(list);
        }, executor);

        CompletableFuture<Void> productDescribeInfo = skuInfoEntity.thenAcceptAsync((res) -> {
            // 4.商品介绍
            SpuInfoDescEntity info = spuInfoDescService.getById(res.getSpuId());
            result.setDesc(info);
        }, executor);

        CompletableFuture<Void> attrGroupInfoList = skuInfoEntity.thenAcceptAsync((res) -> {
            // 5.商品的规格参数信息
            List<SpuItemAttrGroupVo> list = attrGroupService.getProductSpecificationsByProductId(res.getSpuId(), res.getCatalogId());
            result.setGroupAttrs(list);
        }, executor);

        CompletableFuture<Void> productStockImages = CompletableFuture.runAsync(() -> {
            // 2.商品库存图片信息
            List<SkuImagesEntity> list = skuImagesService.getProductStockImageInformationByProductStockId(skuId);
            result.setImages(list);
        }, executor);

        // 等待所有任务结束
        CompletableFuture.allOf(saleAttrValueList,productDescribeInfo,attrGroupInfoList,productStockImages).get();
        return result;
    }

}