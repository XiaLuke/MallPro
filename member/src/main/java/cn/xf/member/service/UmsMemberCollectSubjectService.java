package cn.xf.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.member.entity.UmsMemberCollectSubjectEntity;

import java.util.Map;

/**
 * 会员收藏的专题活动
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:59
 */
public interface UmsMemberCollectSubjectService extends IService<UmsMemberCollectSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

