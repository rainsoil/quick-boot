# c7-cascader 级联选择器组件

基于 Element Plus 的 `el-cascader` 组件进行封装，提供了更便捷的级联选择功能，支持静态数据、异步数据、懒加载等多种模式。

## 基础用法（静态数据）

使用静态数据列表的级联选择器：

:::demo
cascader/demo1
:::

## 异步数据获取

通过函数动态获取数据的级联选择器：

:::demo
cascader/demo2
:::

## 绑定值类型

支持多种绑定值类型，满足不同业务需求：

:::demo
cascader/demo3
:::

## 懒加载模式

支持动态加载下级节点数据：

:::demo
cascader/demo4
:::

## 默认值设置

支持设置默认选中值：

:::demo
cascader/demo5
:::

## 懒加载模式

支持动态加载下级节点数据：

:::demo
cascader/demo6
:::

## 数据格式化

支持自定义数据格式化函数：

:::demo
cascader/demo7
:::

## 绑定值类型对比

展示不同 resultType 的使用效果：

:::demo
cascader/demo8
:::

## Attributes

| 属性名 | 说明 | 类型 | 默认值 |
|--------|------|------|--------|
| `modelValue` / `v-model` | 选中项绑定值 | `Array` / `String` / `Number` | `[]` |
| `fetchData` | 异步获取数据的函数 | `Function` | `null` |
| `dataFormatter` | 数据格式化回调，接收原始 list，返回格式化后的 list | `Function` | `null` |
| `labelKey` | el-option 的 label 字段名 | `String` | `'label'` |
| `valueKey` | el-option 的 value 字段名 | `String` | `'value'` |
| `resultKey` | 异步结果中列表数据所在的路径 | `String` | `'data'` |
| `dataList` | 静态数据列表，当 fetchData 为 null 时使用 | `Array` | `[]` |
| `fetchParams` | 异步获取数据时的额外参数 | `Object` | `{}` |
| `resultType` | 结果类型 | `Number` | `1` |
| `lazy` | 是否开启懒加载模式 | `Boolean` | `false` |
| `parentNodeFetchFunction` | 当 lazy 为 true 且 resultType = 3 时，获取父级节点数据的函数 | `Function` | `null` |
| `checkStrictly` | 是否严格的遵守父子节点不互相关联 | `Boolean` | `false` |
| `rootParentId` | 根节点的父级 ID | `String` | `-1` |

### resultType 可选值

| 值 | 说明 | 示例 |
|----|------|------|
| `1` | 绑定值为原生数组 | `[1, 2, 3]` |
| `2` | 绑定值为逗号分隔字符串 | `"1,2,3"` |
| `3` | 绑定值为数组的最后一个元素 | `3` |

## Events

| 事件名 | 说明 | 回调参数 |
|--------|------|----------|
| `update:modelValue` | 选中值变化时触发 | `(value: any)` |

## 数据格式

### 静态数据格式

```javascript
const dataList = [
  {
    value: "1",
    label: 'Asia',
    children: [
      {
        value: "2",
        label: 'China',
        children: [
          {value: "3", label: 'Beijing'},
          {value: "4", label: 'Shanghai'},
        ],
      },
    ],
  }
]
```

### 异步数据函数格式

```javascript
const fetchData = (parentId) => {
  return new Promise((resolve) => {
    // 返回数据格式
    resolve({
      data: {
        data: [
          {value: "1", label: 'Option 1'},
          {value: "2", label: 'Option 2'},
        ]
      }
    })
  })
}
```

## 功能特性

1. **多种数据源**：支持静态数据和异步数据获取
2. **懒加载支持**：支持动态加载下级节点数据
3. **多种绑定值**：支持数组、字符串、单值等多种绑定方式
4. **数据格式化**：支持自定义数据格式化函数
5. **父子节点关联**：支持严格的父子节点关联控制
6. **默认值设置**：支持设置默认选中值

## 使用说明

1. **静态数据**：通过 `dataList` 属性传入静态数据
2. **异步数据**：通过 `fetchData` 函数动态获取数据
3. **懒加载**：设置 `lazy: true` 开启懒加载模式
4. **绑定值类型**：通过 `resultType` 控制绑定值的格式
5. **数据格式化**：通过 `dataFormatter` 自定义数据格式化逻辑
