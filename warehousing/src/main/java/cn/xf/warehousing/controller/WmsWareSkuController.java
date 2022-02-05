package cn.xf.warehousing.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.xf.warehousing.entity.WmsWareSkuEntity;
import cn.xf.warehousing.service.WmsWareSkuService;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.R;



/**
 * 商品库存
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:38:31
 */
@RestController
@RequestMapping("warehousing/wmswaresku")
public class WmsWareSkuController {
    @Autowired
    private WmsWareSkuService wmsWareSkuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("warehousing:wmswaresku:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wmsWareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("warehousing:wmswaresku:info")
    public R info(@PathVariable("id") Long id){
		WmsWareSkuEntity wmsWareSku = wmsWareSkuService.getById(id);

        return R.ok().put("wmsWareSku", wmsWareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("warehousing:wmswaresku:save")
    public R save(@RequestBody WmsWareSkuEntity wmsWareSku){
		wmsWareSkuService.save(wmsWareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("warehousing:wmswaresku:update")
    public R update(@RequestBody WmsWareSkuEntity wmsWareSku){
		wmsWareSkuService.updateById(wmsWareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("warehousing:wmswaresku:delete")
    public R delete(@RequestBody Long[] ids){
		wmsWareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
