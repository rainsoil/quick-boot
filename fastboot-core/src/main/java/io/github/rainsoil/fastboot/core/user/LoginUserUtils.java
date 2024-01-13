package io.github.rainsoil.fastboot.core.user;

import com.alibaba.fastjson.JSON;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 登录用户工具类
 *
 * @author luyanan
 * @since 2023/11/30
 **/
@UtilityClass
public class LoginUserUtils {


	/**
	 * 获取当前登录的用户
	 *
	 * @return 当前登录用户
	 * @since 2023/11/30
	 */
	public LoginUser getUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (null == context) {
			return null;
		}
		Authentication authentication = context.getAuthentication();
		if (null == authentication) {
			return null;
		}
		Object details = authentication.getPrincipal();
		if (null == details) {
			return null;
		}
		return JSON.parseObject(details.toString(), LoginUser.class);

	}
}
