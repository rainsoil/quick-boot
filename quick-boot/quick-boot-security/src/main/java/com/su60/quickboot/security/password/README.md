# 密码编码器使用指南

## 概述

本项目实现了完整的密码编码器系统，模仿Spring Security的PasswordEncoder设计，支持多种加密算法和委托模式。

## 核心组件

### 1. PasswordEncoder接口
```java
public interface PasswordEncoder {
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodedPassword);
    default boolean upgradeEncoding(String encodedPassword) { return false; }
}
```

### 2. 支持的编码器

#### BCryptPasswordEncoder
- **ID**: `bcrypt`, `bcrypt4`, `bcrypt6`, `bcrypt8`, `bcrypt10`, `bcrypt12`
- **安全性**: 高
- **推荐**: 是
- **示例**: `{bcrypt}$2a$10$N9qo8uLOickgx2ZMRZoMye...`

#### Md5PasswordEncoder
- **ID**: `md5`
- **安全性**: 低（已过时）
- **推荐**: 否，仅用于兼容旧系统
- **示例**: `{md5}5d41402abc4b2a76b9719d911017c592`

#### NoOpPasswordEncoder
- **ID**: `noop`
- **安全性**: 无（明文）
- **推荐**: 否，仅用于兼容
- **示例**: `{noop}password123`

## 使用方式

### 1. 依赖注入方式（推荐）

```java
@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    
    public void saveUser(String password) {
        // 编码密码
        String encoded = passwordEncoder.encode(password);
        // 保存到数据库: {bcrypt}$2a$10$...
    }
    
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        // 验证密码，自动根据前缀选择编码器
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
```

### 2. 静态工具类方式

```java
// 编码密码
String encoded = PasswordUtils.encode("password123");

// 验证密码
boolean matches = PasswordUtils.matches("password123", encoded);

// 检查是否需要升级
boolean needsUpgrade = PasswordUtils.upgradeEncoding(encoded);

// 检查编码器类型
String type = PasswordUtils.getEncoderType(encoded); // "bcrypt"

// 检查是否不安全
boolean unsafe = PasswordUtils.isUnsafeEncoding(encoded);
```

## 委托模式示例

### 编码密码
```java
PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
String encoded = encoder.encode("password123");
// 结果: {bcrypt}$2a$10$N9qo8uLOickgx2ZMRZoMye...
```

### 验证密码（自动选择编码器）
```java
// BCrypt密码
boolean result1 = encoder.matches("password123", "{bcrypt}$2a$10$N9qo8uLOickgx2ZMRZoMye...");

// MD5密码
boolean result2 = encoder.matches("password123", "{md5}5d41402abc4b2a76b9719d911017c592");

// NoOp密码
boolean result3 = encoder.matches("password123", "{noop}password123");

// 无前缀密码（向后兼容）
boolean result4 = encoder.matches("password123", "password123");
```

## 配置

### 默认配置
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
}
```

### 自定义默认编码器
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder("bcrypt8");
}
```

### 简单编码器（无前缀）
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createBCryptPasswordEncoder();
}
```

## 迁移指南

### 从旧系统迁移

1. **MD5密码迁移**
```java
// 旧密码: 5d41402abc4b2a76b9719d911017c592
// 新密码: {md5}5d41402abc4b2a76b9719d911017c592
String oldPassword = "5d41402abc4b2a76b9719d911017c592";
String newPassword = "{md5}" + oldPassword;
```

2. **明文密码迁移**
```java
// 旧密码: password123
// 新密码: {noop}password123
String oldPassword = "password123";
String newPassword = "{noop}" + oldPassword;
```

3. **自动升级检测**
```java
if (PasswordUtils.upgradeEncoding(encodedPassword)) {
    // 重新编码为更安全的格式
    String newEncoded = passwordEncoder.encode(rawPassword);
    // 更新数据库
}
```

## 安全建议

1. **新密码**: 使用BCrypt编码器
2. **旧密码**: 逐步迁移到BCrypt
3. **定期检查**: 使用`upgradeEncoding()`检查是否需要升级
4. **避免使用**: MD5和NoOp编码器（仅用于兼容）

## 测试示例

```java
@Test
public void testPasswordEncoder() {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    
    String rawPassword = "test123";
    String encoded = encoder.encode(rawPassword);
    
    // 验证编码格式
    assertTrue(encoded.startsWith("{bcrypt}"));
    
    // 验证密码
    assertTrue(encoder.matches(rawPassword, encoded));
    assertFalse(encoder.matches("wrong", encoded));
    
    // 检查升级
    assertFalse(encoder.upgradeEncoding(encoded));
}
```
