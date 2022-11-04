package cn.xf.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.product.entity.BrandEntity;

import java.util.List;
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

    /**
     * 根据品牌id集合查询品牌信息
     *
     * @param brandIds 品牌标识
     * @return {@link List}<{@link BrandEntity}>
     */
    List<BrandEntity> getBrandInfosByBrandIds(List<Long> brandIds);
}

