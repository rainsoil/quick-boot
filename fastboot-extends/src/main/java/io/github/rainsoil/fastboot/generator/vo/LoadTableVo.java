package io.github.rainsoil.fastboot.generator.vo;

import io.github.rainsoil.fastboot.common.validation.AddGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 导入表的vo类
 *
 * @author luyanan
 * @since 2024/01/21
 **/
@Data
public class LoadTableVo implements Serializable {


	/**
	 * 数据源id
	 *
	 * @since 2024/01/21
	 */
	@NotNull(message = "datasource.require", groups = AddGroup.class)
	@ApiModelProperty(value = "数据源id")
	private Long datasourceId;

	/**
	 * 表
	 *
	 * @since 2024/01/21
	 */
	@ApiModelProperty(value = "表")
	@NotBlank(message = "tableName.require", groups = AddGroup.class)
	private String tableName;
}
