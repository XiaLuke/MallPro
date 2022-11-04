package cn.xf.product.feign;

import cn.xf.common.es.SkuEsModel;
import cn.xf.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("elasticsearch")
public interface SearchFeignService {

    /**
     * 商品上架数据保存到es中
     *
     * @param skuEsModels sku es模型
     * @return {@link R}
     */
    @PostMapping(value = "/search/save/product")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);

}