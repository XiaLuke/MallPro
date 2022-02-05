package cn.xf.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.Query;

import cn.xf.order.dao.OmsOrderSettingDao;
import cn.xf.order.entity.OmsOrderSettingEntity;
import cn.xf.order.service.OmsOrderSettingService;


@Service("omsOrderSettingService")
public class OmsOrderSettingServiceImpl extends ServiceImpl<OmsOrderSettingDao, OmsOrderSettingEntity> implements OmsOrderSettingService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OmsOrderSettingEntity> page = this.page(
                new Query<OmsOrderSettingEntity>().getPage(params),
                new QueryWrapper<OmsOrderSettingEntity>()
        );

        return new PageUtils(page);
    }

}