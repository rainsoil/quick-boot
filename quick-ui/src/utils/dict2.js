// composables/useDict.js
import useDictStore2 from '@/store/modules/dict2'
import {computed, onServerPrefetch, onMounted} from 'vue';

export function useDict2() {
    const store = useDictStore2();
    const pendingRequests = new Set (); // 本地记录已触发请求


    /**
     * 安全获取字典项（自动去重）
     * @param type 字典类型
     */
    const getDict = (type) => {
        // 如果无缓存且未在请求中，触发请求
        if (!store.dictData[type] && !pendingRequests.has(type)) {
            pendingRequests.add(type);
            store.fetchDict(type).finally(() => {
                pendingRequests.delete(type);
            });
        }

        // 返回响应式数据（自动更新）
        return computed(() => store.dictData[type] || []);
    };

    // SSR 支持：在服务端预取数据
    onServerPrefetch(async () => {
        if (!store.dictData[type]) {
            await store.fetchDict(type);
        }
    });

    const getDictLabel = (type, value) => {
        const dict = getDict(type).value;
        console.log(dict)
        if (!dict) return value;

        const item = dict.find(item => item.dictValue === value);
        return item ? item.dictLabel : value;
    };

    return {getDict, getDictLabel};
}
