package io.github.rainsoil.fastboot.common.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 国密密码编码器
 *
 * @author luyanan
 * @since 2023/11/28
 **/
public class SmPasswordEncoder implements PasswordEncoder {
	@Value("${sm4.key}")
	private String sm4Key;

	@Autowired
	private ClientUtils clientUtils;

	@Override
	public String encode(CharSequence rawPassword) {
		try {
			if(null != clientUtils){
				rawPassword = clientUtils.paramHandler(rawPassword.toString());
			}


			// 1. 将 密码 sm3 加密
			String s = SmUtil.sm3(rawPassword.toString());
			// 2. 填充 原密码长度的0
			String fill = StrUtil.fill("", '0', rawPassword.length(), false);
			return SmUtil.sm3(s + "-" + fill);
		} catch (RuntimeException e) {
//			e.printStackTrace();

		}
		return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {

		return this.encode(rawPassword).equals(encodedPassword);
	}
}
