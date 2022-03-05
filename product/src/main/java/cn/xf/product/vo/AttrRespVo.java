package cn.xf.product.vo;

import lombok.Data;

/**
 * 规格参数返回数据封装
 *
 * @author XF
 * @date 2022/02/27
 */
@Data
public class AttrRespVo extends AttributeVo {
    /**
     * 			"catelogName": "手机/数码/手机", //所属分类名字
     * 			"groupName": "主体", //所属分组名字
     */
    private String catelogName;
    private String groupName;

    /**
     * 分类的完整路径
     */
    private Long[] catelogPath;
}
