package io.github.rainsoil.fastboot.system.serverless.function;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 函数请求参数
 *
 * @author luyanan
 * @since 2023/12/27
 **/
@Data
@Accessors(chain = true)
public class FunctionRequest implements Serializable {


	/**
	 * 函数目标
	 *
	 * @since 2023/12/27
	 */
	@ApiModelProperty(value = "函数目标")
	private String functionTarget;

	/**
	 * 函数方法
	 *
	 * @since 2023/12/27
	 */
	@ApiModelProperty(value = "函数方法")
	private String functionMethod;


	/**
	 * 函数参数
	 *
	 * @since 2023/12/27
	 */
	@ApiModelProperty(value = "函数参数")
	private List<String> functionParams;
}
