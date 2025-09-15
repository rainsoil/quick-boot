package com.su60.quickboot.security.password;

import cn.hutool.crypto.digest.BCrypt;
import lombok.extern.slf4j.Slf4j;

/**
 * BCrypt密码编码器实现
 * 使用Hutool的BCrypt实现
 *
 * @author luyanan
 * @since 2024/12/19
 */
@Slf4j
public class BCryptPasswordEncoder implements PasswordEncoder {

    private final int strength;

    /**
     * 默认构造函数，使用默认强度
     */
    public BCryptPasswordEncoder() {
        this(-1);
    }

    /**
     * 构造函数
     *
     * @param strength BCrypt强度，-1表示使用默认强度
     */
    public BCryptPasswordEncoder(int strength) {
        this.strength = strength;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        
        try {
            if (strength > 0) {
                // 使用指定强度
                return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(strength));
            } else {
                // 使用默认强度
                return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt());
            }
        } catch (Exception e) {
            log.error("密码编码失败", e);
            throw new RuntimeException("密码编码失败", e);
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
            return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
        } catch (Exception e) {
            log.error("密码验证失败", e);
            return false;
        }
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.length() == 0) {
            return false;
        }
        
        // 检查是否需要升级编码强度
        // BCrypt的格式是 $2a$strength$...
        if (encodedPassword.startsWith("$2a$")) {
            try {
                String[] parts = encodedPassword.split("\\$");
                if (parts.length >= 3) {
                    int currentStrength = Integer.parseInt(parts[2]);
                    return currentStrength < strength;
                }
            } catch (NumberFormatException e) {
                log.warn("无法解析BCrypt强度: {}", encodedPassword);
            }
        }
        
        return false;
    }
}
