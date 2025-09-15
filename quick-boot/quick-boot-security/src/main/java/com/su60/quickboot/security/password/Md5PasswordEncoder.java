package com.su60.quickboot.security.password;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * MD5密码编码器
 * 注意：MD5不安全，仅用于演示和兼容旧系统
 *
 * @author luyanan
 * @since 2024/12/19
 */
@Slf4j
public class Md5PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        
        try {
            return DigestUtil.md5Hex(rawPassword.toString());
        } catch (Exception e) {
            log.error("MD5密码编码失败", e);
            throw new RuntimeException("MD5密码编码失败", e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            return false;
        }
        if (encodedPassword == null || encodedPassword.length() == 0) {
            log.warn("Empty encoded password");
            return false;
        }

        try {
            String encoded = DigestUtil.md5Hex(rawPassword.toString());
            return encoded.equals(encodedPassword);
        } catch (Exception e) {
            log.error("MD5密码验证失败", e);
            return false;
        }
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        // MD5总是需要升级到更安全的算法
        return true;
    }
}
