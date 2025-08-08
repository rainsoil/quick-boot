# c7-upload 文件上传组件

基于 Element Plus 的 `el-upload` 组件进行封装，提供了更便捷的文件上传功能，支持图片预览、文件类型限制、大小限制等。

> **v2.0 优化亮点**：
> - ✅ **文件验证增强**：完整的文件类型、大小、数量验证系统
> - ✅ **错误处理优化**：清晰的错误提示和异常处理机制
> - ✅ **代码质量提升**：清理调试代码，优化文件状态管理
> - ✅ **用户体验改善**：动态提示信息和更好的加载状态
> - ✅ **性能优化**：计算属性缓存和内存使用优化

## 基础用法

最简单的文件上传使用方式：

:::demo
upload/baseUpload
:::

## Attributes

| 属性名 | 说明 | 类型 | 默认值 |
|--------|------|------|--------|
| `modelValue` | 上传文件的 URL 字符串或数组 | `String \| Array` | - |
| `uploadUrl` | 上传接口地址 | `String` | `''` |
| `fileSize` | 文件大小限制（MB） | `Number` | `5` |
| `fileType` | 允许上传的文件类型（后缀名，逗号分隔） | `String` | `'jpg,png'` |
| `limit` | 上传文件数量限制 | `Number` | `1` |
| `deleteUrl` | 删除文件的接口地址 | `String` | `''` |
| `headers` | 上传请求的头部信息 | `Object` | `{}` |
| `listType` | 文件列表显示类型 | `'text' \| 'picture' \| 'picture-card'` | `'picture-card'` |
| `showTip` | 是否显示上传提示信息 | `Boolean` | `true` |
| `tipText` | 自定义提示信息内容 | `String` | `''` |
| `disabled` | 是否禁用上传 | `Boolean` | `false` |
| `multiple` | 是否支持多选文件 | `Boolean` | `false` |
| `autoUpload` | 是否自动上传 | `Boolean` | `true` |

## Events

| 事件名 | 说明 | 回调参数 |
|--------|------|----------|
| `update:modelValue` | 文件列表变化时触发 | `(fileUrls: string)` |
| `success` | 文件上传成功时触发 | `(response: any, file: File, fileList: FileInfo[])` |
| `error` | 文件上传失败时触发 | `(error: any, file: File, fileList: FileInfo[])` |
| `progress` | 文件上传过程中触发 | `(event: ProgressEvent, file: File, fileList: FileInfo[])` |
| `remove` | 文件移除时触发 | `(file: File, fileList: FileInfo[])` |
| `preview` | 点击文件预览时触发 | `(file: File)` |

## 插槽

| 插槽名 | 说明 |
|--------|------|
| `tip` | 自定义上传提示内容 |

## 功能特性

1. **智能文件验证**：
   - 文件类型验证（支持扩展名和 MIME 类型）
   - 文件大小验证（支持 MB 单位限制）
   - 文件数量限制
   - 自定义验证规则支持

2. **增强用户体验**：
   - 动态提示信息生成
   - 计算属性优化的文件类型处理
   - 友好的错误提示
   - 加载状态管理

3. **完善错误处理**：
   - 详细的验证错误信息
   - 上传失败自动重试机制
   - 网络错误处理
   - 异常状态恢复

4. **性能优化**：
   - 文件状态统一管理
   - 内存使用优化
   - 计算属性缓存
   - 避免重复验证

## 文件验证说明

组件集成了 c7-plus 文件验证工具，提供：

- **预设验证器**：图片、文档、视频等常用类型
- **自定义验证**：支持复杂的业务验证规则
- **批量验证**：一次性验证多个文件
- **详细错误**：精确的错误定位和提示

```javascript
// 支持的文件类型常量
import { FILE_TYPES } from 'c7-plus'

// 图片类型：jpg, jpeg, png, gif, bmp, webp
FILE_TYPES.IMAGE

// 文档类型：pdf, doc, docx, xls, xlsx, ppt, pptx, txt
FILE_TYPES.DOCUMENT

// 视频类型：mp4, avi, mov, wmv, flv, webm
FILE_TYPES.VIDEO
```

## 注意事项

1. **上传接口**：`uploadUrl` 必须返回标准的响应格式，包含 `code`、`data` 等字段
2. **文件格式**：`fileType` 支持多种格式，用逗号分隔，如 `'jpg,png,gif'`
3. **响应格式**：上传成功后的响应应包含 `fileName`、`url`、`id` 等字段
4. **删除接口**：如果配置了 `deleteUrl`，删除文件时会调用该接口
5. **性能优化**：大文件上传建议使用分片上传，避免内存溢出
6. **类型安全**：推荐在 TypeScript 项目中使用，享受完整的类型检查

## 工具函数支持

组件现已集成强大的工具函数：

- **文件验证**：`useFileValidator` - 完整的文件验证系统
- **错误处理**：`useErrorHandler` - 统一的错误处理机制
- **数据格式化**：`formatFileSize` - 文件大小格式化
- **类型检查**：`getFileExtension` - 安全的文件类型检查

### 基础上传功能

展示各种基础上传功能的使用：

:::demo
upload/demo1
:::

### 高级上传功能

展示高级上传功能的使用：

:::demo
upload/demo2
:::

### 实际应用场景

展示在实际应用中的使用场景：

:::demo
upload/demo3
:::
