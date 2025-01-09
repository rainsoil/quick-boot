package com.su60.quickboot.security.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 用户信息
 *
 * @author luyanan
 * @since 2024/07/19
 **/
@Data
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

	/**
	 * 密码
	 *
	 * @since 2024/07/19
	 */

	private String password;


	/**
	 * 用户名
	 *
	 * @since 2024/07/19
	 */

	private String username;


	/**
	 * 存储用户权限的集合，这些权限以GrantedAuthority接口的实现类形式表示。
	 * 授权信息用于在应用程序中识别用户的权限，以决定他们是否被允许执行特定的操作。
	 */
	private Collection<? extends GrantedAuthority> authorities;


	/**
	 * 是否账户未过期的标志。
	 * 该属性用于标识用户账户是否处于非过期状态。
	 * 如果账户过期，该值为false；如果账户未过期，该值为true。
	 * 这个标志对于验证用户身份和访问权限时非常关键。
	 */
	private boolean accountNonExpired;


	/**
	 * 标识账户是否未被锁定。
	 * <p>
	 * 该属性用于判断账户是否处于锁定状态，锁定的账户无法进行登录或其他需要验证身份的操作。
	 * 锁定通常是因为多次尝试登录失败或其他违反使用政策的行为导致。
	 * </p>
	 */
	private boolean accountNonLocked;


	/**
	 * 表示用户凭证是否过期的标志。
	 * <p>
	 * 该字段用于跟踪用户凭证的有效性，值为true表示凭证未过期，值为false表示凭证已过期。
	 * 对于身份验证系统来说，这是一个重要的属性，用于决定是否需要重新认证用户。
	 */
	private boolean credentialsNonExpired;

	/**
	 * 是否启用
	 *
	 * @since 2024/07/19
	 */

	private boolean enabled;

	/**
	 * 客户端
	 *
	 * @since 2024/07/19
	 */

	private String clientId;
}
