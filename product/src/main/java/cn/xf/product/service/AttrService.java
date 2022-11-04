package cn.xf.product.service;

import cn.xf.product.vo.AttrGroupRelationVo;
import cn.xf.product.vo.AttrRespVo;
import cn.xf.product.vo.AttributeVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.AttrEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存规格参数
     *
     * @param attr attr
     */
    void saveSpecifications(AttributeVo attr);

    /**
     * 规格属性详细信息
     *
     * @param params    参数个数
     * @param catelogId catelog id
     * @param type      类型(区分规格参数还是销售属性)
     * @return {@link PageUtils}
     */
    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    /**
     * 获取每条规格参数的详细数据
     *
     * @param attrId attr id
     * @return {@link AttrRespVo}
     */
    AttrRespVo getAttrInfo(Long attrId);

    /**
     * 修改规则属性以及分组分类信息
     *
     * @param attr attr
     */
    void updateAttr(AttrRespVo attr);

    /**
     * 获取分组信息关联的规格参数
     *
     * @param attrId attr id
     * @return {@link List}<{@link AttrEntity}>
     */
    List<AttrEntity> getRelationDetail(Long attrId);

    /**
     * 获取当前选中的分组下还没有关联的规格参数
     *
     * @param attrGroupId 属性分组id
     * @param map         数据集合
     * @return {@link PageUtils}
     */
    PageUtils getWithOutRelationByGroupId(Long attrGroupId, Map<String, Object> map);

    /**
     * 根据分组id查找关联的所有基本属性
     *
     * @param attrGroupId attr组id
     * @return {@link List}<{@link AttrEntity}>
     */
    List<AttrEntity> getRelationAttr(Long attrGroupId);

    List<Long> selectSearchAttrs(List<Long> attrIds);

}

