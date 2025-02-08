import {inject} from "vue";

// 字段type类型
type  GetDictByType = (dictType: string) => Promise<any>;

// 请求的类型
type  GetRequest = (path: string, params?: Object, headers?: Record<string, string>) => Promise<any>;


export default {
    /**
     * 根据字典类型获取字典数据
     * @param dictType
     * @returns {*|*[]}
     */
    getDictByType(dictType: string): any[] {
        // 从 inject 中获取字典查询函数
        const injectGetDictByType = inject<GetDictByType>('getDictByType');
        if (!injectGetDictByType) {
            throw new Error('getDictByType 没有提供，请在父组件中通过 provide 注入！');
        }

        const result = injectGetDictByType(dictType);
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
        // 从 inject 中获取请求函数
        const injectGetRequest = inject<GetRequest>('getRequest');
        if (!injectGetRequest) {
            throw new Error('getRequest 没有提供，请在父组件中通过 provide 注入！');
        }

        return injectGetRequest(path, params, headers);
    },
    /**
     * 匹配字典项
     * @param dictType
     * @param dictValue
     */
    getDictLabel(dictType: string, dictValue: string) {
        let matchedDicts = this.getDictByType(dictType).filter(p => p.value == dictValue);
        return matchedDicts.length > 0 ? matchedDicts[0] : null;
    }

}
