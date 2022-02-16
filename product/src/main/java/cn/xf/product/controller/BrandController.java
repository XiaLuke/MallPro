package cn.xf.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.xf.common.validate.AddGroup;
import cn.xf.common.validate.UpdateGroup;
import cn.xf.common.validate.UpdateStatusGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.xf.product.entity.BrandEntity;
import cn.xf.product.service.BrandService;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated(value = {AddGroup.class}) @RequestBody BrandEntity brand) {
        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@Validated(value = {UpdateGroup.class}) @RequestBody BrandEntity brand) {
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 是否启用
     *
     * @param brand 品牌
     * @return {@link R}
     */
    @RequestMapping("/changeStatus")
    public R whetherToEnable(@Validated(value = {UpdateStatusGroup.class}) @RequestBody BrandEntity brand) {
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
