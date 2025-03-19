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
