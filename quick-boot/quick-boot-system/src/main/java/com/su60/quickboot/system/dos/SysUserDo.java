package com.su60.quickboot.system.dos;

import com.su60.quickboot.common.validation.AddGroup;
import com.su60.quickboot.common.validation.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author luyanan
 * @since 2024/08/07
 */
@Data
@Accessors(chain = true)
public class SysUserDo implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;


	/**
	 * 用户ID
	 *
	 * @since 2024/08/07
	 */
	@NotNull(groups = UpdateGroup.class, message = "用户ID 不能为空")
	private Long id;


	/**
	 * 用户账号
	 *
	 * @since 2024/08/07
	 */
	@NotBlank(groups = AddGroup.class, message = "用户账号不能为空")
	private String userName;


	/**
	 * 用户昵称
	 *
	 * @since 2024/08/07
	 */
	@NotBlank(groups = AddGroup.class, message = "用户昵称不能为空")
	private String nickName;


	/**
	 * 用户类型（00系统用户）
	 *
	 * @since 2024/08/07
	 */
	private String userType;


	/**
	 * 用户邮箱
	 *
	 * @since 2024/08/07
	 */
	private String email;


	/**
	 * 手机号码
	 *
	 * @since 2024/08/07
	 */
	private String phonenumber;


	/**
	 * 用户性别（0男 1女 2未知）
	 *
	 * @since 2024/08/07
	 */
	private String sex;


	/**
	 * 头像地址
	 *
	 * @since 2024/08/07
	 */
	private String avatar;


	/**
	 * 密码
	 *
	 * @since 2024/08/07
	 */
	@NotBlank(message = "密码不能为空", groups = {AddGroup.class})
	private String password;


	/**
	 * 帐号状态（0正常 1停用）
	 *
	 * @since 2024/08/07
	 */
	@NotBlank(message = "状态不能为空", groups = {AddGroup.class})
	private String status;


	/**
	 * 删除标志（0代表存在 2代表删除）
	 *
	 * @since 2024/08/07
	 */
	@JsonIgnore
	private String delFlag;


	/**
	 * 最后登录IP
	 *
	 * @since 2024/08/07
	 */
	private String loginIp;


	/**
	 * 最后登录时间
	 *
	 * @since 2024/08/07
	 */
	private Date loginDate;


	/**
	 * 创建者
	 *
	 * @since 2024/08/07
	 */
	@JsonIgnore
	private Long createBy;


	/**
	 * 创建时间
	 *
	 * @since 2024/08/07
	 */
	private Date createTime;


	/**
	 * 更新者
	 *
	 * @since 2024/08/07
	 */
	@JsonIgnore
	private Long updateBy;


	/**
	 * 更新时间
	 *
	 * @since 2024/08/07
	 */
	@JsonIgnore
	private Date updateTime;


	/**
	 * 备注
	 *
	 * @since 2024/08/07
	 */
	private String remark;

	/**
	 * 关联的角色id
	 *
	 * @since 2024/10/14
	 */

	private List<Long> roleIds;


	/**
	 * 角色名称
	 *
	 * @since 2024/10/14
	 */

	private String roleNames;

}
