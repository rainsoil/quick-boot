# tableHook
> 对表格的常用操作进行封装的一个Hook

## 基础用法
:::demo
hooks/tableView
:::


## 使用
```
import { tableHook, IViewHooksOptions} from "c7-plus";
```

## Attributes


| 属性名 | 说明 | 类型 | 是否必填 | 可选值 | 默认值 |
|--------|------|------|----------|--------|--------|
| createdIsNeed | 此页面是否在创建时，调用查询数据列表接口？ | boolean | 否 | - | `true` |
| activatedIsNeed | 此页面是否在激活（进入）时，调用查询数据列表接口？ | boolean | 否 | - | `false` |
| getDataListURL | 数据列表接口，API地址 | string | 否 | - | `''` |
| getDataListIsPage | 数据列表接口，是否需要分页？ | boolean | 否 | - | `true` |
| deleteURL | 删除接口，API地址 | string | 否 | - | `""` |
| deleteIsBatch | 删除接口，是否需要批量？ | boolean | 否 | - | `true` |
| deleteIsBatchKey | 删除接口，批量状态下由那个key进行标记操作？ | string | 否 | - | `'id'` |
| exportURL | 导出接口，API地址 | string | 否 | - | `''` |
| dataForm | 查询条件 | IObject | 否 | - | `{}` |
| dataList | 数据列表 | IObject[] | 否 | - | `[]` |
| order | 排序，asc／desc | string | 否 | - | `''` |
| orderField | 排序，字段 | string | 否 | - | `''` |
| page | 当前页码 | number | 否 | - | `1` |
| limit | 每页数 | number | 否 | - | `10` |
| total | 总条数 | number | 否 | - | `0` |
| dataListLoading | 数据列表，loading状态 | boolean | 否 | - | `false` |
| dataListSelections | 数据列表，多选项 | IObject[] | 否 | - | `[]` |
| dataListSelectionsIds | 多选的id集合 | IObject[] | 否 | - | `[]` |

## Exposes
| 方法名 | 说明 | Type |
|--------|------|------|
| `query` | 查询列表记录 | `() => void` |
| `dataListSelectionChangeHandle` | 列表多选事件 | `(list: IObject[]) => void` |
| `dataListSortChangeHandle` | 列表排序事件 | `(sort: IObject) => void` |
| `pageSizeChangeHandle` | 列表切换每页显示数量事件 | `(pageSize: number) => void` |
| `pageCurrentChangeHandle` | 列表分页事件 | `(pageIndex: number) => void` |
| `getDataList` | 列表搜索事件 | `() => void` |
| `deleteHandle` | 列表删除事件 | `(id?: string) => Promise<any>` |
| `exportHandle` | 列表导出事件 | `() => void` |
| `handleReset` | 重置表单 | `(param?: IObject) => void` |
| `getDictLabel` | 获取字典名称 | `(dictType: string, dictValue: string) => string | any` |
