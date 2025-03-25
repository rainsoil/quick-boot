import {IDict, IObject} from "../types/ITypes";
import {IinjectService} from "../types/IinjectService";

/**
 * 发送get请求
 * @param path 路径(必填)
 * @param params 参数
 * @param headers 头部
 */
const getRequest = (path: string, params?: IObject | {}, headers?: IObject | {}) => {
    return window.$getRequest(path, params, headers);
}

/**
 * 发送post请求
 * @param path 路径
 * @param params 参数
 * @param headers 头部
 */
const postRequest = (path: string, params?: IObject | {}, headers?: IObject | {}) => {
    return window.$postRequest(path, params, headers);
}

/**
 * 发送delete请求
 * @param path 路径
 * @param params 参数
 * @param headers 头部
 */
const deleteRequest = (path: string, params?: IObject | {}, headers?: IObject | {}) => {
    return window.$deleteRequest(path, params, headers);
}

/**
 * 根据type查询字典
 * @param dictType
 */
const getDictByType = (dictType: string) => {
    let result = window.$getDictByType(dictType)
    return Array.isArray(result) ? result : [];
}

/**
 * 根据字典type和字典值获取字典名称
 * @param dictType 字典类型
 * @param dictValue 字典值
 */
const getDictLabel = (dictType: string, dictValue: string) => {
    let res = getDictByType(dictType);
    let matchedDicts = res ? res.filter(p => p.value == dictValue) : [];
    return matchedDicts.length > 0 ? matchedDicts[0] : null;
}

/**
 * 打开新的tab
 * @param path
 */
const openNewTab = (path: string) => {
    window.$openNewTab(path);
}
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
const download = (path?: string, params?: IObject, filename?: string, headers?: IObject) => {
    window.$download(path, params, filename, headers);
}


export const injectService: IinjectService = {
    getRequest,
    postRequest,
    deleteRequest,
    getDictByType,
    getDictLabel,
    openNewTab,
    download
}
