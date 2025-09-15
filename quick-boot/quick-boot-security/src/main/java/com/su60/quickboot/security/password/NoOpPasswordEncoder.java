package com.su60.quickboot.security.password;

import lombok.extern.slf4j.Slf4j;

/**
 * 无操作密码编码器
 * 用于向后兼容，不进行任何编码
 * 注意：此编码器不安全，仅用于兼容旧系统
 *
 * @author luyanan
 * @since 2024/12/19
 */
@Slf4j
public class NoOpPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        
        log.warn("使用NoOpPasswordEncoder编码密码，这是不安全的！");
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null && encodedPassword == null) {
            return true;
        }
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        
        log.warn("使用NoOpPasswordEncoder验证密码，这是不安全的！");
        return rawPassword.toString().equals(encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        // 总是需要升级到更安全的算法
        return true;
    }
}
