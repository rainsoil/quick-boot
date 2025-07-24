# c7JsonTableColumn 组件文档

## 概述
[c7JsonTableColumn] 是一个基于 Vue 3 和 Element Plus 封装的动态表格列组件，支持通过 JSON 配置动态渲染表格列，包含文本、标签、图片、插槽等多种展示形式。适用于需要动态配置表格列的场景。

### 简单使用
:::demo
jsonTableColumn/demo1
:::

### Props
| 属性名 | 类型 | 默认值 | 说明 |
|-------|------|-------|------|
| columns | `TableColumnProps[]` | - | 列配置数组（必填） |
| modelValue | `Record<string, any>` | `{}` | 表格数据对象 |

### TableColumnProps 配置项
| 属性 | 类型 | 默认值 | 说明 |
|-------|------|-------|------|
| columnType | [ColumnEnumType](file://E:\workspace\luyanan\quick-boot\quick-ui\packages\c7-plus\components\c7-json-table-column\types\JsonTableColumnTypes.ts#L2-L15) | - | 列类型（text/image/tag/slot） |
| label | [string](file://E:\workspace\luyanan\quick-boot\quick-ui\docs\.vitepress\cache\deps\chunk-MKY3YV4O.js#L3345-L3348) | - | 列标题 |
| prop | [string](file://E:\workspace\luyanan\quick-boot\quick-ui\docs\.vitepress\cache\deps\chunk-MKY3YV4O.js#L3345-L3348) | - | 字段名（对应数据属性） |
| visible | [boolean](file://E:\workspace\luyanan\quick-boot\quick-ui\docs\.vitepress\cache\deps\chunk-MKY3YV4O.js#L3329-L3332) | `true` | 是否可见 |
| order | [number](file://E:\workspace\luyanan\quick-boot\quick-ui\docs\.vitepress\cache\deps\chunk-MKY3YV4O.js#L3337-L3340) | - | 排序权重 |

### ColumnEnumType 枚举
