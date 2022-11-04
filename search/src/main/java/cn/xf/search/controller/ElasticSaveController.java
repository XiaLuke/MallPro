package cn.xf.search.controller;

import cn.xf.common.es.SkuEsModel;
import cn.xf.common.exception.CommonExceptionCode;
import cn.xf.common.utils.R;
import cn.xf.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * es Controller
 *
 * @author XF
 * @date 2022/08/25
 */
@Slf4j
@RequestMapping(value = "/search/save")
@RestController
public class ElasticSaveController {

    @Autowired
    private ProductSaveService productSaveService;


    /**
     * 上架商品
     *
     * @param skuEsModels
     * @return
     */
    @PostMapping(value = "/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {
        boolean status = false;
        try {
            status = productSaveService.productStatusUp(skuEsModels);
        } catch (IOException e) {
            return R.error(CommonExceptionCode.PRODUCT_UP_EXCEPTION.getCode(), CommonExceptionCode.PRODUCT_UP_EXCEPTION.getMsg());
        }
        if (status) {
            return R.error(CommonExceptionCode.PRODUCT_UP_EXCEPTION.getCode(), CommonExceptionCode.PRODUCT_UP_EXCEPTION.getMsg());
        } else {
            return R.ok();
        }
    }
}
