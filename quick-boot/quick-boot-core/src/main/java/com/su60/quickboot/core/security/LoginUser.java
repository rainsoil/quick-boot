package com.su60.quickboot.core.security;

import cn.hutool.core.collection.CollectionUtil;
import com.su60.quickboot.security.user.UserDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录用户
 *
 * @author luyanan
 * @since 2024/06/30
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginUser extends UserDetails {


	/**
	 * id
	 *
	 * @since 2024/06/29
	 */

	private Long id;


	/**
	 * 创建时间
	 *
	 * @since 2024/06/29
	 */

	private Date createTime;


	/**
	 * 用户名
	 *
	 * @since 2024/06/29
	 */

	private String username;


	/**
	 * 昵称
	 *
	 * @since 2024/06/29
	 */

	private String nickname;


	/**
	 * 性别
	 *
	 * @since 2024/06/29
	 */

	private String gender;


	/**
	 * 头像
	 *
	 * @since 2024/06/29
	 */

	private String avatar;


	/**
	 * 状态(USER_STATUS)
	 *
	 * @since 2024/06/29
	 */

	private String status;


	/**
	 * 密码
	 *
	 * @since 2024/06/29
	 */

	private String password;


	/**
	 * 角色编码集合
	 *
	 * @since 2024/07/05
	 */

	private List<String> roles;


	/**
	 * 路由id集合
	 *
	 * @since 2024/07/20
	 */

	private List<Long> roleIds;


	/**
	 * 权限标识集合
	 *
	 * @since 2024/07/05
	 */

	private List<String> perms;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (CollectionUtil.isEmpty(this.perms)) {
			return new ArrayList<>();
		}
		return this.perms.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.status.equals("0");
	}


}
