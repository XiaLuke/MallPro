package cn.xf.warehousing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.warehousing.entity.WmsPurchaseEntity;

import java.util.Map;

/**
 * 采购信息
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:38:31
 */
public interface WmsPurchaseService extends IService<WmsPurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

