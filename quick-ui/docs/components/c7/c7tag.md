# c7-dict-tag 字典标签组件

基于 Element Plus 的 `el-tag` 组件进行封装，用于展示字典数据的标签组件。支持多种数据格式和自定义样式。

> 主要用于展示字典数据，支持单个值、数组值、分隔符字符串等多种格式

## 基础用法

最基本的用法，展示单个字典值：

:::demo
dictTag/demo1
:::

## 数组值展示

支持数组格式的值，可以同时展示多个标签：

:::demo
dictTag/demo2
:::

## 分隔符字符串

支持以分隔符分隔的字符串格式：

:::demo
dictTag/demo3
:::

## 未匹配值处理

当传入的值在选项列表中找不到匹配项时，可以选择是否显示原始值：

:::demo
dictTag/demo4
:::

## 自定义样式

支持通过 `elTagClass` 属性自定义标签样式：

:::demo
dictTag/demo5
:::





### 条件渲染

:::demo
dictTag/demo7
:::

---

## Events（事件）

该组件为展示型组件，不提供事件。

:::demo
dictTag/demo8
:::
---

## 插槽

该组件不提供插槽。

---

## 注意事项

1. **数据格式**：`modelValue` 支持字符串、数字、数组等多种格式
2. **选项匹配**：组件会根据 `value` 字段进行精确匹配
3. **未匹配处理**：当值在选项中找不到匹配时，会根据 `showValue` 属性决定是否显示原始值
4. **样式优先级**：`elTagClass` 的优先级高于 `elTagType`
5. **分隔符处理**：字符串格式的值会按照 `separator` 进行分割处理

---

## 完整示例

:::demo
dictTag/demo8
:::

---
## Attributes（属性）

| 属性名       | 说明                                       | 类型                     | 可选值           | 默认值              |
| ------------ | ------------------------------------------ | ------------------------ | ---------------- | ------------------- |
| `modelValue` | 绑定值，支持 String/Number/Array           | `String` \| `Number` \| `Array` | —                | `''`                |
| `options`    | 选项数据列表                               | `Array`                  | —                | `[]`                |
| `showValue`  | 当未找到匹配的数据时，是否显示原始值       | `Boolean`                | —                | `true`              |
| `separator`  | 分隔符，用于解析字符串格式的值             | `String`                 | —                | `','`               |

### options 数据结构

| 属性名        | 说明           | 类型     | 可选值                                    | 默认值   |
| ------------- | -------------- | -------- | ----------------------------------------- | -------- |
| `label`       | 标签显示文本   | `String` | —                                         | —        |
| `value`       | 标签值         | `String` \| `Number` | —                                | —        |
| `elTagType`   | Element Plus 标签类型 | `String` | `'default'` \| `'primary'` \| `'success'` \| `'warning'` \| `'danger'` \| `'info'` | `'default'` |
| `elTagClass`  | 自定义 CSS 类名 | `String` | —                                         | `''`     |

---
