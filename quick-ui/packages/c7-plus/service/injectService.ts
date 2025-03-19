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
    console.log(res)
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


export const injectService: IinjectService = {
    getRequest,
    postRequest,
    deleteRequest,
    getDictByType,
    getDictLabel,
    openNewTab,
}
