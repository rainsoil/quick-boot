# c7-dialog 对话框组件

基于 Element Plus 的 `el-dialog` 和 `el-drawer` 组件进行封装，支持对话框和抽屉两种模式。

## 基础用法

最简单的对话框使用方式：

:::demo
dialog/Dialog
:::

## 对话框模式

展示对话框模式的各种使用方式：

:::demo
dialog/demo1
:::

## 抽屉模式

展示抽屉模式的各种使用方式：

:::demo
dialog/demo2
:::

## 配置选项

展示不同配置选项的使用：

:::demo
dialog/demo3
:::

## 事件处理

展示事件处理的使用方式：

:::demo
dialog/demo4
:::

## Attributes

| 属性名 | 说明 | 类型 | 默认值 |
|--------|------|------|--------|
| `visible` | 是否显示对话框 | Boolean | - |
| `footer` | 是否显示底部按钮区域 | Boolean | `true` |
| `mode` | 弹框模式 | String | `'dialog'` |
| `modalProps` | 对话框/抽屉的配置属性 | Object | `{}` |

### mode 可选值

| 值 | 说明 |
|----|------|
| `dialog` | 对话框模式 |
| `drawer` | 抽屉模式 |

### modalProps 配置项

当 `mode` 为 `dialog` 时，支持 `el-dialog` 的所有属性：
- `title`：标题
- `width`：宽度
- `center`：是否居中
- `close-on-click-modal`：是否可通过点击遮罩关闭
- 等等...

当 `mode` 为 `drawer` 时，支持 `el-drawer` 的所有属性：
- `title`：标题
- `direction`：方向（rtl/ltr/ttb/btt）
- `size`：大小
- 等等...

## Events

| 事件名 | 说明 | 回调参数 |
|--------|------|----------|
| `update:visible` | 显示状态变化时触发 | `(visible: boolean)` |
| `close` | 关闭对话框时触发 | - |
| `submit` | 点击确定按钮时触发 | - |

## Slots

| 插槽名 | 说明 |
|--------|------|
| `default` | 对话框内容区域 |
| `footer` | 底部按钮区域（仅在 dialog 模式下有效） |

## 功能特性

1. **双模式支持**：支持对话框和抽屉两种显示模式
2. **默认按钮**：当 `footer` 为 `true` 且未提供 `footer` 插槽时，显示默认的确定/取消按钮
3. **自定义配置**：通过 `modalProps` 传递底层组件的所有配置
4. **事件处理**：支持关闭、提交等事件处理

## 使用说明

1. **显示控制**：通过 `v-model:visible` 或 `visible` 属性控制显示状态
2. **模式选择**：通过 `mode` 属性选择对话框或抽屉模式
3. **自定义配置**：通过 `modalProps` 传递底层组件的配置
4. **事件监听**：监听 `submit`、`close` 等事件处理用户操作
