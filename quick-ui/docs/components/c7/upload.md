# c7-upload 文件上传组件

基于 Element Plus 的 `el-upload` 组件进行封装，提供了更便捷的文件上传功能，支持图片预览、文件类型限制、大小限制等。

## 基础用法

最简单的文件上传使用方式：

:::demo
upload/baseUpload
:::

## Attributes

| 属性名 | 说明 | 类型 | 默认值 |
|--------|------|------|--------|
| `modelValue` | 上传文件的 URL 字符串（逗号分隔） | String | - |
| `uploadUrl` | 上传接口地址 | String | `''` |
| `fileSize` | 文件大小限制（MB） | Number | `5` |
| `fileType` | 允许上传的文件类型（后缀名，逗号分隔） | String | `'jpg,png'` |
| `limit` | 上传文件数量限制 | Number | `1` |
| `deleteUrl` | 删除文件的接口地址 | String | `''` |
| `headers` | 上传请求的头部信息 | Object | `{}` |
| `listType` | 文件列表显示类型 | String | `'picture-card'` |

## Events

| 事件名 | 说明 | 回调参数 |
|--------|------|----------|
| `update:modelValue` | 文件列表变化时触发 | `(fileUrls: string)` |

## 功能特性

1. **文件类型限制**：通过 `fileType` 属性限制上传文件的类型
2. **文件大小限制**：通过 `fileSize` 属性限制上传文件的大小
3. **数量限制**：通过 `limit` 属性限制上传文件的数量
4. **图片预览**：支持图片文件的预览功能
5. **文件删除**：支持删除已上传的文件
6. **上传进度**：显示文件上传的进度和状态
7. **错误处理**：自动处理上传失败的情况

## 注意事项

1. **上传接口**：`uploadUrl` 必须返回标准的响应格式，包含 `code`、`data` 等字段
2. **文件格式**：`fileType` 支持多种格式，用逗号分隔，如 `'jpg,png,gif'`
3. **响应格式**：上传成功后的响应应包含 `fileName`、`url`、`id` 等字段
4. **删除接口**：如果配置了 `deleteUrl`，删除文件时会调用该接口

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
