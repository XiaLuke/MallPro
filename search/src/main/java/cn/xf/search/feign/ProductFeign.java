package cn.xf.search.feign;

import cn.xf.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品远程调用
 *
 * @author XF
 * @date 2022/09/04
 */
@FeignClient("product")
public interface ProductFeign {

    /**
     * 属性信息
     *
     * @param attrId attr id
     * @return {@link R}
     */
    @GetMapping("/product/attr/info/{attrId}")
    R attrInfo(@PathVariable("attrId") Long attrId);


    /**
     * 根据品牌id集合查询品牌信息
     *
     * @param brandIds 品牌标识
     * @return {@link R}
     */
    @GetMapping("/product/brand/getBrandInfosByBrandIds")
    R getBrandInfosByBrandIds(@RequestParam("brandIds") List<Long> brandIds);
}
