import {IObject} from "./ITypes";

export interface IViewHooksOptions {
    //  设置属性
    /**
     * 此页面是否在创建时，调用查询数据列表接口？
     */
    createdIsNeed?: boolean;
    /**
     * 此页面是否在激活（进入）时，调用查询数据列表接口？
     */
    activatedIsNeed?: boolean;
    /**
     * 数据列表接口，API地址
     */
    getDataListURL?: string;


    /**
     * 数据列表接口，是否需要分页？
     */
    getDataListIsPage?: boolean;
    /**
     * 删除接口，API地址
     */
    deleteURL?: string;
    /**
     * 删除接口，是否需要批量？
     */
    deleteIsBatch?: boolean;
    /**
     * 删除接口，批量状态下由那个key进行标记操作？比如：pid，uid...
     */
    deleteIsBatchKey?: string;
    /**
     * 导出接口，API地址
     */
    exportURL?: string;

    /**
     * 查询条件
     */
    dataForm?: IObject;
    /**
     * 数据列表
     */
    dataList?: IObject[];
    /**
     * 排序，asc／desc
     */
    order?: string;
    /**
     * 排序，字段
     */
    orderField?: string;
    /**
     * 当前页码
     */
    page?: number;
    /**
     * 每页数
     */
    limit?: number;
    /**
     * 总条数
     */
    total?: number;
    /**
     * 数据列表，loading状态
     */
    dataListLoading?: boolean;
    /**
     * 数据列表，多选项
     */
    dataListSelections?: IObject[];
    /**
     * 多选的id集合
     */
    dataListSelectionsIds?: IObject[];
}

export interface IViewHooks extends IViewHooksOptions, IObject {

    /**
     * 查询列表记录
     */
    query: () => void;
    /**
     * 列表多选事件
     */
    dataListSelectionChangeHandle: (list: IObject[]) => void;
    /**
     * 列表排序事件
     */

    dataListSortChangeHandle: (sort: IObject) => void;
    /**
     * 列表切换每页显示数量事件
     */
    pageSizeChangeHandle: (pageSize: number) => void;
    /**
     * 列表分页事件
     */
    pageCurrentChangeHandle: (pageIndex: number) => void;
    /**
     * 列表搜索事件
     */
    getDataList: () => void;
    /**
     * 列表删除事件
     */
    deleteHandle: (id?: string) => Promise<any>;
    /**
     * 列表导出事件
     */
    exportHandle: () => void;

    /**
     * 重置表单
     * @param param
     */
    handleReset: (param?: IObject) => void;
    /**
     * 获取字典名称
     */
    getDictLabel: (dictType: string, dictValue: string) => string | any;
}
