export interface IObject<T = any> {
    [key: string]: T;
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


}




/**
 * 按钮配置
 */
export interface IButton {
    // 是否开启
    enable: boolean,
    // 按钮文本
    label: string,

    // 是否显示
    display: boolean,
    // 是否禁用
    disabled: boolean,

    // type
    type: string,
    // icon
    icon: string,
}

export enum IColumnEnum {
    // 输入框类型
    INPUT = 'input',
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
