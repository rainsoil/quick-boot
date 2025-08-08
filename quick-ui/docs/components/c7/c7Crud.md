# c7-crud 表格组件

一个集成了搜索、表格、分页功能的完整 CRUD 组件，基于 Element Plus 的表格和分页组件进行封装。

## 基础用法

最简单的使用方式，包含搜索、表格和分页功能：

:::demo
crud/demo1
:::

## 高级用法

包含更多自定义配置的用法：

:::demo
crud/demo2
:::

## Attributes

| 属性名 | 说明 | 类型 | 默认值 |
|--------|------|------|--------|
| `init` | 是否在组件挂载时自动初始化数据 | Boolean | `true` |
| `initParam` | 初始化时的额外参数 | Object | `{}` |
| `listFunction` | 获取列表数据的函数 | Function | - |
| `rowKey` | 行主键字段（用于多选返回主键集合） | String | `'id'` |
| `pageTotal` | 总数据条数 | Number | - |
| `pagePage` | 当前页码 | Number | `1` |
| `pageLimit` | 每页显示条数 | Number | `10` |
| `pageSizes` | 每页条数选项 | Array | `[10, 20, 30, 50]` |
| `pageLayout` | 分页器布局 | String | `'prev, pager, next, jumper'` |
| `pageBackground` | 分页器是否有背景色 | Boolean | `true` |
| `pageAutoScroll` | 分页时是否自动滚动到顶部 | Boolean | `true` |
| `pageHidden` | 是否隐藏分页器 | Boolean | `false` |
| `showSearch` | 是否显示搜索区域 | Boolean | `true` |
| `rowsKey` | 数据列表在响应中的路径 | String | `'rows'` |
| `totalKey` | 总数在响应中的路径 | String | `'total'` |
| `labelWidth` | 搜索表单标签宽度 | Number | `100` |
| `searchParam` | 搜索参数对象 | Object | `{}` |

## Events

| 事件名 | 说明 | 回调参数 |
|--------|------|----------|
| `update:showSearch` | 搜索区域显示状态变化时触发 | `(showSearch: boolean)` |
| `selection-change` | 多选变化时触发 | `(selectedKeys: string[])` |
| `update:searchParam` | 搜索参数变化时触发 | `(searchParam: object)` |
| `cleanParam` | 重置搜索参数时触发 | - |

## Slots

| 插槽名 | 说明 |
|--------|------|
| `search` | 搜索区域内容，作用域参数：`{ queryParams }` |
| `operate` | 操作按钮区域内容 |
| `default` | 表格列定义区域 |

## 注意事项

1. **listFunction 要求**：必须返回 Promise 或支持 async/await 的函数
2. **数据格式**：组件会自动从响应中提取 `rowsKey` 和 `totalKey` 指定的数据
3. **搜索参数**：通过 `searchParam` 属性传入，组件会自动合并到请求参数中
4. **多选功能**：组件内置了多选列，选中行会返回 `rowKey` 指定的主键值
5. **分页处理**：组件自动处理分页逻辑，包括页码和每页条数的变化
