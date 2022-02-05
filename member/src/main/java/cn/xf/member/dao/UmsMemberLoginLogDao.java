package cn.xf.member.dao;

import cn.xf.member.entity.UmsMemberLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录记录
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:59
 */
@Mapper
public interface UmsMemberLoginLogDao extends BaseMapper<UmsMemberLoginLogEntity> {
	
}
