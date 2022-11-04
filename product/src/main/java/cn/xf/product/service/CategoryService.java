package cn.xf.product.service;

import cn.xf.product.vo.Catelog2Vo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 类型树状结构
     *
     * @return
     */
    List<CategoryEntity> treeList();

    /**
     * 根据id删除类型
     *
     * @param idList id集合
     */
    void removeItemByIds(List<Long> idList);

    /**
     * 找到catelogId的完整路径；
     * [父/子/孙]
     * @param catelogId
     * @return
     */
    Long[] findCatelogPath(Long catelogId);

    /**
     * 级联更新
     *
     * @param category 类别
     */
    void updateCascade(CategoryEntity category);

    /**
     * 查询一级分类
     *
     * @return {@link List}<{@link CategoryEntity}>
     */
    List<CategoryEntity> queryLevelOneClassification();

    /**
     * 获取二级三级json数据
     *
     * @return {@link Map}<{@link String}, {@link List}<{@link Catelog2Vo}>>
     */
    Map<String, List<Catelog2Vo>> getCatalogJson();

}

