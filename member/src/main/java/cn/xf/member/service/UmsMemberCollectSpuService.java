package cn.xf.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.member.entity.UmsMemberCollectSpuEntity;

import java.util.Map;

/**
 * 会员收藏的商品
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:59
 */
public interface UmsMemberCollectSpuService extends IService<UmsMemberCollectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

