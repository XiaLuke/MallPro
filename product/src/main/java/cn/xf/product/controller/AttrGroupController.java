package cn.xf.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.xf.product.entity.AttrEntity;
import cn.xf.product.service.AttrAttrgroupRelationService;
import cn.xf.product.service.AttrService;
import cn.xf.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.xf.product.entity.AttrGroupEntity;
import cn.xf.product.service.AttrGroupService;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.R;



/**
 * 属性分组
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    AttrService attrService;
    @Autowired
    AttrAttrgroupRelationService relationService;

    @GetMapping("/{attrId}/attr/relation")
    public R attrGroupRelation(@PathVariable("attrId") Long attrId){
        List<AttrEntity> attrDetail = attrService.getRelationDetail(attrId);
        return R.ok().put("data",attrDetail);
    }

    /**
     * 携带三级分类id查询列表
     *
     * @param params 参数个数
     * @return {@link R}
     */
    @RequestMapping("/list/{catelogId}")
    public R listById(@RequestParam Map<String, Object> params,@PathVariable("catelogId")Long catelogId){
        PageUtils page = attrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        // 当前商品所属路径id
        Long catelogId = attrGroup.getCatelogId();
        // 找到当前分类的完整路径
        Long[] path = categoryService.findCatelogPath(catelogId);

        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
