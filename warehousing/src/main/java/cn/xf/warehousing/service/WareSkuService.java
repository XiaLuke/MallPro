package cn.xf.warehousing.service;

import cn.xf.common.to.SkuHasStockVo;
import cn.xf.common.to.mq.OrderTo;
import cn.xf.common.to.mq.StockLockedTo;
import cn.xf.warehousing.vo.WareSkuLockVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.warehousing.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 23:13:33
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 商品入库
     *
     * @param skuId  商品id
     * @param wareId 仓库id
     * @param skuNum 商品入库数量
     */
    void addStock(Long skuId, Long wareId, Integer skuNum);

    /**
     * 查询商品是否有库存
     *
     * @param skuIds sku id
     * @return {@link List}<{@link SkuHasStockVo}>
     */
    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

    /**
     * 订单下单锁定库存
     *
     * @param vo 签证官
     * @return boolean
     */
    boolean orderLockStock(WareSkuLockVo vo);


    /**
     * 释放消息队列和解锁库存
     *
     * @param to 来
     */
    void releaseAndUnlock(StockLockedTo to);

    /**
     * 库存释放
     * -- 接收到从订单模块释放订单时发送过来的释放库存请求
     * @param order
     */
    void releaseAndUnlock(OrderTo order);

}

