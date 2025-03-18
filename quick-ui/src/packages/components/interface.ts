/**************通用***************/

/**********表格*************/

/**
 * 表格字段
 */
export interface ITableColumn {

    /**
     * 显示的标题
     */
    label: string,

    /**
     * 字段名称
     */
    prop: string,
    /**
     * 排序
     */
    order?: number,

    /**
     * 字段类型
     */
    type?: ITableColumnType,

    /**
     * 宽度
     */
    width?: string,


    minWidth?: string,


    props?: Record<string, any>,
    /**
     * 格式化
     * @param row
     * @param column
     */
    formatter?: Function,


    /**
     * 是否使用卡槽
     */
    isSlot?: boolean,
    /**
     * 卡槽名称
     */
    slotName?: string,
}


/**
 * 字段类型
 */
export enum ITableColumnType {
    INPUT = 'text',
    SLOT = 'slot',
    IMAGE = 'image',

}
