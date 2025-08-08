 # c7-checkbox 复选框组件

 基于 Element Plus 的 `el-checkbox` 组件进行封装，提供了更便捷的多选功能，支持静态数据、异步数据、全选功能等多种模式。

 ## 基础用法

 最简单的复选框使用方式：

 :::demo
 checkbox/demo1
 :::

 ## 返回结果格式

 支持数组和字符串两种返回格式：

 :::demo
 checkbox/demo2
 :::

 ## 全选功能

 支持全选/全不选功能：

 :::demo
 checkbox/demo3
 :::

 ## 按钮样式

 支持按钮样式的复选框：

 :::demo
 checkbox/demo4
 :::

 ## 事件监听

 支持 change 事件监听：

 :::demo
 checkbox/demo5
 :::

 ## 异步数据获取

 支持通过函数动态获取数据：

 :::demo
 checkbox/demo6
 :::

 ## 异步数据示例

 展示异步数据获取的完整流程：

 :::demo
 checkbox/demo7
 :::

 ## 数据格式化

 支持自定义数据格式化函数：

 :::demo
 checkbox/demo8
 :::

 ## 绑定值类型对比

 展示不同绑定值类型的使用效果：

 :::demo
 checkbox/demo9
 :::

 ## Attributes

 | 属性名 | 说明 | 类型 | 默认值 |
 |--------|------|------|--------|
 | `modelValue` / `v-model` | 选中项绑定值，支持数组或逗号分隔字符串 | `Array` / `String` | `[]` |
 | `fetchData` | 异步获取数据的函数 | `Function` | `null` |
 | `dataList` | 静态数据列表，当 fetchData 为 null 时使用 | `Array` | `[]` |
 | `fetchParams` | 异步获取数据时的额外参数 | `Object` | `{}` |
 | `resultKey` | 异步结果中列表数据所在的路径 | `String` | `'data'` |
 | `dataFormatter` | 数据格式化回调，接收原始 list，返回格式化后的 list | `Function` | `null` |
 | `indeterminate` | 是否显示"全选"复选框 | `Boolean` | `false` |
 | `separator` | 是否将选中值转换为逗号分隔的字符串 | `Boolean` | `true` |
 | `labelKey` | 数据项中标签字段的键名 | `String` | `'label'` |
 | `valueKey` | 数据项中值字段的键名 | `String` | `'value'` |
 | `button` | 是否使用按钮样式复选框 | `Boolean` | `false` |

 ## Events

 | 事件名 | 说明 | 回调参数 |
 |--------|------|----------|
 | `update:modelValue` | 当绑定值发生变化时触发 | `(value: Array | String)` |
 | `change` | 当选中项发生变化时触发 | `(value: Array)` |

 ## 数据格式

 ### 静态数据格式

 ```javascript
 const dataList = [
   { label: '苹果', value: 'apple' },
   { label: '香蕉', value: 'banana' },
   { label: '橙子', value: 'orange' }
 ]
 ```

 ### 异步数据函数格式

 ```javascript
 const fetchData = () => {
   return new Promise((resolve) => {
     resolve({
       data: [
         { label: '选项1', value: 'option1' },
         { label: '选项2', value: 'option2' },
         { label: '选项3', value: 'option3' }
       ]
     })
   })
 }
 ```

 ## 功能特性

 1. **多种数据源**：支持静态数据和异步数据获取
 2. **全选功能**：支持全选/全不选功能，自动处理中间状态
 3. **多种绑定值**：支持数组和字符串两种绑定方式
 4. **按钮样式**：支持按钮样式的复选框
 5. **数据格式化**：支持自定义数据格式化函数
 6. **事件监听**：支持 change 事件监听

 ## 使用说明

 1. **静态数据**：通过 `dataList` 属性传入静态数据
 2. **异步数据**：通过 `fetchData` 函数动态获取数据
 3. **全选功能**：设置 `indeterminate: true` 启用全选功能
 4. **绑定值类型**：通过 `separator` 控制返回值的格式
 5. **按钮样式**：设置 `button: true` 使用按钮样式
 6. **数据格式化**：通过 `dataFormatter` 自定义数据格式化逻辑
