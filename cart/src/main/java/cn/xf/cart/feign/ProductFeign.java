package cn.xf.cart.feign;

import cn.xf.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@FeignClient("product")
public interface ProductFeign {
    @RequestMapping("/product/skuinfo/info/{skuId}")
    R getSkuInfo(@PathVariable("skuId") Long skuId);

    @GetMapping("/product/skusaleattrvalue/saleList/{skuId}")
    List<String> getSaleAttributeConcatList(@PathVariable("skuId") Long skuId);

    /**
     * 获取最新价格
     *
     * @param skuId sku id
     * @return {@link BigDecimal}
     */
    @GetMapping("/product/skuinfo/{skuId}/getPrice")
    BigDecimal getPrice(@PathVariable("skuId") Long skuId);
}
