package cn.xf.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存sku信息
     *
     * @param skuInfoEntity sku信息实体
     */
    void saveSkuInfo(SkuInfoEntity skuInfoEntity);
}

