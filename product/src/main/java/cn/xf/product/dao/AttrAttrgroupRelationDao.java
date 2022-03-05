package cn.xf.product.dao;

import cn.xf.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    /**
     * 商品属性与规格参数关联的删除方法
     *
     * @param entities 商品与规格参数
     */
    void batchDelete(@Param("entities") List<AttrAttrgroupRelationEntity> entities);
}
