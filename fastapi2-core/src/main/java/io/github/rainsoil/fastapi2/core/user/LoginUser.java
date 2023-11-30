package io.github.rainsoil.fastapi2.core.user;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录用户
 *
 * @author luyanan
 * @since 2023/11/29
 **/
@Data
public class LoginUser implements UserDetails, Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 *
	 * @since 2023/11/29
	 */

	private Long userId;


	/**
	 * 用户名称
	 *
	 * @since 2023/11/29
	 */

	private String username;


	/**
	 * 密码
	 *
	 * @since 2023/11/29
	 */
	@JsonIgnore
	private String password;


	/**
	 * 姓名
	 *
	 * @since 2023/11/30
	 */

	private String nickName;
	/**
	 * 角色编码
	 *
	 * @since 2023/11/29
	 */

	private List<String> roleCodes;


	/**
	 * 角色id
	 *
	 * @since 2023/11/29
	 */

	private List<Long> roles;


	/**
	 * 授权
	 *
	 * @since 2023/11/29
	 */

	private List<String> auths;


	/**
	 * 客户端id
	 *
	 * @since 2023/11/30
	 */

	private String clientId;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (CollUtil.isEmpty(this.auths)) {
			return null;
		}
		return AuthorityUtils.createAuthorityList(this.auths.stream().collect(Collectors.joining(",")));
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
		return true;
	}
}
