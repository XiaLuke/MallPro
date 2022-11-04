package cn.xf.warehousing.dao;

import cn.xf.warehousing.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 查询商品的总库存量
     *
     * @param skuId sku id
     * @return {@link Long}
     */
    Long getSkuStock(@Param("skuId") Long skuId);

    /**
     * 查询这个商品在哪个仓库有库存
     *
     * @param skuId sku id
     * @return {@link List}<{@link Long}>
     */
    List<Long> listWareIdHasSkuStock(@Param("skuId") Long skuId);

    /**
     * 锁定库存
     *
     * @param skuId  sku id
     * @param wareId 器皿id
     * @param num    全国矿工工会
     * @return {@link Long}
     */
    Long lockSkuStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("num") Integer num);

    /**
     * 商品库存解锁
     *
     * @param skuId  商品id
     * @param wareId 仓库id
     * @param num    锁定数量
     */
    void unLockStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("num") Integer num);
}
