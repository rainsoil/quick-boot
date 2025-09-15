package com.su60.quickboot.security.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 密码工具类
 * 提供静态方法访问密码编码器
 *
 * @author luyanan
 * @since 2024/12/19
 */
@Component
public class PasswordUtils {

    private static PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        PasswordUtils.passwordEncoder = passwordEncoder;
    }

    /**
     * 编码密码
     *
     * @param rawPassword 原始密码
     * @return 编码后的密码
     */
    public static String encode(CharSequence rawPassword) {
        if (passwordEncoder == null) {
            throw new IllegalStateException("PasswordEncoder未初始化");
        }
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 验证密码
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 编码后的密码
     * @return 是否匹配
     */
    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (passwordEncoder == null) {
            throw new IllegalStateException("PasswordEncoder未初始化");
        }
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 检查是否需要升级编码
     *
     * @param encodedPassword 编码后的密码
     * @return 是否需要升级
     */
    public static boolean upgradeEncoding(String encodedPassword) {
        if (passwordEncoder == null) {
            throw new IllegalStateException("PasswordEncoder未初始化");
        }
        return passwordEncoder.upgradeEncoding(encodedPassword);
    }

    /**
     * 获取当前使用的密码编码器
     *
     * @return 密码编码器
     */
    public static PasswordEncoder getPasswordEncoder() {
        if (passwordEncoder == null) {
            throw new IllegalStateException("PasswordEncoder未初始化");
        }
        return passwordEncoder;
    }

    /**
     * 检查密码是否使用了不安全的编码方式
     *
     * @param encodedPassword 编码后的密码
     * @return 是否不安全
     */
    public static boolean isUnsafeEncoding(String encodedPassword) {
        if (encodedPassword == null) {
            return false;
        }
        
        // 检查是否使用了不安全的编码方式
        return encodedPassword.startsWith("{md5}") || 
               encodedPassword.startsWith("{noop}") ||
               (!encodedPassword.startsWith("{") && encodedPassword.length() < 60); // 无前缀且长度较短
    }

    /**
     * 获取编码器类型
     *
     * @param encodedPassword 编码后的密码
     * @return 编码器类型
     */
    public static String getEncoderType(String encodedPassword) {
        if (encodedPassword == null || !encodedPassword.startsWith("{")) {
            return "plain"; // 无前缀，可能是明文
        }
        
        int endIndex = encodedPassword.indexOf("}");
        if (endIndex > 0) {
            return encodedPassword.substring(1, endIndex);
        }
        
        return "unknown";
    }
}
