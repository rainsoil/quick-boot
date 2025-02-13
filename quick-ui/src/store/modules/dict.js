// stores/dict.js
import {ref} from 'vue';
import {getDicts} from '@/api/system/dict/data'
export const useDictStore = defineStore('dict', () => {
    const dictData = ref({});      // 存储字典数据：{ 字典类型: 字典项数组 }
    const promises = ref({});      // 存储进行中的请求，避免重复调用

    /**
     * 获取字典数据
     * @param {string} type 字典类型
     */
    const fetchDict = async (type) => {
        // 已有缓存数据，直接返回
        if (dictData.value[type]) return dictData.value[type];

        // 相同类型请求正在进行，等待其结果
        if (promises.value[type]) {
            return await promises.value[type];
        }

        try {
            // 发起新请求并记录 Promise
            const promise = getDicts(type);
            promises.value[type] = promise;

            const data = await promise;
            dictData.value[type] = data.data; // 存入缓存
            return data.data;
        } catch (error) {
            throw error; // 错误处理可按需扩展
        } finally {
            delete promises.value[type]; // 清理 Promise 记录
        }
    };

    return { dictData, fetchDict };
});
export default useDictStore;
