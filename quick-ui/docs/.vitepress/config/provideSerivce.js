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

/**
 * 通用post方法
 * @param path
 * @param body
 * @returns
 */
export function postRequest(path, body, headers) {
    return new Promise((resolve, reject) => {
        request({
            url: path,
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=UTF-8",
                ...headers
            },
            data: body
        })
            .then(resolve)
            .catch((error) => {
                if (error !== "-999") {
                    reject(error);
                }
            });
    });
}


export function getDictByType(dictType) {
    let dict = [{
        label: "男",
        value: "0"
    },
        {
            label: "女",
            value: "1"
        },


    ];

    return dict;
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


/**
 * 打开新的页面
 * @param path
 */
export function openNewTab(path) {
    window.open(path)
}
