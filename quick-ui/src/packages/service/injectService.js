import {getCurrentInstance} from "vue";

let cache_instance = getCurrentInstance();
console.log(cache_instance)
export default {


    /**
     * 根据字典类型获取字典数据
     * @param dictType
     * @returns {*|*[]}
     */
    getDictByType(dictType) {
        let result = window.$getDictByType(dictType)
        return Array.isArray(result) ? result : [];
    },

    /**
     * 发送 get 请求
     * @param path 路径
     * @param params 参数
     * @param headers header头部
     * @returns {*}
     */
    getRequest(path, params, headers) {


        return window.$getRequest(path, params, headers);
    },


    /**
     * 发送 get 请求
     * @param path 路径
     * @param params 参数
     * @param headers header头部
     * @returns {*}
     */
    postRequest(path, params
        ,
                headers
    ) {


        return window.$postRequest(path, params, headers);
    },
    /**
     * 发送Delete请求
     * @param path 路径
     * @param params 参数
     * @param headers header头部
     * @returns {*}
     */
    deleteRequest(path
        , params
        ,
                  headers
    ) {
        // 从 inject 中获取请求函数

        return window.$deleteRequest(path, params, headers);
    },

    /**
     * 匹配字典项
     * @param dictType
     * @param dictValue
     */
    getDictLabel(dictType,
                 dictValue
    ) {
        let res = this.getDictByType(dictType);
        console.log(res)
        let matchedDicts = res ? res.filter(p => p.value == dictValue) : [];
        return matchedDicts.length > 0 ? matchedDicts[0] : null;
    }
    ,
    /**
     * 打开新的页面
     * @param path
     */
    openNewTab(path) {

        return window.$openNewTab(path);
    },


    getFunctionTarget() {


        if (cache_instance) {
            return cache_instance.proxy;
        }
        const instance = getCurrentInstance();
        if (null != instance) {
            cache_instance = instance.proxy;
        }

        if (null == cache_instance) {

            cache_instance = window;
        }
        return cache_instance;

    }

}
