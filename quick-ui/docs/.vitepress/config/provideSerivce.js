import {provide} from "vue";
import request from './request';


export function getRequest(path, params, headers) {

    console.log(path)

    return new Promise((resolve, reject) => {
        request({
            url: path,
            params,
            headers,
            method: "GET"
        })
            .then(resolve)
            .catch((error) => {
                if (error !== "-999") {
                    reject(error);
                }
            });
    });
}

export function getDictByType (dictType) {
    return [];
}

/**
 * 发送delete请求
 * @param path 路径
 * @param params 参数
 * @param headers header
 * @returns {Promise | Promise<unknown>}
 */
export function deleteRequest(path, params, headers) {
    return request({
        url: path,
        data: params,
        method: "DELETE"
    });
}
