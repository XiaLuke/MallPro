package cn.xf.product.entity;

import cn.xf.common.validate.AddGroup;
import cn.xf.common.validate.ListValue;
import cn.xf.common.validate.UpdateGroup;
import cn.xf.common.validate.UpdateStatusGroup;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 品牌
 * 
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 16:40:38
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 * @NotBlank：不为空或使用NotEmpty，NotNull
	 */
	@NotBlank(message = "名字不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@URL(message = "图片需要是合法url地址",groups = {AddGroup.class, UpdateGroup.class})
	@NotBlank(message = "图片地址不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@NotNull(groups = {AddGroup.class, UpdateStatusGroup.class})
	@ListValue(value = {0,1},groups = {AddGroup.class, UpdateStatusGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@Pattern(regexp = "^[a-zA-Z]$",message = "首字母必须为字母",groups = {AddGroup.class, UpdateGroup.class})
	@NotBlank(message = "首字母不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@Min(value = 0,message = "排序必须大于登录0",groups = {AddGroup.class, UpdateGroup.class})
	@NotNull(message = "排序不能为空",groups = {AddGroup.class, UpdateGroup.class})
	private Integer sort;

}
