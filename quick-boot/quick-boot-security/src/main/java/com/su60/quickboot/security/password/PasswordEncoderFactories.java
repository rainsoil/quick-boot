package com.su60.quickboot.security.password;

import java.util.HashMap;
import java.util.Map;

/**
 * 密码编码器工厂类
 * 模仿Spring Security的PasswordEncoderFactories
 *
 * @author luyanan
 * @since 2024/12/19
 */
public final class PasswordEncoderFactories {

    private PasswordEncoderFactories() {
        // 私有构造函数，防止实例化
    }

    /**
     * 创建委托密码编码器
     * 支持多种编码算法，根据前缀自动选择
     * 默认使用BCrypt编码
     *
     * @return 委托密码编码器
     */
    public static PasswordEncoder createDelegatingPasswordEncoder() {
        return createDelegatingPasswordEncoder("bcrypt");
    }

    /**
     * 创建委托密码编码器
     * 支持多种编码算法，根据前缀自动选择
     *
     * @param defaultIdForEncode 默认编码器ID
     * @return 委托密码编码器
     */
    public static PasswordEncoder createDelegatingPasswordEncoder(String defaultIdForEncode) {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        
        // 注册各种编码器
        encoders.put("bcrypt", new BCryptPasswordEncoder());

        encoders.put("md5", new Md5PasswordEncoder());
        
        // 添加无前缀的兼容性支持（向后兼容）
        encoders.put("noop", new NoOpPasswordEncoder());
        
        PasswordEncoder defaultEncoder = encoders.get(defaultIdForEncode);
        if (defaultEncoder == null) {
            throw new IllegalArgumentException("Default encoder not found: " + defaultIdForEncode);
        }
        
        return new DelegatingPasswordEncoder(defaultIdForEncode, defaultEncoder, encoders);
    }


}
