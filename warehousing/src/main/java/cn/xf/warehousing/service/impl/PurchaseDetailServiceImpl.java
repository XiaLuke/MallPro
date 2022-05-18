package cn.xf.warehousing.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.Query;

import cn.xf.warehousing.dao.PurchaseDetailDao;
import cn.xf.warehousing.entity.PurchaseDetailEntity;
import cn.xf.warehousing.service.PurchaseDetailService;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    /**
     * 原生查询页面
     *
     * @param params 参数个数
     * @return {@link PageUtils}
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                new QueryWrapper<PurchaseDetailEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 通过条件查询页面
     *
     * @param params 参数个数
     * @return {@link PageUtils}
     */
    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<PurchaseDetailEntity>();

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and(wrapper -> {
                wrapper.eq("purchase_id",key).or().eq("sku_id",key);
            });
        }

        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status) && !"0".equalsIgnoreCase(status)) {
            queryWrapper.eq("status",status);
        }

        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(wareId) && !"0".equalsIgnoreCase(wareId)) {
            queryWrapper.eq("ware_id",wareId);
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<PurchaseDetailEntity> listDetailByPurchaseId(Long id) {
        List<PurchaseDetailEntity> purchaseId = this.list(
                new QueryWrapper<PurchaseDetailEntity>().eq("purchase_id", id));

        return purchaseId;
    }

}