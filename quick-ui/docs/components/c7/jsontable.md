#  简单表格

## 基础用法

:::demo
jsontable/demo
:::

### 基础表格功能

展示基础表格功能的使用：

:::demo
jsontable/demo1
:::

### 高级表格功能

展示高级表格功能的使用：

:::demo
jsontable/demo2
:::

### 复杂表格功能

展示复杂表格功能的使用：

:::demo
jsontable/demo3
:::demo
jsontable/demo
:::



## c7-json-table 组件文档

### Attributes

| 属性名       | 说明               | 类型    | 默认值              |
|------------|--------------------|-------|-------------------|
| init       | 是否初始化加载数据     | Boolean | true              |
| initParam  | 初始化请求参数        | Object | {}                |
| listFunction | 数据列表请求函数      | Function | -                |
| rowKey     | 行唯一标识字段名       | String | 'id'              |
| pageTotal  | 总页数              | Number | -                 |
| pagePage   | 当前页码            | Number | 1                 |
| pageLimit  | 每页数据量           | Number | 10                |
| pageSizes  | 分页选项            | Array  | [10, 20, 30, 50]  |
| pageLayout | 分页布局            | String | 'prev, pager, next, jumper' |
| pageBackground | 分页背景色        | Boolean | true              |
| pageAutoScroll | 分页自动滚动       | Boolean | true              |
| pageHidden | 是否隐藏分页         | Boolean | false             |
| showSearch | 是否显示搜索栏        | Boolean | true              |
| rowsKey    | 数据列表字段名        | String | 'rows'            |
| totalKey   | 总数据量字段名        | String | 'total'           |
| labelWidth | 表单标签宽度          | Number | 100               |
| searchParam | 搜索参数对象         | Object | {}                |
| searchColumns | 搜索表单配置        | Array  | []                |
| tableColumns | 表格列配置          | Array  | []                |

### Slots

| 插槽名     | 说明               | 作用域参数                                 |
|----------|--------------------|----------------------------------------|
| search   | 自定义搜索表单         | 搜索参数 |
| operate  | 自定义操作栏          | -                                      |
| default  | 表格内容插槽          | `slotProps`: 表格行数据                  |
| [slotName] | 动态列插槽          | `item`: 列配置, [searchParam]  |

### Events

| 事件名         | 说明               | 参数类型                          |
|--------------|--------------------|---------------------------------|
| handleFormChange | 表单字段变化时触发      | Function(data: Record<string, any>) |

