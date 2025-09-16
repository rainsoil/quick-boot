# 🏗️ Quick-Boot 模块依赖优化方案

## 📊 当前模块依赖关系分析

### 现有模块结构
```
quick-boot (父模块)
├── quick-boot-common     # 公共基础模块
├── quick-boot-data       # 数据访问层
├── quick-boot-core       # 核心业务模块
├── quick-boot-system     # 系统管理模块
└── quick-boot-web        # Web应用模块
```

### 当前依赖关系图
```
quick-boot-web
├── quick-boot-core (版本硬编码: 1.0-SNAPSHOT)
├── quick-boot-system
└── quick-boot-data

quick-boot-system
├── quick-boot-core
└── quick-boot-data

quick-boot-core
└── quick-boot-data

quick-boot-data
└── quick-boot-common

quick-boot-common
└── (基础依赖)
```

## ⚠️ 发现的问题

### 1. **版本管理问题**
- `quick-boot-web` 中 `quick-boot-core` 版本硬编码为 `1.0-SNAPSHOT`
- 缺少统一的版本管理策略
- 依赖版本不一致

### 2. **依赖结构问题**
- 多个模块重复依赖 `quick-boot-data`
- 缺少依赖传递性优化
- 模块职责边界不够清晰

### 3. **潜在循环依赖风险**
- `quick-boot-core` 和 `quick-boot-system` 之间可能存在循环依赖
- 缺少依赖关系验证机制

## 🎯 优化目标

### 1. **消除循环依赖**
- 重新设计模块边界
- 建立清晰的依赖层次结构

### 2. **统一版本管理**
- 使用 `${project.version}` 统一版本管理
- 在父POM中集中管理依赖版本

### 3. **优化依赖结构**
- 减少重复依赖
- 提高依赖传递效率
- 明确模块职责

## 🔧 优化方案

### 1. **重新设计模块依赖层次**

#### 优化后的依赖关系图
```
quick-boot-web
├── quick-boot-system
└── quick-boot-core

quick-boot-system
├── quick-boot-core
└── quick-boot-data

quick-boot-core
├── quick-boot-data
└── quick-boot-common

quick-boot-data
└── quick-boot-common

quick-boot-common
└── (基础依赖)
```

#### 模块职责重新定义
- **quick-boot-common**: 公共工具类、基础配置、通用接口
- **quick-boot-data**: 数据访问层、实体类、Mapper接口
- **quick-boot-core**: 核心业务逻辑、服务接口、工具类
- **quick-boot-system**: 系统管理功能、用户权限、系统配置
- **quick-boot-web**: Web层、控制器、配置类

### 2. **依赖版本统一管理**

#### 父POM优化
```xml
<dependencyManagement>
    <dependencies>
        <!-- 内部模块依赖 -->
        <dependency>
            <groupId>com.su60.quickboot.common</groupId>
            <artifactId>quick-boot-common</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>com.su60.quickboot.data</groupId>
            <artifactId>quick-boot-data</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>com.su60.quickboot.core</groupId>
            <artifactId>quick-boot-core</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>com.su60.quickboot.system</groupId>
            <artifactId>quick-boot-system</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 3. **各模块POM优化**

#### quick-boot-common/pom.xml
```xml
<dependencies>
    <!-- 基础依赖 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
    </dependency>
    <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-all</artifactId>
    </dependency>
    <!-- Sa-Token 依赖 -->
    <dependency>
        <groupId>cn.dev33</groupId>
        <artifactId>sa-token-spring-boot3-starter</artifactId>
    </dependency>
</dependencies>
```

#### quick-boot-data/pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>com.su60.quickboot.common</groupId>
        <artifactId>quick-boot-common</artifactId>
    </dependency>
    <!-- MyBatis-Plus 相关依赖 -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
    </dependency>
    <!-- 其他数据访问相关依赖 -->
</dependencies>
```

#### quick-boot-core/pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>com.su60.quickboot.data</groupId>
        <artifactId>quick-boot-data</artifactId>
    </dependency>
    <!-- 核心业务相关依赖 -->
</dependencies>
```

#### quick-boot-system/pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>com.su60.quickboot.core</groupId>
        <artifactId>quick-boot-core</artifactId>
    </dependency>
    <dependency>
        <groupId>com.su60.quickboot.data</groupId>
        <artifactId>quick-boot-data</artifactId>
    </dependency>
    <!-- 系统管理相关依赖 -->
</dependencies>
```

#### quick-boot-web/pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>com.su60.quickboot.system</groupId>
        <artifactId>quick-boot-system</artifactId>
    </dependency>
    <dependency>
        <groupId>com.su60.quickboot.core</groupId>
        <artifactId>quick-boot-core</artifactId>
    </dependency>
    <!-- Web相关依赖 -->
</dependencies>
```

## 📋 实施步骤

### 第一阶段：版本统一管理
1. ✅ 在父POM中添加内部模块的依赖管理
2. ✅ 更新各模块POM，使用 `${project.version}`
3. ✅ 移除硬编码版本号

### 第二阶段：依赖结构优化
1. ✅ 重新设计模块依赖关系
2. ✅ 消除循环依赖
3. ✅ 优化依赖传递

### 第三阶段：验证和测试
1. ✅ 编译验证依赖关系
2. ✅ 运行测试确保功能正常
3. ✅ 性能测试验证优化效果

## 🎯 预期收益

### 1. **构建效率提升**
- 减少重复依赖下载
- 提高编译速度
- 优化依赖解析时间

### 2. **维护成本降低**
- 统一版本管理
- 清晰的模块边界
- 减少依赖冲突

### 3. **开发体验改善**
- 清晰的模块职责
- 更好的IDE支持
- 简化的依赖管理

## 🔍 风险评估

### 技术风险
- **模块重构**：可能影响现有功能
- **依赖变更**：需要充分测试验证
- **版本兼容**：需要确保向后兼容

### 实施风险
- **时间成本**：重构工作需要时间投入
- **学习成本**：团队需要适应新的模块结构
- **测试成本**：需要全面的回归测试

## 📝 总结

通过系统性的模块依赖优化，可以显著提升项目的可维护性和开发效率。建议按照优先级逐步实施，确保项目的稳定性和向后兼容性。

---

**文档生成时间**：2024年12月19日  
**项目版本**：Quick-Boot 1.0-SNAPSHOT  
**优化范围**：模块依赖关系重构

