import {IObject, IDictHooksOptions, IDictHooks, IViewHooksOptions} from "./interface";
import injectService from '../service/injectService'
import {Ref, ref} from "vue";

const dictHook = () => {


    const viewFns = {


        /**
         * 获取字典值
         */
        getDict(dictList: IObject, dictType: string, dictUrl: string, param: IObject, loading: boolean): Promise<any> {
            return new Promise((resolve, reject) => {
                if (dictUrl) {
                    loading = true;
                    try {
                        injectService.postRequest(dictUrl, param, {}).then(res => {
                            let data = res.data;
                            resolve(data);
                        });

                    } catch (error) {
                        console.error('请求失败:', error);
                    } finally {
                        loading = false;
                    }
                } else if (dictType) {
                    let dict = injectService.getDictByType(dictType) || [];
                    resolve(dict);
                } else {
                    resolve(dictList);
                }
            });

        },

    }

    return {
        ...viewFns,

    };

}

export default dictHook;
