package cn.xf.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.xf.product.entity.CategoryEntity;
import cn.xf.product.service.CategoryService;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.R;


/**
 * 类别控制器
 * 商品三级分类
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 查询分类并以树状结构展示
     */
    @RequestMapping("/tree")
    public R treeList(){
        List<CategoryEntity> categoryTree = categoryService.treeList();
        return R.ok().put("tree",categoryTree);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 重新排序
     *
     * @param category 类别
     * @return {@link R}
     */
    @RequestMapping("/reSort")
    public R reSort(@RequestBody CategoryEntity[] category){
        categoryService.updateBatchById(Arrays.asList(category));

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
        categoryService.updateCascade(category);

        return R.ok();
    }

    /**
     * 删除
     *
     * @RequestBody：获取请求体，只有POST才有
     * SpringMVC自动将请求体（json）数据转为对象
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
        // 检查当前菜单是否被其他地方引用
        categoryService.removeItemByIds(Arrays.asList(catIds));

//		categoryService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
