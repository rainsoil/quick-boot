# c7-date-picker 日期选择器组件

基于 Element Plus 的 `el-date-picker` 组件进行封装，提供了更便捷的日期选择功能，支持多种日期类型和格式。

## 基础用法

最简单的日期选择器使用方式：

:::demo
datepicker/demo1
:::

## 日期范围选择

支持日期范围选择，可选择是否合并结果：

:::demo
datepicker/demo2
:::

## 默认值设置

支持设置默认选中值：

:::demo
datepicker/demo3
:::

## 数组结果格式

支持返回数组格式的结果：

:::demo
datepicker/demo4
:::

## 不同日期类型

展示各种日期选择器类型的使用：

:::demo
datepicker/demo5
:::

## 自定义格式

支持自定义显示和绑定格式：

:::demo
datepicker/demo6
:::

## 范围合并对比

展示范围值合并功能的对比：

:::demo
datepicker/demo7
:::

## 默认值设置

展示各种默认值的设置方式：

:::demo
datepicker/demo8
:::

## Attributes

| 属性名 | 说明 | 类型 | 默认值 |
|--------|------|------|--------|
| `modelValue` / `v-model` | 绑定值，支持字符串、数组或日期对象 | `String` / `Array` / `Date` | - |
| `type` | 日期选择器类型 | `String` | `'date'` |
| `format` | 显示在输入框中的格式 | `String` | 根据 `type` 推断 |
| `valueFormat` | 绑定值的格式 | `String` | 根据 `type` 推断 |
| `rangeMerge` | 是否将范围值合并为逗号分隔字符串（仅限 range 类型） | `Boolean` | `true` |

### type 可选值

| 值 | 说明 | 默认格式 |
|----|------|----------|
| `date` | 日期选择器 | `YYYY-MM-DD` |
| `datetime` | 日期时间选择器 | `YYYY-MM-DD HH:mm:ss` |
| `daterange` | 日期范围选择器 | `YYYY-MM-DD` |
| `datetimerange` | 日期时间范围选择器 | `YYYY-MM-DD HH:mm:ss` |
| `year` | 年份选择器 | `YYYY` |
| `month` | 月份选择器 | `YYYY-MM` |

### 默认格式映射

| type | format | valueFormat |
|------|--------|-------------|
| `date` | `YYYY-MM-DD` | `YYYY-MM-DD` |
| `datetime` | `YYYY-MM-DD HH:mm:ss` | `YYYY-MM-DD HH:mm:ss` |
| `daterange` | `YYYY-MM-DD` | `YYYY-MM-DD` |
| `datetimerange` | `YYYY-MM-DD HH:mm:ss` | `YYYY-MM-DD HH:mm:ss` |
| `year` | `YYYY` | `YYYY` |
| `month` | `YYYY-MM` | `YYYY-MM` |

## Events

| 事件名 | 说明 | 回调参数 |
|--------|------|----------|
| `update:modelValue` | 选中值变化时触发 | `(value: any)` |

## Slots

| 插槽名 | 说明 |
|--------|------|
| `default` | 自定义 `el-date-picker` 内部插槽内容 |

## 功能特性

1. **多种日期类型**：支持年、月、日、日期时间、范围选择等多种类型
2. **自动格式推断**：根据 type 自动推断合适的显示和绑定格式
3. **范围值合并**：支持将日期范围合并为逗号分隔的字符串
4. **格式自定义**：支持自定义显示格式和绑定值格式
5. **双向绑定**：支持 v-model 双向数据绑定

## 使用说明

1. **类型选择**：通过 `type` 属性选择不同的日期选择器类型
2. **格式设置**：通过 `format` 和 `valueFormat` 自定义格式
3. **范围合并**：通过 `rangeMerge` 控制范围值是否合并为字符串
4. **默认值**：支持设置默认选中值

