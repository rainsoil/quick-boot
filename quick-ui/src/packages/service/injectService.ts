import {inject, getCurrentInstance} from "vue";
import provideSerivce from '../../service/provideSerivce'

let servicePath = inject<string>('servicePath');
// 请求的类型
type  GetRequest = (path: string, params?: Object, headers?: Record<string, string>) => Promise<any>;
// const servicePath = '../../../docs/.vitepress/config/provideSerivce.js';
// const servicePath2222 = inject('servicePath2');
let cache_instance = getCurrentInstance();
export default {


    /**
     * 根据字典类型获取字典数据
     * @param dictType
     * @returns {*|*[]}
     */
    getDictByType(dictType: string): any[] {
        // const instance = getCurrentInstance();
        const instance = getCurrentInstance();
        if (null == cache_instance && null != instance) {
            cache_instance = instance;
        }
        let result = cache_instance.proxy.$getDictByType(dictType)
        console.log(result)
        return Array.isArray(result) ? result : [];
    },

    /**
     * 发送 get 请求
     * @param path 路径
     * @param params 参数
     * @param headers header头部
     * @returns {*}
     */
    getRequest(path: string, params?: Object, headers?: Record<string, string>): Promise<any> {

        if (null == servicePath) {
            servicePath = inject('servicePath');
        }
        // const servicePath2 = inject<string>('servicePath');
        // if (!servicePath2) {
        //     throw new Error('servicePath2 没有提供，请在父组件中通过 provide 注入！');
        // }
        /*        const mod = import(/!* @vite-ignore *!/servicePath);
                mod.then(mod => {
                    // mod.getRequest(path, params, headers).then(res => {
                    //     console.log(res)
                    // })
                    return mod.getRequest(path, params, headers)
                })
                return null;*/
        const instance = getCurrentInstance();
        if (null == cache_instance && null != instance) {
            cache_instance = instance;
        }
        return cache_instance.proxy.$getRequest(path, params, headers);
        // return mod != null ? mod.getRequest(path, params, headers) :
        //     Promise.reject('getRequest 没有提供，请在' + servicePath + '中加入！');

    },

    /**
     * 发送Delete请求
     * @param path 路径
     * @param params 参数
     * @param headers header头部
     * @returns {*}
     */
    deleteRequest(path: string, params?: Object, headers?: Record<string, string>): Promise<any> {
        // 从 inject 中获取请求函数
        const injectDeleteRequest = inject<GetRequest>('deleteRequest');
        if (!injectDeleteRequest) {
            throw new Error('deleteRequest 没有提供，请在父组件中通过 provide 注入！');
        }

        return injectDeleteRequest(path, params, headers);
    },

    /**
     * 匹配字典项
     * @param dictType
     * @param dictValue
     */
    getDictLabel(dictType: string, dictValue: string) {
        let res = this.getDictByType(dictType);
        console.log(res)
        let matchedDicts = res ? res.filter(p => p.dictValue == dictValue) : [];
        return matchedDicts.length > 0 ? matchedDicts[0] : null;
    }


}
