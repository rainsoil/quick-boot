import useDictStore from '@/store/modules/dict'
import {getDicts} from '@/api/system/dict/data'

/**
 * 获取字典数据
 */
export function useDict(...args) {
    const res = ref({});
    return (() => {
        args.forEach((dictType, index) => {
            res.value[dictType] = [];
            const dicts = useDictStore().getDict(dictType);
            if (dicts) {
                res.value[dictType] = dicts;
            } else {
                getDicts(dictType).then(resp => {
                    res.value[dictType] = resp.data.map(p => ({
                        label: p.dictLabel,
                        value: p.dictValue,
                        elTagType: p.listClass,
                        elTagClass: p.cssClass
                    }))
                    useDictStore().setDict(dictType, res.value[dictType]);
                })
            }
        })
        return toRefs(res.value);
    })()
}

export function getDictList(dictType) {


    const dicts = useDictStore().getDict(dictType);

    if (dicts) {
        return dicts;
    } else {
        getDicts(dictType).then(resp => {
            console.log(resp)
            let rs = resp.data.map(p => ({
                label: p.dictLabel,
                value: p.dictValue,
                elTagType: p.listClass,
                elTagClass: p.cssClass
            }))
            console.log(rs)
            return rs;
            // console.log(dictType,res.value)
            // useDictStore().setDict(dictType, res.value);
        })
    }

}

export function getDictLabel(dictType, dictValue) {
    let mathDicts = getDictList(dictType).filter(p => p.value === dictValue);
    if (null != mathDicts && mathDicts.length > 0) {
        return mathDicts[0];
    } else {
        return null;
    }
}
