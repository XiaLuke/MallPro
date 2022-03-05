package cn.xf.product.service.impl;

import cn.xf.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.Query;

import cn.xf.product.dao.CategoryDao;
import cn.xf.product.entity.CategoryEntity;
import cn.xf.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查出所有分类并组装成为树形结构
     *
     * @return
     */
    @Override
    public List<CategoryEntity> treeList() {
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(null);
        // 一级分类
        List<CategoryEntity> parent = categoryEntityList.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map(item -> {
            item.setChildrenList(getChildren(item, categoryEntityList));
            return item;
        }).sorted((item, itemNext) -> {
            return (item.getSort() == null ? 0 : item.getSort()) - (itemNext.getSort() == null ? 0 : itemNext.getSort());
        }).collect(Collectors.toList());
        return parent;
    }

    @Override
    public void removeItemByIds(List<Long> idList) {
        //TODO 检查当前删除数据是否在其他地方使用
        // 逻辑删除
        baseMapper.deleteBatchIds(idList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catelogId, paths);

        Collections.reverse(parentPath);

        return parentPath.toArray(new Long[parentPath.size()]);
    }

    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        //1、收集当前节点id
        paths.add(catelogId);
        // 当前分类
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }

    private List<CategoryEntity> getChildren(CategoryEntity category, List<CategoryEntity> categoryEntityList) {
        List<CategoryEntity> children = categoryEntityList.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().equals(category.getCatId());
        }).map(categoryEntity -> {
            // 找子菜单
            categoryEntity.setChildrenList(getChildren(categoryEntity, categoryEntityList));
            return categoryEntity;
        }).sorted((item, itemNext) -> {
            // 排序
            return (item.getSort() == null ? 0 : item.getSort()) - (itemNext.getSort() == null ? 0 : itemNext.getSort());
        }).collect(Collectors.toList());
        return children;
    }

}