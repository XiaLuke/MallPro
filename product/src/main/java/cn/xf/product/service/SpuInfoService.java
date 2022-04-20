package cn.xf.product.service;

import cn.xf.product.vo.SpuSaveVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存添加商品信息
     *
     * @param spuInfoVo spu信息签证官
     */
    void saveSpuInfo(SpuSaveVo spuInfoVo);

    /**
     * spu信息保存
     *
     * @param infoEntity 信息实体
     */
    void saveBaseSpuInfo(SpuInfoEntity infoEntity);
}

