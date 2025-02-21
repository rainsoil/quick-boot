import {inject} from "vue";


let servicePath = inject<string>('servicePath');
// 请求的类型
type  GetRequest = (path: string, params?: Object, headers?: Record<string, string>) => Promise<any>;
// const servicePath = '../../../docs/.vitepress/config/provideSerivce.js';
// const servicePath2222 = inject('servicePath2');
export default {
    setup() {


    },
    /**
     * 根据字典类型获取字典数据
     * @param dictType
     * @returns {*|*[]}
     */
    async getDictByType(dictType: string): any[] {
        // 从 inject 中获取字典查询函数
        // const injectGetDictByType = inject<GetDictByType>('getDictByType');
        // if (!injectGetDictByType) {
        //     throw new Error('getDictByType 没有提供，请在父组件中通过 provide 注入！');
        // }

        // const modules = import.meta.glob(`../../../docs/.vitepress/config/provideSerivce.js`, {
        //     eager: true,
        // })
        //
        // // 如果只有一个匹配的模块，可以直接获取：
        // const moduleKeys = Object.keys(modules);
        // const injectGetDictByTypeFun = moduleKeys.length > 0 ? modules[moduleKeys[0]] : null
        // if (!injectGetDictByTypeFun) {
        //     throw new Error('getDictByType 没有提供，请在父组件中通过 provide 注入！');
        // }
        if (null == servicePath) {
            servicePath = inject('servicePath');
        }
        console.log("servicePath",servicePath)
        const mod = await import(/* @vite-ignore */ servicePath);
        if (!mod) {
            throw new Error('getDictByType 没有提供，请在父组件中通过 provide 注入！');
        }
        const result = mod.getDictByType(dictType)

        return Array.isArray(result) ? result : [];
    },

    /**
     * 发送 get 请求
     * @param path 路径
     * @param params 参数
     * @param headers header头部
     * @returns {*}
     */
    async getRequest(path: string, params?: Object, headers?: Record<string, string>): Promise<any> {

        if (null == servicePath) {
            servicePath = inject('servicePath');
        }
        console.log("servicePath",servicePath)
        // const servicePath2 = inject<string>('servicePath');
        // if (!servicePath2) {
        //     throw new Error('servicePath2 没有提供，请在父组件中通过 provide 注入！');
        // }
        const mod = await import(/* @vite-ignore */servicePath);
        return mod != null ? mod.getRequest(path, params, headers) :
            Promise.reject('getRequest 没有提供，请在' + servicePath + '中加入！');

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
        let matchedDicts = this.getDictByType(dictType).filter(p => p.dictValue == dictValue);
        return matchedDicts.length > 0 ? matchedDicts[0] : null;
    }


}
