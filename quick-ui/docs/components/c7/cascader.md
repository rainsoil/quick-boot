# c7-cascader

## 基础用法(静态数据)
:::demo
cascader/demo1
:::


## 基础用法(使用函数获取数据)
:::demo
cascader/demo2
:::


## 绑定值
> 由于cascader 组件默认的绑定值为数组,但是我们可能会由别的情况,比如 想要的值用,隔开或者只需要最后一级的值

:::demo
cascader/demo3
:::


## 远程加载

:::demo
cascader/demo4
:::

## 默认值
:::demo
cascader/demo5
:::




#### Attributes

| 属性名               | 说明                                                         | 类型                                    | 默认值                   |
| -------------------- | ------------------------------------------------------------ | --------------------------------------- | ------------------------ |
| `modelValue`<br/>`v-model` | 选中项绑定值，单选时为 `String`/`Number`，多选时根据 `separator` 为 `String`（逗号分隔）或 `Array` | `String \| Number \| Array`            | `''`                     |
| `fetchData`          | 异步获取数据的函数，接收 `{ ...fetchParams, query }`，返回 `Promise` | `Function`                              | `null`                   |
| `dataFormatter`      | 对异步结果列表进行格式化的回调，接收原始 `list`，返回格式化后的 `list` | `Function`                              | `null`                   |
| `labelKey`           | `el-option` 的标签字段名称                                     | `String`                                | `'label'`                |
| `valueKey`           | `el-option` 的值字段名称                                       | `String`                                | `'value'`                |
| `resultKey`          | 异步结果中列表数据所在的路径                                   | `String`                                | `'data'`                 |
| `dataList`           | 静态数据列表，当 `fetchData` 为 `null` 时使用                  | `Array<Object>`                         | `[]`                     |
| `fetchParams`        | 异步获取数据时的额外参数                                       | `Object`                                | `{}`                     |
| `autoLoad`           | 组件挂载后是否自动加载（仅在非远程模式且 `autoLoad=true` 时生效） | `Boolean`                               | `true`                   |
| `separator`          | 多选模式下是否用逗号分隔来存储值                               | `Boolean`                               | `true`                   |
| `multiple`           | 是否开启多选模式                                               | `Boolean`                               | `false`                  |
| `tag`                | 是否开启 Tag 模式（允许用户输入不存在的选项）                  | `Boolean`                               | `false`                  |
| `remote`             | 是否开启远程搜索模式                                           | `Boolean`                               | `false`                  |
| `group`              | 是否开启分组显示                                               | `Boolean`                               | `false`                  |

#### Events

| 事件名                     | 说明                                                   | 类型               |
| -------------------------- | ------------------------------------------------------ | ------------------ |
| `update:modelValue`        | 在内部 `selectedValue` 改变后，向外同步最新绑定值       | `(payload) ⇒ void` |
| `change`                   | 用户选中值变化后触发（与 `update:modelValue` 顺序相同） | `(val) ⇒ void`     |
| `visibleChange`            | 下拉面板显隐变化时触发，`open: Boolean`                 | `(open) ⇒ void`    |
