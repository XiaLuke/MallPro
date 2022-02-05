package cn.xf.member.dao;

import cn.xf.member.entity.UmsMemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:39:58
 */
@Mapper
public interface UmsMemberDao extends BaseMapper<UmsMemberEntity> {
	
}
