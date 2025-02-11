// stores/dict.js
import {ref} from 'vue';
import {getDicts} from '@/api/system/dict/data'

export const useDictStore2 = defineStore('dict2', () => {
    const dictData = ref({});
    const promises = ref({});

    /**
     * 获取字典数据
     * @param {string} type 字典类型
     */
    const fetchDict = async (type) => {
        console.log("type2", type)
        console.log(dictData.value, dictData.value[type])
        // 已有缓存数据，直接返回
        if (dictData.value[type]) {
            return Promise.resolve(dictData.value[type]);
        }
        // 已有进行中的请求，复用该 Promise
        if (promises.value[type]) {
            return promises.value[type];
        }

// 创建新请求，并记录 Promise
        const promise = getDicts(type)
            .then((res) => {
                dictData.value[type] = res.data; // 存入缓存
                return res.data;
            })
            .finally(() => {
                delete promises.value[type]; // 清理记录
            });

        promises.value[type] = promise;
        return promise;
    };

    return {dictData, fetchDict};
});

export default useDictStore2;
