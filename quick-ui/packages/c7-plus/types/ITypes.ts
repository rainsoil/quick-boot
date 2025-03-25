import {Ref} from "vue";

export interface IObject<T = any> {
    [key: string]: T;
}


/**
 * 字典
 */
export interface IDict {

    /**
     * 字典的标签值
     */
    value: string;


    /**
     * 字典的标签名
     */
    label: string;
}


export interface IDictHook {

    /**
     * 根据指定的字典类型获取字典项列表。
     *
     * @param dictType - 字典类型，用于指定需要获取的字典项类别。
     * @returns 返回一个包含字典项的数组，数组中的每个元素都符合 `IDict` 接口的定义。
     */
    getDict(dictType: string): Ref<IDict[]>;


    /**
     * 根据字典类型和值获取对应的字典项
     *
     * @param dictType - 字典类型，用于指定要查询的字典类别
     * @param value - 字典值，用于在指定字典类型中查找对应的字典项
     * @returns 返回一个符合 IDict 接口的字典项，包含字典的详细信息
     */
    getDictByValue(dictType: string, value: string): string;


    /**
     * 根据多个字典类型获取多个字典项列表。
     *
     * @param dictTypes - 字典类型数组，用于指定需要获取的字典项类别。
     */
    getDicts(dictTypes: string[]): Ref<Record<string, Ref<IDict[]>>>;
}

export interface IC7Config {
    dictUrl?: string
}
