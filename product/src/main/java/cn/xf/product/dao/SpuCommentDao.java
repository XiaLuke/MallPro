package cn.xf.product.dao;

import cn.xf.product.entity.SpuCommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
@Mapper
public interface SpuCommentDao extends BaseMapper<SpuCommentEntity> {
	
}
