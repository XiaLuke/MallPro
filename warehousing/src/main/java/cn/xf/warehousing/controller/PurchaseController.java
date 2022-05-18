package cn.xf.warehousing.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import cn.xf.warehousing.vo.MergeVo;
import cn.xf.warehousing.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.xf.warehousing.entity.PurchaseEntity;
import cn.xf.warehousing.service.PurchaseService;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.R;



/**
 * 采购信息
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 23:13:33
 */
@RestController
@RequestMapping("warehousing/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("warehousing:purchase:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("warehousing:purchase:info")
    public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("warehousing:purchase:save")
    public R save(@RequestBody PurchaseEntity purchase){
        purchase.setCreateTime(new Date());
        purchase.setUpdateTime(new Date());
		purchaseService.save(purchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("warehousing:purchase:update")
    public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("warehousing:purchase:delete")
    public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 查询新建且没有被领取的采购单
     *
     * @param params 参数个数
     * @return {@link R}
     */
    @GetMapping(value = "/notReceived/list")
    public R notReceivedList(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPageNotReceived(params);

        return R.ok().put("page", page);
    }

    /**
     * 合并采购单
     *
     * @param mergeVo 合并签证官
     * @return {@link R}
     */
    @PostMapping(value = "/merge")
    public R merge(@RequestBody MergeVo mergeVo) {
        purchaseService.mergePurchase(mergeVo);
        return R.ok();
    }

    /**
     * 领取采购单
     *
     * @param ids id
     * @return {@link R}
     */
    @PostMapping(value = "/received")
    public R received(@RequestBody List<Long> ids) {
        purchaseService.received(ids);
        return R.ok();
    }

    /**
     * 完成采购单
     * @param doneVo
     * @return
     */
    @PostMapping(value = "/done")
    public R finish(@RequestBody PurchaseDoneVo doneVo) {
        purchaseService.done(doneVo);

        return R.ok();
    }
}
