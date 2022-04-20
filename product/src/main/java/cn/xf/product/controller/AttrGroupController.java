package cn.xf.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.xf.product.entity.AttrEntity;
import cn.xf.product.service.AttrAttrgroupRelationService;
import cn.xf.product.service.AttrService;
import cn.xf.product.service.CategoryService;
import cn.xf.product.vo.AttrGroupRelationVo;
import cn.xf.product.vo.AttrGroupWithAttrsVo;
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

    @GetMapping("/{attrGroupId}/attr/relation")
    public R attrGroupRelation(@PathVariable("attrGroupId") Long attrId){
        List<AttrEntity> attrDetail = attrService.getRelationDetail(attrId);
        return R.ok().put("data",attrDetail);
    }

    /**
     * 获取当前分组下没有进行关联的规格参数列表
     *
     * @param attrGroupId 当前分组的id
     * @return {@link R}
     */
    @GetMapping("/{attrGroupId}/noattr/relation")
    public R attrGroupWithOutRelation(@PathVariable("attrGroupId") Long attrGroupId,
                                      @RequestParam Map<String,Object> map){
        PageUtils page = attrService.getWithOutRelationByGroupId(attrGroupId,map);
        return R.ok().put("page",page);
    }

    /**
     * 根据分类查询获取所有分组与关联属性
     * */
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId")Long catelogId){

        //1、查出当前分类下的所有属性分组，
        //2、查出每个属性分组的所有属性
        List<AttrGroupWithAttrsVo> vos =  attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return R.ok().put("data",vos);
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

    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] attrGroupRelationVo){
        attrGroupService.deleteRelation(attrGroupRelationVo);
        return R.ok();
    }

}
