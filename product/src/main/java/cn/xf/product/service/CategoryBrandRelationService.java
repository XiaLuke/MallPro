package cn.xf.product.service;

import cn.xf.product.entity.BrandEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存分类细节
     *
     * @param categoryBrandRelation 类别品牌关系
     */
    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    /**
     * 品牌更新时同步数据
     *
     * @param brandId 品牌标识
     * @param name    名字
     */
    void updateBrand(Long brandId, String name);

    /**
     * 更新类别
     *
     * @param catId 猫id
     * @param name  名字
     */
    void updateCategory(Long catId, String name);

    List<BrandEntity> getBrandsByCatId(Long catId);
}

