package cn.xf.search.vo;

import lombok.Data;

/**
 * 商品信息返回接收
 *
 * @author XF
 * @date 2022/09/05
 */
@Data
public class BrandVo {

    /**
     * "brandId": 0,
     * "brandName": "string",
     */
    private Long brandId;
    private String  brandName;
}