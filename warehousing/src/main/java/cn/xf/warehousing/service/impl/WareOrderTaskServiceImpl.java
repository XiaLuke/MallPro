package cn.xf.warehousing.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.Query;

import cn.xf.warehousing.dao.WareOrderTaskDao;
import cn.xf.warehousing.entity.WareOrderTaskEntity;
import cn.xf.warehousing.service.WareOrderTaskService;


@Service("wareOrderTaskService")
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskDao, WareOrderTaskEntity> implements WareOrderTaskService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareOrderTaskEntity> page = this.page(
                new Query<WareOrderTaskEntity>().getPage(params),
                new QueryWrapper<WareOrderTaskEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据订单号查询仓库工作单
     *
     * @param orderSn 订单号
     * @return {@link WareOrderTaskEntity}
     */
    @Override
    public WareOrderTaskEntity getByOrderSn(String orderSn) {
        return baseMapper.selectOne(new QueryWrapper<WareOrderTaskEntity>().eq("order_sn",orderSn));
    }

}