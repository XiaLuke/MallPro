package cn.xf.product.web;

import cn.xf.product.service.SkuInfoService;
import cn.xf.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * 商品详情
 *
 * @author XF
 * @date 2022/09/06
 */
@Controller
public class ItemController {

    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 展示当前产品库存的详情
     * @param skuId
     * @return
     */
    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) throws Exception {

        System.out.println("准备查询" + skuId + "详情");
        SkuItemVo vos = skuInfoService.productInventoryDetails(skuId);
        model.addAttribute("item",vos);

        return "item";
    }
}