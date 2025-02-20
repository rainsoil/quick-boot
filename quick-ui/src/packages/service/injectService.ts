import { inject } from 'vue';

// 请求的类型
type GetRequest = (path: string, params?: Object, headers?: Record<string, string>) => Promise<any>;

export default {
    setup() {
        // 使用 inject 获取需要的服务路径和请求函数
        const servicePath = '../../../docs/.vitepress/config/provideSerivce.js';

        const servicePath2 = inject<string>('servicePath2');
        if (!servicePath2) {
            throw new Error('servicePath2 没有提供，请在父组件中通过 provide 注入！');
        }

        // 定义请求函数
        const getDictByType = async (dictType: string): Promise<any[]> => {
            // 使用动态导入加载字典服务
            const mod = await import(servicePath);
            if (!mod) {
                throw new Error('getDictByType 没有提供，请在父组件中通过 provide 注入！');
            }
            const result = mod.getDictByType(dictType);
            return Array.isArray(result) ? result : [];
        };

        const getRequest = async (path: string, params?: Object, headers?: Record<string, string>): Promise<any> => {
            const mod = await import(/* @vite-ignore */ servicePath2);
            return mod != null ? mod.getRequest(path, params, headers) :
                Promise.reject('getRequest 没有提供，请在' + servicePath2 + '中加入！');
        };

        const deleteRequest = async (path: string, params?: Object, headers?: Record<string, string>): Promise<any> => {
            const injectDeleteRequest = inject<GetRequest>('deleteRequest');
            if (!injectDeleteRequest) {
                throw new Error('deleteRequest 没有提供，请在父组件中通过 provide 注入！');
            }
            return injectDeleteRequest(path, params, headers);
        };

        const getDictLabel = (dictType: string, dictValue: string) => {
            let matchedDicts = getDictByType(dictType).filter(p => p.dictValue == dictValue);
            return matchedDicts.length > 0 ? matchedDicts[0] : null;
        };

        return {
            getDictByType,
            getRequest,
            deleteRequest,
            getDictLabel
        };
    }
};
