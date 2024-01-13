package io.github.rainsoil.fastboot.system.vo;

import io.github.rainsoil.fastboot.common.validation.ValidatorGroup1;
import io.github.rainsoil.fastboot.common.validation.ValidatorGroup2;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改密码
 *
 * @author luyanan
 * @since 2024/01/11
 **/
@Data
@Accessors(chain = true)
public class ChangePasswordVo implements Serializable {


	/**
	 * 用户id
	 *
	 * @since 2024/01/11
	 */
	@NotNull(message = "{userId.require}", groups = {ValidatorGroup1.class})
	@ApiModelProperty(value = "用户id")
	private Long userId;

	/**
	 * 旧密码
	 *
	 * @since 2024/01/11
	 */
	@NotNull(message = "{changepasswordvo.oldpassword.require}", groups = { ValidatorGroup2.class})
	@ApiModelProperty(value = "旧密码")
	private String oldpassword;

	/**
	 * 新密码
	 *
	 * @since 2024/01/11
	 */
	@NotNull(message = "{changepassword.newpassword.require}", groups = {ValidatorGroup1.class, ValidatorGroup2.class})
	@ApiModelProperty(value = "新密码")
	private String newpassword;


	/**
	 * 确认新密码
	 *
	 * @since 2024/01/11
	 */
	@NotNull(message = "{changepasswordvo.newpasswords.require}", groups = { ValidatorGroup2.class})
	@ApiModelProperty(value = "确认新密码")
	private String newpasswords;
}
