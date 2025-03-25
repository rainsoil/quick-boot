// stores/dict.ts
import {defineStore} from 'pinia';
import {ref} from 'vue';
import {IDict} from "../types/ITypes";
import {getConfig} from "../config/C7Config";
import {injectService} from "../service/injectService";

export const useDictStore = defineStore('dictStore', () => {
    // 定义字典数据缓存（类型明确）
    const dictData = ref<Record<string, IDict[]>>({});

    // 定义进行中的请求记录（Promise 缓存）
    const promises = ref<Record<string, Promise>>({});

    let C7Config = getConfig();
    if (!C7Config.dictUrl) {
        throw new Error('请配置字典接口地址')
    }

    /**
     * 获取字典数据
     * @param type 字典类型
     */
    const fetchDict = async (type: string): Promise<IDict[]> => {
        // 已有缓存数据，直接返回
        if (dictData.value[type]) {
            return dictData.value[type];
        }

        // 检查是否有进行中的请求
        if (promises.value[type]) {
            return await promises.value[type].then(res => res.data);
        }

        try {
            // 创建新请求并记录 Promise
            const promise = injectService.getRequest(C7Config.dictUrl + "/" + type);
            promises.value[type] = promise;

            const response = await promise;
            if (response.code === 200) {
                // 存入缓存
                dictData.value[type] = response.data;
                return response.data;
            } else {
                throw new Error(response.msg || '获取字典数据失败');
            }
        } catch (error) {
            throw error;
        } finally {
            // 清理 Promise 记录
            delete promises.value[type];
        }
    };

    return {dictData, fetchDict};
});

export type {IDict};
export default useDictStore;
