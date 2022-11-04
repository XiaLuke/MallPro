package cn.xf.product.dao;

import cn.xf.product.entity.AttrGroupEntity;
import cn.xf.product.vo.SpuItemAttrGroupVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    /**
     * 根据商品id查询商品规格参数信息
     *
     * @param productId 产品id
     * @param catalogId 目录id
     * @return {@link List}<{@link SpuItemAttrGroupVo}>
     */
    List<SpuItemAttrGroupVo> getProductSpecificationsByProductId(@Param("productId") Long productId, @Param("catalogId") Long catalogId);
}
