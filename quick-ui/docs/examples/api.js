import axios from "axios";

export function getSelectData() {
    return axios.get("https://m1.apifoxmock.com/m1/4742948-4395863-default/mockapi/dict/sex");

}


// 获取级联数据
export function getCascaderData() {
    return axios.get("https://m1.apifoxmock.com/m1/4742948-4395863-default/mockapi/cascader");
}


// 获取级联数据
export function getCascaderDataLazy(parentId) {
    console.log("getCascaderDataLazy", parentId)
    return axios.get("https://m1.apifoxmock.com/m1/4742948-4395863-default/mockapi/cascader/lazy?parentId=" + parentId);
}

// 获取父级级数据
export function parentNodeFetchFunction(value) {
    return axios.get("https://m1.apifoxmock.com/m1/4742948-4395863-default/mockapi/cascader/parentCode?value=" + value);
}


// crud列表
export function getCrudData(param) {
    console.log("getCrudData",param)
    return axios.get("https://m1.apifoxmock.com/m1/4742948-4395863-default/crud/page", {
        params: {

            ...param
        }
    });
}
