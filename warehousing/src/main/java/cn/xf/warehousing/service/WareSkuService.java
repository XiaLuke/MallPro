package cn.xf.warehousing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.warehousing.entity.WareSkuEntity;

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
}

