package cn.xf.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.CategoryBrandRelationEntity;

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
}

