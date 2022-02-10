package cn.xf.product.service.impl;

import org.springframework.stereotype.Service;

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


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

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
            return (item.getSort()==null?0:item.getSort()) - (itemNext.getSort()==null?0:itemNext.getSort());
        }).collect(Collectors.toList());
        return parent;
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
            return (item.getSort()==null?0:item.getSort()) - (itemNext.getSort()==null?0:itemNext.getSort());
        }).collect(Collectors.toList());
        return children;
    }

}