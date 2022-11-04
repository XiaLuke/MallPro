package cn.xf.product.feign;

import cn.xf.common.to.SkuHasStockVo;
import cn.xf.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 仓库远程调用服务
 *
 * @author XF
 * @date 2022/08/25
 */
@FeignClient("warehousing")
public interface WareFeignService {

    /**
     * 查询商品是否有库存
     *
     * @param skuIds sku id
     * @return {@link R}
     */
    @PostMapping(value = "/warehousing/waresku/hasStock")
    R getSkuHasStock(@RequestBody List<Long> skuIds);

}
