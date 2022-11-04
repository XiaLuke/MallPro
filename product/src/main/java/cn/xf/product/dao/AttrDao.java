package cn.xf.product.dao;

import cn.xf.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    /**
     * 根据商品属性id查询需要检索的商品属性
     *
     * @param attrIds attr id
     * @return {@link List}<{@link Long}>
     */
    List<Long> selectSearchAttrIds(@Param("ids") List<Long> attrIds);
}
