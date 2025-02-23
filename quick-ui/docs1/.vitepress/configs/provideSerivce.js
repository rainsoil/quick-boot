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

export function getDictByType(dictType) {
    return [];
}


