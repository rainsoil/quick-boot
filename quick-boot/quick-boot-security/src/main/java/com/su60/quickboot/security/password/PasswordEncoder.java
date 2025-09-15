package com.su60.quickboot.security.password;

/**
 * 密码编码器接口
 * 模仿Spring Security的PasswordEncoder
 *
 * @author luyanan
 * @since 2024/12/19
 */
public interface PasswordEncoder {

    /**
     * 编码原始密码
     *
     * @param rawPassword 原始密码
     * @return 编码后的密码
     */
    String encode(CharSequence rawPassword);

    /**
     * 验证原始密码是否与编码后的密码匹配
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 编码后的密码
     * @return 是否匹配
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);

    /**
     * 判断编码后的密码是否需要升级
     * 默认返回false，表示不需要升级
     *
     * @param encodedPassword 编码后的密码
     * @return 是否需要升级
     */
    default boolean upgradeEncoding(String encodedPassword) {
        return false;
    }
}
