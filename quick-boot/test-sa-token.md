# Sa-Token 功能测试指南

## 🚀 测试步骤

### 1. 启动应用
```bash
cd quick-boot
mvn spring-boot:run
```

### 2. 测试登录接口
```bash
# 测试登录
curl -X POST http://localhost:12000/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### 3. 测试权限验证接口
```bash
# 获取登录状态
curl -X GET http://localhost:12000/test/sa-token/status

# 测试用户列表权限
curl -X GET http://localhost:12000/test/sa-token/permission/user-list \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"

# 测试用户添加权限
curl -X GET http://localhost:12000/test/sa-token/permission/user-add \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"

# 测试admin角色
curl -X GET http://localhost:12000/test/sa-token/role/admin \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### 4. 测试用户信息获取
```bash
# 获取当前用户信息
curl -X GET http://localhost:12000/test/sa-token/user \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"

# 获取用户权限列表
curl -X GET http://localhost:12000/test/sa-token/permissions \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"

# 获取用户角色列表
curl -X GET http://localhost:12000/test/sa-token/roles \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### 5. 测试登出
```bash
curl -X POST http://localhost:12000/login/logout \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## 🔍 预期结果

### 登录成功响应
```json
{
  "code": 200,
  "msg": null,
  "data": {
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...",
    "tokenName": "Authorization",
    "tokenValue": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...",
    "isLogin": true,
    "loginId": "1",
    "loginDevice": "default-device",
    "tokenTimeout": 2592000,
    "sessionTimeout": -2,
    "tokenSessionTimeout": -2,
    "tokenActiveTimeout": -1
  }
}
```

### 权限验证成功响应
```json
{
  "code": 200,
  "msg": null,
  "data": "用户列表权限验证通过"
}
```

### 用户信息响应
```json
{
  "code": 200,
  "msg": null,
  "data": {
    "id": 1,
    "username": "admin",
    "nickname": "管理员",
    "status": "0",
    "roles": ["admin"],
    "perms": ["system:user:list", "system:user:add", "system:user:edit", "system:user:delete", "system:role:list", "system:role:export"]
  }
}
```

## ⚠️ 注意事项

1. **Token格式**: Sa-Token使用JWT格式，token值较长
2. **权限验证**: 确保用户有相应的权限才能访问受保护的接口
3. **错误处理**: 如果token无效或权限不足，会返回相应的错误信息
4. **跨域问题**: 如果前端和后端分离部署，需要配置CORS

## 🐛 常见问题

### 1. 登录失败
- 检查用户名密码是否正确
- 检查用户状态是否正常
- 查看后端日志获取详细错误信息

### 2. 权限验证失败
- 检查token是否有效
- 检查用户是否有相应权限
- 检查权限配置是否正确

### 3. 跨域问题
- 检查CORS配置
- 确保前端请求头设置正确
- 检查代理配置

## 📝 测试记录

- [ ] 登录功能测试
- [ ] 权限验证测试
- [ ] 角色验证测试
- [ ] 用户信息获取测试
- [ ] 登出功能测试
- [ ] 前端集成测试
