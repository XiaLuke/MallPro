package cn.xf.product.service;

import cn.xf.product.vo.AttrGroupRelationVo;
import cn.xf.product.vo.AttrGroupWithAttrsVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.AttrGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {


    PageUtils queryPage(Map<String, Object> params);

    /**
     * 通过三级分类id查询列表
     *
     * @param params    参数个数
     * @param catelogId catelog id
     * @return {@link PageUtils}
     */
    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    /**
     * 删除商品属性与规格参数的中间信息
     *
     * @param attrGroupRelationVo 属性分组与规格参数
     */
    void deleteRelation(AttrGroupRelationVo[] attrGroupRelationVo);

    /**
     * 根据分类id查出所有的分组以及这些组里面的属性
     *
     * @param catelogId catelog id
     * @return {@link List}<{@link AttrGroupWithAttrsVo}>
     */
    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);


//    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);

}

