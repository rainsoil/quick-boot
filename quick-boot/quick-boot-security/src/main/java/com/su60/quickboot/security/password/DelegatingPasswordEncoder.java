package com.su60.quickboot.security.password;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 委托密码编码器
 * 根据密码前缀选择不同的编码器
 * 模仿Spring Security的DelegatingPasswordEncoder
 *
 * @author luyanan
 * @since 2024/12/19
 */
@Slf4j
public class DelegatingPasswordEncoder implements PasswordEncoder {

    private static final String PREFIX = "{";
    private static final String SUFFIX = "}";

    private final String idForEncode;
    private final PasswordEncoder idToPasswordEncoder;
    private final Map<String, PasswordEncoder> idToPasswordEncoderMap;

    /**
     * 构造函数
     *
     * @param idForEncode           默认编码器ID
     * @param idToPasswordEncoder   默认编码器
     * @param idToPasswordEncoderMap 所有编码器映射
     */
    public DelegatingPasswordEncoder(String idForEncode, PasswordEncoder idToPasswordEncoder,
                                   Map<String, PasswordEncoder> idToPasswordEncoderMap) {
        if (idForEncode == null) {
            throw new IllegalArgumentException("idForEncode cannot be null");
        }
        if (idToPasswordEncoder == null) {
            throw new IllegalArgumentException("idToPasswordEncoder cannot be null");
        }
        if (idToPasswordEncoderMap == null) {
            throw new IllegalArgumentException("idToPasswordEncoderMap cannot be null");
        }
        if (!idToPasswordEncoderMap.containsKey(idForEncode)) {
            throw new IllegalArgumentException("idToPasswordEncoderMap does not contain idForEncode");
        }

        this.idForEncode = idForEncode;
        this.idToPasswordEncoder = idToPasswordEncoder;
        this.idToPasswordEncoderMap = new HashMap<>(idToPasswordEncoderMap);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return PREFIX + this.idForEncode + SUFFIX + this.idToPasswordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String prefixEncodedPassword) {
        if (rawPassword == null && prefixEncodedPassword == null) {
            return true;
        }
        if (rawPassword == null || prefixEncodedPassword == null) {
            return false;
        }

        String id = extractId(prefixEncodedPassword);
        if (id == null) {
            return this.idToPasswordEncoder.matches(rawPassword, prefixEncodedPassword);
        }

        PasswordEncoder delegate = this.idToPasswordEncoderMap.get(id);
        if (delegate == null) {
            log.warn("No PasswordEncoder mapped for id \"{}\"", id);
            return false;
        }

        String encodedPassword = extractEncodedPassword(prefixEncodedPassword);
        return delegate.matches(rawPassword, encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String prefixEncodedPassword) {
        String id = extractId(prefixEncodedPassword);
        if (id == null) {
            return this.idToPasswordEncoder.upgradeEncoding(prefixEncodedPassword);
        }

        PasswordEncoder delegate = this.idToPasswordEncoderMap.get(id);
        if (delegate == null) {
            return false;
        }

        String encodedPassword = extractEncodedPassword(prefixEncodedPassword);
        return delegate.upgradeEncoding(encodedPassword);
    }

    /**
     * 提取编码器ID
     *
     * @param prefixEncodedPassword 带前缀的编码密码
     * @return 编码器ID
     */
    private String extractId(String prefixEncodedPassword) {
        if (prefixEncodedPassword == null) {
            return null;
        }
        int start = prefixEncodedPassword.indexOf(PREFIX);
        if (start != 0) {
            return null;
        }
        int end = prefixEncodedPassword.indexOf(SUFFIX, start);
        if (end < 0) {
            return null;
        }
        return prefixEncodedPassword.substring(start + 1, end);
    }

    /**
     * 提取编码后的密码部分
     *
     * @param prefixEncodedPassword 带前缀的编码密码
     * @return 编码后的密码
     */
    private String extractEncodedPassword(String prefixEncodedPassword) {
        int start = prefixEncodedPassword.indexOf(SUFFIX);
        return prefixEncodedPassword.substring(start + 1);
    }

    /**
     * 获取所有支持的编码器ID
     *
     * @return 编码器ID集合
     */
    public Map<String, PasswordEncoder> getIdToPasswordEncoderMap() {
        return new HashMap<>(this.idToPasswordEncoderMap);
    }
}
