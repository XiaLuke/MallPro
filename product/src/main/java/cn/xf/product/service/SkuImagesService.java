package cn.xf.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.SkuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * sku图片
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据商品库存id得到商品库存图片信息
     *
     * @param skuId sku id
     * @return {@link List}<{@link SkuImagesEntity}>
     */
    List<SkuImagesEntity> getProductStockImageInformationByProductStockId(Long skuId);
}

