package cn.xf.product.dao;

import cn.xf.product.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spu信息
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

    /**
     * 修改当前商品状态
     *
     * @param spuId spu id
     * @param code  代码
     */
    void updateSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);
}
