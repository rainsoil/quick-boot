# c7-select 下拉选择器

当选项过多时，使用下拉菜单展示并选择内容。基于 Element Plus 的 `el-select` 组件进行封装，提供了更强大的数据处理和交互功能。

> **v2.0 优化亮点**：
> - ✅ **数据处理优化**：提取工具函数，支持多种数据格式转换
> - ✅ **性能提升**：使用计算属性缓存，避免重复计算
> - ✅ **代码简化**：清理冗余代码，统一数据处理逻辑
> - ✅ **类型安全**：完整的 TypeScript 类型定义支持
> - ✅ **工具集成**：集成 c7-plus 数据格式化工具

## 基础用法
适用广泛的基础单选 v-model 的值为当前被选中的 el-option 的 value 属性值
:::demo

select/demo
:::

## 默认值
适用需要设置默认值，v-model 的值为当前被选中的 el-option 的 value 值
:::demo
select/demo1

:::



##  使用函数
数据项可以调用函数获取

:::demo
select/demo2

:::


##  禁用选项
禁用选项
:::demo
select/demo3

:::

##  指定标签和文本
指定标签和文本

:::demo
select/demo4

:::

## 基础多选
多选选择器使用 tag 组件来展示已选中的选项。


:::demo
select/demo5

:::

## 创建条目和搜索

创建并选中未包含在初始选项中的条目。
:::demo
select/demo6

:::

## 自定义模板
您可以自定义标签。
:::demo
select/demo7

:::


## change事件回调
监听事件
:::demo
select/demo8
:::

## 远程加载
输入关键字以从远程服务器中查找数据。
:::demo
select/demo9
:::

## 分组展示
你可以为选项进行分组来区分不同的选项

:::demo
select/demo10
:::

### 高级选择功能

展示各种高级选择功能的使用：

:::demo
select/demo11
:::

### 复杂选择功能

展示复杂选择功能的使用：

:::demo
select/demo12
:::

### 实际应用场景

展示在实际应用中的使用场景：

:::demo
select/demo13
:::

### 性能优化演示

展示优化后的性能特性：

:::demo
select/demo14
:::

## Attributes

| 属性名       | 说明                                       | 类型                     | 可选值           | 默认值              |
| ------------ | ------------------------------------------ | ------------------------ | ---------------- | ------------------- |
| modelValue   | 绑定值，支持 String/Number/Array           | `String` \| `Number` \| `Array` | —                | `''`                |
| fetchData    | 异步获取数据的函数，统一用于初始化加载与远程搜索 | `Function`               | —                | `null`              |
| dataFormatter| 数据格式化回调，接收原始 list，返回格式化后的 list | `Function`               | —                | `null`              |
| labelKey     | el-option 的 label 字段名                  | `String`                 | —                | `'label'`           |
| valueKey     | el-option 的 value 字段名                  | `String`                 | —                | `'value'`           |
| resultKey    | 异步结果中列表数据所在的路径                | `String`                 | —                | `'data'`            |
| dataList     | 静态数据列表，当 fetchData 为 null 时使用   | `Array`                  | —                | `[]`                |
| fetchParams  | 异步获取数据时的额外参数                   | `Object`                 | —                | `{}`                |
| autoLoad     | 是否在组件挂载后自动加载数据（仅在非远程模式） | `Boolean`                | `true` \| `false`| `true`              |
| separator    | 多选模式下是否使用逗号分隔符来存储         | `Boolean`                | `true` \| `false`| `true`              |
| multiple     | 是否开启多选模式                           | `Boolean`                | `true` \| `false`| `false`             |
| tag          | 是否开启 tag 模式（允许用户输入不存在的选项） | `Boolean`                | `true` \| `false`| `false`             |
| remote       | 是否远程搜索模式                           | `Boolean`                | `true` \| `false`| `false`             |
| group        | 是否开启分组                               | `Boolean`                | `true` \| `false`| `false`             |

## Events

| 事件名 | 说明 | 回调参数 |
|--------|------|----------|
| `update:modelValue` | 选中值发生变化时触发 | `(value: any)` |
| `change` | 选中值发生变化时触发 | `(value: any)` |
| `visible-change` | 下拉框出现/隐藏时触发 | `(visible: boolean)` |
| `remove-tag` | 多选模式下移除tag时触发 | `(tag: any)` |
| `clear` | 可清空的单选模式下用户点击清空按钮时触发 | — |
| `blur` | 当 input 失去焦点时触发 | `(event: FocusEvent)` |
| `focus` | 当 input 获得焦点时触发 | `(event: FocusEvent)` |

## 插槽

| 插槽名 | 说明 |
|--------|------|
| `default` | Option 组件列表 |
| `label` | 自定义标签内容 |

## 性能优化说明

### 数据处理优化

组件使用了优化的数据处理逻辑：

```javascript
// 工具函数：标准化值处理
const normalizeValue = (value, multiple, separator) => {
  if (!multiple) return value
  if (typeof value === 'string') {
    return value === '' ? [] : value.split(',')
  }
  return Array.isArray(value) ? value : value
}

// 工具函数：反标准化值处理  
const denormalizeValue = (value, multiple, separator) => {
  if (!multiple || !separator || !Array.isArray(value)) {
    return value
  }
  return value.join(',')
}
```

### 计算属性缓存

- **selectedValue**：使用计算属性处理双向绑定，避免重复计算
- **数据格式化**：缓存格式化后的选项数据
- **性能提升**：减少不必要的重新渲染

## 工具函数支持

组件现已集成 c7-plus 数据处理工具：

- **数据格式化**：`useDataFormatter` - 完整的数据格式化工具集
- **选项处理**：`formatOptionsData` - 标准化选项数据格式
- **树形数据**：`formatTreeData` - 支持树形选项数据
- **数据查找**：`findOptionByValue` - 快速查找选项
- **路径获取**：`getOptionPath` - 获取选项完整路径

### 使用示例

```javascript
import { useDataFormatter, formatOptionsData } from 'c7-plus'

// 使用数据格式化工具
const { formatOptions } = useDataFormatter({
  labelKey: 'name',
  valueKey: 'id'
})

// 格式化API返回的数据
const formattedOptions = formatOptions(apiData)
```

## 注意事项

1. **数据格式**：确保 `fetchData` 返回的数据格式符合 `resultKey` 配置
2. **性能优化**：大量数据时建议使用远程搜索模式
3. **多选处理**：多选模式下注意 `separator` 配置对数据存储的影响
4. **类型安全**：推荐在 TypeScript 项目中使用，享受完整的类型检查
5. **工具函数**：合理使用内置的数据处理工具，避免重复开发

## 最佳实践

1. **数据获取**：使用 `fetchData` 统一处理数据获取逻辑
2. **格式化**：使用 `dataFormatter` 标准化不同来源的数据
3. **性能优化**：合理使用 `autoLoad` 和 `remote` 配置
4. **用户体验**：为异步操作提供适当的加载状态提示