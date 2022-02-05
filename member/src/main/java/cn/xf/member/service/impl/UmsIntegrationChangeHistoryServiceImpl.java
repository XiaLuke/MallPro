package cn.xf.member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.xf.common.utils.PageUtils;
import cn.xf.common.utils.Query;

import cn.xf.member.dao.UmsIntegrationChangeHistoryDao;
import cn.xf.member.entity.UmsIntegrationChangeHistoryEntity;
import cn.xf.member.service.UmsIntegrationChangeHistoryService;


@Service("umsIntegrationChangeHistoryService")
public class UmsIntegrationChangeHistoryServiceImpl extends ServiceImpl<UmsIntegrationChangeHistoryDao, UmsIntegrationChangeHistoryEntity> implements UmsIntegrationChangeHistoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UmsIntegrationChangeHistoryEntity> page = this.page(
                new Query<UmsIntegrationChangeHistoryEntity>().getPage(params),
                new QueryWrapper<UmsIntegrationChangeHistoryEntity>()
        );

        return new PageUtils(page);
    }

}