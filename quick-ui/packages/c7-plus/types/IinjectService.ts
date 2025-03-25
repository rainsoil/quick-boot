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



    /**
     * 下载文件或数据。
     *
     * 该函数用于从指定路径下载文件或数据，并支持自定义请求参数、文件名和请求头。
     *
     * @param {string} [path] - 下载资源的路径。如果未提供，则使用默认路径。
     * @param {IObject} [params] - 请求参数，通常为键值对对象。如果未提供，则不传递任何参数。
     * @param {string} [filename] - 下载文件的名称。如果未提供，则使用默认文件名或从响应头中获取。
     * @param {IObject} [headers] - 请求头信息，通常为键值对对象。如果未提供，则使用默认请求头。
     */
    download(path?: string, params?: IObject, filename?: string, headers?: IObject): void;
}
