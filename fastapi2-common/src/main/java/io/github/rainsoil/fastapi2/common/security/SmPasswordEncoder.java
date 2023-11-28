package io.github.rainsoil.fastapi2.common.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 国密密码编码器
 *
 * @author luyanan
 * @since 2023/11/28
 **/
public class SmPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        // 1. 将 密码 sm3 加密
        String s = SmUtil.sm3(rawPassword.toString());
        // 2. 填充 原密码长度的0
        String fill = StrUtil.fill("", '0', rawPassword.length(), false);
        return SmUtil.sm3(s + "-" + fill);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return this.encode(rawPassword).equals(encodedPassword);
    }
}
