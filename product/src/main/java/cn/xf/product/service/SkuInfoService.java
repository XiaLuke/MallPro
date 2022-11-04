package cn.xf.product.service;

import cn.xf.product.vo.SkuItemVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.SkuInfoEntity;

import java.util.List;
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

    /**
     * 商品页面携带条件查询
     *
     * @param params 参数个数
     * @return {@link PageUtils}
     */
    PageUtils queryPageCondition(Map<String, Object> params);

    List<SkuInfoEntity> getSkusBySpuId(Long spuId);

    /**
     * 产品库存信息
     *
     * @param skuId sku id
     * @return {@link SkuItemVo}
     * @throws Exception 异常
     */
    SkuItemVo productInventoryDetails(Long skuId) throws Exception;
}

