import {IObject, IDictHooksOptions, IDictHooks, IViewHooksOptions} from "./interface";
import injectService from '../service/injectService'

const dictHook = () => {


    const viewFns = {


        /**
         * 获取字典值
         */
        getDict(dictList: IObject, dictType: string, dictUrl: string, param: IObject, loading: boolean): Promise<any> {
            return new Promise((resolve, reject) => {
                console.log(dictList, dictType, dictUrl, param, loading)
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
                    console.log('dictType', dictType)
                    let dict = injectService.getDictByType(dictType) || [];
                    resolve(dict);
                } else {
                    resolve(dictList);
                }
            });

            // console.log(state.dictList,state.dictUrl,state.dictType)
            // if (state.dictType) {
            //     viewFns.getDictByDictType(state.dictType)
            // } else if (state.dictUrl) {
            //     viewFns.getDictListFromApi(state.dictUrl, param)
            // }
            // console.log(state.dictList)
        }
    }

    return {
        ...viewFns,

    };

}

export default dictHook;
