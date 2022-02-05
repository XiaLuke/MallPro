package cn.xf.warehousing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.warehousing.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:38:30
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

