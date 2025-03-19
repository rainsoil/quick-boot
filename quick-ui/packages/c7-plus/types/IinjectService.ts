import {IDict, IObject} from "./ITypes";

export interface IinjectService {
    /**
     * 发送get请求
     * @param path 路径(必填)
     * @param params 参数
     * @param headers 头部
     */
    getRequest(path: string, params?: IObject | {}, headers?: IObject | {}): Promise<any>;

    /**
     * 发送post请求
     * @param path 路径
     * @param params 参数
     * @param headers 头部
     */
    postRequest(path: string, params?: IObject | {}, headers?: IObject | {}): Promise<any>;

    /**
     * 发送delete请求
     * @param path 路径
     * @param params 参数
     * @param headers 头部
     */
    deleteRequest(path: string, params?: IObject | {}, headers?: IObject | {}): Promise<any>;

    /**
     * 根据type查询字典
     * @param dictType
     */
    getDictByType(dictType: string): IDict[];

    /**
     * 根据字典type和字典值获取字典名称
     * @param dictType 字典类型
     * @param dictValue 字典值
     */
    getDictLabel(dictType: string, dictValue: string): IDict;

    /**
     * 打开新的tab
     * @param path
     */
    openNewTab(path: string): void;
}
