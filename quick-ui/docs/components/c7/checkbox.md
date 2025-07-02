# checkBox


## 基础用法
:::demo
checkbox/demo1

:::

## 返回结果演示(用,隔开和数组)

:::demo
checkbox/demo2

:::

##  全不选和全选

:::demo
checkbox/demo3

:::

## button样式

:::demo
checkbox/demo4

:::

## change事件

:::demo
checkbox/demo5

:::

## 使用函数
:::demo
checkbox/demo6

:::




---

## Attributes

| 属性名              | 说明                                                                 | 类型          | 默认值        |
|---------------------|----------------------------------------------------------------------|---------------|---------------|
| **model-value** / **v-model** | 选中项绑定值，支持数组或逗号分隔字符串                                | `Array` / `String` | `[]`（空数组）|
| **indeterminate**   | 是否显示“全选”复选框（由组件内部逻辑控制状态）                       | `Boolean`     | `false`       |
| **separator**       | 是否将选中值转换为逗号分隔的字符串（`true`：字符串绑定；`false`：数组绑定） | `Boolean`     | `true`        |
| **label-key**       | 数据项中标签字段的键名（默认 `label`）                               | `String`      | `'label'`     |
| **value-key**       | 数据项中值字段的键名（默认 `value`）                                 | `String`      | `'value'`     |
| **button**          | 是否使用 `el-checkbox-button` 风格（按钮样式复选框）                 | `Boolean`     | `false`       |

---

## Events

| 事件名              | 说明                                                                 | 类型       |
|---------------------|----------------------------------------------------------------------|------------|
| **update:model-value** | 当绑定值（`v-model`）发生变化时触发，传递当前选中的值（数组或字符串） | `Function` |
| **change**          | 当选中项发生变化时触发，传递当前选中的值数组                         | `Function` |

---














