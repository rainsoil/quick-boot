package io.github.rainsoil.fastapi2.common.security;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;

/**
 * 国密密码编码器
 *
 * @author luyanan
 * @since 2023/11/28
 **/
public class SmPasswordEncoder implements PasswordEncoder {
	@Value("${sm4.key}")
	private String sm4Key;

	@Override
	public String encode(CharSequence rawPassword) {
		try {
			rawPassword = URLUtil.decode(rawPassword.toString());
			if (StrUtil.isBlank(sm4Key)) {
				throw new RuntimeException("sm4Key 不能为空");
			}
			SM4 sm4 = new SM4(HexUtil.decodeHex(sm4Key));
			rawPassword = sm4.decryptStr(rawPassword.toString());
			// 1. 将 密码 sm3 加密
			String s = SmUtil.sm3(rawPassword.toString());
			// 2. 填充 原密码长度的0
			String fill = StrUtil.fill("", '0', rawPassword.length(), false);
			return SmUtil.sm3(s + "-" + fill);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {

		return this.encode(rawPassword).equals(encodedPassword);
	}
}
