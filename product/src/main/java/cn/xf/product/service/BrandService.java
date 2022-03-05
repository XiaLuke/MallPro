package cn.xf.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 更新品牌信息及关联信息
     *
     * @param brand 品牌
     */
    void updateDetail(BrandEntity brand);
}

