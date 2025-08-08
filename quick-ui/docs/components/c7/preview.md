# c7-preview 文件预览组件

一个支持多种文件类型预览的组件，包括图片、视频和普通文件，支持多种预览模式。

## 基础用法

最简单的文件预览使用方式：

:::demo
preview/preview
:::

### 图片预览功能

展示各种图片预览功能的使用：

:::demo
preview/demo1
:::

### 视频预览功能

展示视频预览功能的使用：

:::demo
preview/demo2
:::

### 文件预览功能

展示文件预览功能的使用：

:::demo
preview/demo3
:::

### 高级预览功能

展示高级预览功能的使用：

:::demo
preview/demo4
:::demo
preview/preview
:::

## Attributes

| 属性名 | 说明 | 类型 | 默认值 |
|--------|------|------|--------|
| `urls` | 以逗号分隔的文件 URL 列表 | String | - |
| `width` | 预览区域宽度，可传数字或带单位的字符串 | String / Number | `'200px'` |
| `height` | 预览区域高度 | String / Number | `'200px'` |
| `displayType` | 文件显示类型 | String | `'image'` |
| `coverType` | 封面类型 | String | `'None'` |
| `buttonText` | 当封面类型为 button 时，按钮显示的文本 | String | `'预览文件'` |
| `defaultVideoImage` | 当 displayType 为 video 时，默认的视频缩略图 | String | `'https://via.placeholder.com/200?text=Video'` |
| `defaultFileImage` | 当 displayType 为 file 且封面类型为 file 时，默认的文件封面图 | String | `'https://via.placeholder.com/200?text=File'` |

### displayType 可选值

| 值 | 说明 |
|----|------|
| `image` | 图片类型，支持图片预览 |
| `video` | 视频类型，支持视频播放 |
| `file` | 文件类型，支持文件下载 |

### coverType 可选值

| 值 | 说明 |
|----|------|
| `None` | 直接展示所有预览项 |
| `button` | 按钮封面，点击按钮弹出预览对话框 |
| `file` | 文件封面，显示文件封面图片 |

## 功能特性

1. **多文件支持**：支持同时预览多个文件，URL 用逗号分隔
2. **多种文件类型**：支持图片、视频、普通文件的预览
3. **多种预览模式**：支持直接预览、按钮触发、文件封面等模式
4. **图片预览**：支持图片的缩略图显示和全屏预览
5. **视频播放**：支持视频的缩略图显示和弹窗播放
6. **文件下载**：支持普通文件的链接下载
7. **自定义样式**：支持自定义预览区域的宽度和高度

## 使用说明

1. **文件 URL**：通过 `urls` 属性传入文件 URL，多个文件用逗号分隔
2. **显示类型**：通过 `displayType` 指定文件的显示类型
3. **预览模式**：通过 `coverType` 选择预览模式
4. **样式定制**：通过 `width` 和 `height` 自定义预览区域大小

