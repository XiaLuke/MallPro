package cn.xf.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存产品属性
     *
     * @param collect 收集
     */
    void saveProductAttr(List<ProductAttrValueEntity> collect);

    /**
     * 获取商品规格
     *
     * @param spuId 商品id
     * @return {@link List}<{@link ProductAttrValueEntity}>
     */
    List<ProductAttrValueEntity> baseAttrListforspu(Long spuId);

    /**
     * 更新商品规格参数
     *
     * @param spuId    spu id
     * @param entities 实体
     */
    void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> entities);
}

