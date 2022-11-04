package cn.xf.search.service;

import cn.xf.common.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * 产品保存服务
 *
 * @author XF
 * @date 2022/08/25
 */
public interface ProductSaveService {

    /**
     * 产品上架保存信息到es中
     *
     * @param skuEsModels sku es模型
     * @return boolean
     * @throws IOException ioexception
     */
    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
