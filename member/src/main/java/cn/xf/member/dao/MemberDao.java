package cn.xf.member.dao;

import cn.xf.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 会员
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 22:27:35
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {

}
