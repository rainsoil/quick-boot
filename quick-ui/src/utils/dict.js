// composables/useDict.js
import useDictStore from '@/store/modules/dict'
import {computed, onServerPrefetch, onMounted} from 'vue';

export function useDict() {
    const store = useDictStore();

    /**
     * 获取字典项（自动触发请求）
     * @param {string} type 字典类型
     * @returns {import('vue').ComputedRef<Array>} 字典项数组（响应式）
     */
    const getDict = (type) => {
        // 触发请求（若尚未缓存）
        store.fetchDict(type);

        // 返回响应式字典数据，初始为空数组
        return computed(() => store.dictData[type] || []);
    };

    const getDictLabel = (type, value) => {
        const dict = getDict(type).value;
        if (!dict) return value;

        const item = dict.find(item => item.dictValue === value);
        return item ? item.dictLabel : value;
    };

    return {getDict, getDictLabel};
}
