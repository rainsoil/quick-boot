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
| columnType | [ColumnEnumType](JsonTableColumnTypes.ts) | - | 列类型（text/image/tag/slot） |
| label | [string] | - | 列标题 |
| prop | [string] | - | 字段名（对应数据属性） |
| visible | [boolean] | `true` | 是否可见 |
| order | [number] | - | 排序权重 |

### ColumnEnumType 枚举

```
import {IObject} from "../../c7-search/src/search";

export enum ColumnEnumType {

    // 文本
    TEXT = 'text',

    // 图片
    IMAGE = 'image',

    // tag
    TAG = 'tag',
    // 卡槽
    SLOT = 'slot',

}


/**
 * 列配置属性定义
 * 对应 el-table-column 组件的所有可配置属性
 */
export interface TableColumnProps {
    // 类型
    columnType: ColumnEnumType;

    //是否显示 visible
    visible?: boolean;

    // 排序order
    order?: number;

    /**
     * 列的类型
     * - selection: 显示多选框
     * - index: 显示行索引（从1开始）
     * - expand: 显示展开按钮
     */
    type?: 'selection' | 'index' | 'expand';

    /**
     * 自定义索引值（当 type=index 时生效）
     * 可以是数字或返回索引值的函数
     */
    index?: number | ((row: any, rowIndex: number) => number);

    /**
     * 列标题显示文本
     */
    label?: string;

    /**
     * 列的唯一标识key（用于filter-change事件标识）
     */
    columnKey?: string;

    /**
     * 字段名称（对应数据对象的属性名）
     */
    prop?: string;

    /**
     * 列宽度（固定值）
     */
    width?: string | number;

    /**
     * 列最小宽度（弹性布局时使用）
     */
    minWidth?: string | number;

    /**
     * 列固定位置
     * - true: 固定在左侧
     * - 'left': 固定在左侧
     * - 'right': 固定在右侧
     */
    fixed?: boolean | 'left' | 'right';

    /**
     * 自定义表头渲染函数
     * @param h 渲染函数
     * @param column 列配置
     * @param $index 列索引
     */
    renderHeader?: (h: any, column: any, $index: number) => any;

    /**
     * 排序配置
     * - true: 本地排序
     * - 'custom': 自定义排序（需监听sort-change事件）
     */
    sortable?: boolean | 'custom';

    /**
     * 自定义排序方法（当sortable=true时生效）
     * @param a 第一个比较对象
     * @param b 第二个比较对象
     */
    sortMethod?: (a: any, b: any) => number;

    /**
     * 排序字段（当未设置sortMethod时生效）
     */
    sortBy?: string | string[] | Record<string, any>;

    /**
     * 排序策略轮转顺序
     * 默认顺序：['ascending', 'descending', null]
     */
    sortOrders?: ('ascending' | 'descending' | null)[];

    /**
     * 是否允许调整列宽（需设置border属性）
     */
    resizable?: boolean;

    /**
     * 内容格式化函数
     * @param row 当前行数据
     * @param column 列配置
     * @param cellValue 单元格值
     */
    formatter?: (row: any, column: any, cellValue: any) => string;

    /**
     * 内容过长时显示tooltip
     */
    showOverflowTooltip?: boolean | Record<string, any>;

    /**
     * 列对齐方式
     * - left: 左对齐
     * - center: 居中对齐
     * - right: 右对齐
     */
    align?: 'left' | 'center' | 'right';

    /**
     * 表头对齐方式（未设置则继承表格对齐方式）
     */
    headerAlign?: 'left' | 'center' | 'right';

    /**
     * 列自定义类名
     */
    className?: string;

    /**
     * 列标题自定义类名
     */
    labelClassName?: string;

    /**
     * 多选列的可选状态判断函数
     * @param row 当前行数据
     * @param index 行索引
     */
    selectable?: (row: any, index: number) => boolean;

    /**
     * 数据刷新后是否保留已选项
     */
    reserveSelection?: boolean;

    /**
     * 数据过滤选项配置
     * 每个选项需包含text和value属性
     */
    filters?: { text: string; value: any }[];

    /**
     * 过滤弹出框定位策略
     */
    filterPlacement?: string;

    /**
     * 过滤弹出框自定义类名（v2.5.0+）
     */
    filterClassName?: string;

    /**
     * 过滤是否支持多选
     */
    filterMultiple?: boolean;

    /**
     * 自定义过滤方法
     * @param value 过滤值
     * @param row 当前行数据
     * @param column 列配置
     */
    filterMethod?: (value: any, row: any, column: any) => boolean;

    /**
     * 已选中的过滤值
     */
    filteredValue?: Record<string, any>;

    /**
     * 自定义tooltip内容（v2.9.4+）
     * @param row 当前行数据
     */
    tooltipFormatter?: (row: any) => string;


    // 数据列表
    dictList: IObject[];

    // 卡槽名称
    slotName: string;
}


```
