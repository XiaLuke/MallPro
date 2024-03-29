package cn.xf.search.vo;

import cn.xf.common.es.SkuEsModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResult {

    /**
     * 查询到的所有商品信息
     */
    private List<SkuEsModel> product;


    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页码
     */
    private Integer totalPages;

    private List<Integer> pageNavs;

    /**
     * 当前查询到的结果，所有涉及到的品牌，公共使用
     *
     */
    private List<BrandVo> brands;

    /**
     * 当前查询到的结果，所有涉及到的所有属性
     */
    private List<AttrVo> attrs;

    /**
     * 当前查询到的结果，所有涉及到的所有分类
     */
    private List<CatalogVo> catalogs;


    //===========================以上是返回给页面的所有信息============================//


    /**
     * 面包屑导航数据
     * */
    private List<NavVo> navs = new ArrayList<>();
    private List<Long> attrIds = new ArrayList<>();

    @Data
    public static class NavVo {
        private String navName;
        private String navValue;
        private String link;
    }


    /**
     * 当前查询到的结果所有品牌
     *
     * @author XF
     * @date 2022/09/04
     */
    @Data
    public static class BrandVo {

        private Long brandId;

        private String brandName;

        private String brandImg;
    }


    /**
     * 属性
     *
     * @author XF
     * @date 2022/09/04
     */
    @Data
    public static class AttrVo {

        private Long attrId;

        private String attrName;

        private List<String> attrValue;
    }


    /**
     * 分类
     *
     * @author XF
     * @date 2022/09/04
     */
    @Data
    public static class CatalogVo {

        private Long catalogId;

        private String catalogName;
    }
}