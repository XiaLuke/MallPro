package cn.xf.product.dao;

import cn.xf.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
