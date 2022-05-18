package cn.xf.warehousing.dao;

import cn.xf.warehousing.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 23:13:33
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    /**
     * 商品入库
     *
     * @param skuId  商品id
     * @param wareId 仓库id
     * @param skuNum 商品数量
     */
    void addStock(Long skuId, Long wareId, Integer skuNum);
}
