# c7-radio 单选组件

基于 Element Plus 的 `el-radio` 组件进行封装，提供了更便捷的单选功能。

## 基础用法

最简单的单选使用方式：

:::demo
radio/demo
:::

### 基础单选功能

展示各种基础单选功能的使用：

:::demo
radio/demo1
:::

### 高级单选功能

展示高级单选功能的使用：

:::demo
radio/demo2
:::

### 复杂单选功能

展示复杂单选功能的使用：

:::demo
radio/demo3
:::demo
radio/demo
:::

## Attributes

| 属性名 | 说明 | 类型 | 默认值 |
|--------|------|------|--------|
| `modelValue` | 绑定值 | String / Number | `''` |
| `dataList` | 选项数据列表 | Array | `[]` |

### dataList 数据格式

| 属性名 | 说明 | 类型 | 必填 |
|--------|------|------|------|
| `label` | 选项显示文本 | String | 是 |
| `value` | 选项值 | String / Number | 是 |

## Events

| 事件名 | 说明 | 回调参数 |
|--------|------|----------|
| `update:modelValue` | 选中值变化时触发 | `(value: string | number)` |
| `change` | 选中值变化时触发 | `(value: string | number)` |

## 使用说明

1. **数据格式**：`dataList` 数组中的每个对象必须包含 `label` 和 `value` 属性
2. **绑定值**：通过 `v-model` 进行双向绑定
3. **事件监听**：可以通过 `@change` 监听选中值的变化
