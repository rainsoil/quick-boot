import {getConfig} from "../config/C7Config";
import {IDict, IDictHook} from "../types/ITypes";
import useDictStore from './dictStore'
import {computed, Ref, ref} from "vue";

/**
 * 字典值封装
 */
export const dictHook = (): IDictHook => {
    const store = useDictStore();

    return {
        getDictByValue(dictType: string, value: string): string {

            const dict = this.getDict(dictType).value;
            if (!dict) return value;

            const item = dict.find(item => item.value === value);
            return item ? item.label : value;
        },
        getDicts: (dictTypes: string[]) => {
            const dictData: Record<string, Ref<IDict[]>> = {};

            dictTypes.forEach(dictType => {
                // 触发请求（若尚未缓存）
                store.fetchDict(dictType);

                // 返回响应式字典数据，初始为空数组
                dictData[dictType] = computed(() => store.dictData[dictType] || []);
                // dictData[dictType] = dicts.value;

            });
            return ref(dictData);
        },
        getDict: (dictType: string) => {
            // 触发请求（若尚未缓存）
            store.fetchDict(dictType);

            // 返回响应式字典数据，初始为空数组
            return computed(() => store.dictData[dictType] || []);
        }
    }
}
