import {IObject} from "../../c7-search/src/search";

export enum IColumnEnum {
    // 输入框类型
    INPUT = 'input',
    // 输入框类型
    INPUT_NUMBER = 'input-number',
    // 输入框类型
    SELECT = 'select',
    // 输入框类型
    DATE_PICKER = 'date',

    DATE_RANGE = 'daterange',
    // 输入框类型
    CHECKBOX = 'checkbox',
    // 输入框类型
    CASCADER = 'cascader',
    // 输入框类型
    UPLOAD = 'upload',

    // 卡槽
    SLOT = 'slot'
}


/**
 * 字段配置
 */
export interface IColumn {
    // 标签文本
    label: string,
    // model 的键名
    prop: string,
    // 标签宽度
    labelWidth?: string,
    // 是否为必填项，如不设置，则会根据校验规则确认
    required?: boolean,

    // 是否显示
    display?: boolean | true,
    // 排序
    order?: number,
    //表单验证规
    rules?: IObject | {},
    // 输入框提示文字
    placeholder?: string | ''
    // 输入框宽度
    columnsWidth?: string | number,
    // 输入框类型
    type?: IColumnEnum,
    span?: number,
    // 字典type
    dictType?: string,
    // 字典数据
    dataList?: IObject[],
    // 函数
    change?: Function,
    // 日期
    // 显示在输入框中的格式
    format?: string
    // 可选，绑定值的格式。 不指定则绑定值为 Date 对象
    valueFormat?: string,

    //范围选择时开始日期的占位内容
    startPlaceholder?: string,

    //范围选择时结束日期的占位内容
    endPlaceholder?: string,

    //选择范围时的分隔符
    rangeSseparator?: string,

    // 可选，选择器打开时默认显示的时间
    defaultValue?: string,
    // 范围选择时选中日期所使用的当日内具体时刻
    defaultTime?: string,

// 组件名称（例如 el-input、el-date-picker 等）
    component?: string
    // 组件的 props 对象
    props?: Record<string, any>
    // 动态插槽配置
    slots?: Record<string, any>

    // 是否使用卡槽
    useSlot?: boolean

    // 卡槽名称
    slotName?: string


    //数值精度
    precision?: number
    // 设置计数器允许的最小值
    min?: number,
    // 设置计数器允许的最大值
    max?: number,

    // 返回结果
    resultType?: number


    // 日期 选择范围时的分隔符
    rangeSeparator: string

    // 分隔符
    separator?: string


    // 文件数量限制
    limit?: number
    // 文件大小限制
    fileSize?: number
    // 文件类型限制
    fileType?: string[]
    // 是否多选
    multiple?: boolean
    // 是否显示提示
    isShowTip?: boolean

    // 上传的url
    uploadUrl?: string

    // 删除的url
    deleteUrl?: string
}

// 定义表单列类型
export interface FormColumn extends IColumn {
    // 基础属性
    span?: number                // 栅格跨度（0-24），默认8
    label: string               // 字段标签
    prop: string                // 字段标识（必须）
    required?: boolean          // 是否必填
    rules?: Record<string, any>  // 验证规则

    // 输入配置
    componentType?: string      // 组件类型（备用）
    disabled?: boolean          // 是否禁用
    placeholder?: string        // 占位符文本

    // 布局相关
    order?: number              // 排序序号
    display?: boolean           // 是否显示（默认true）
    visible?: boolean           // 可见性控制（备用）

    // 事件处理
    change?: (payload: {
        prop: string              // 字段标识
        value: any                // 当前值
        formData: Record<string, any>  // 整体表单数据
    }) => void
}
