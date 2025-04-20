// composables/useDict.ts
import {ref, Ref} from 'vue'
import {IDict, IDictHook, IObject} from "../types/ITypes";
import {getConfig} from "../config/C7Config";
import {injectService} from "../service/injectService";

const promises: Record<string, Promise<any>> = {}

function getStoredDict(type: string): IDict[] | null {
    const stored = localStorage.getItem(`dict_${type}`)
    if (stored) {
        try {
            return JSON.parse(stored) as IDict[]
        } catch (e) {
            console.error(`解析 localStorage 中的字典数据失败 (${type})`, e)
            return null
        }
    }
    return null
}

function setStoredDict(type: string, data: IDict[]): void {
    localStorage.setItem(`dict_${type}`, JSON.stringify(data))
}

/**
 * 使用字典数据 Hook
 * @param types 多个字典类型
 */
export function useDict(...types: string[]): Ref<Record<string, IDict[]>> {
    const dicts = ref<Record<string, IDict[]>>({})

    types.forEach(type => {
        dicts.value[type] = []

        const cached = getStoredDict(type)
        if (cached) {
            dicts.value[type] = cached
        } else {
            const config = getConfig()
            if (!config.dictUrl) {
                throw new Error('请配置字典接口地址')
            }

            // 防止并发请求
            if (!promises[type]) {
                promises[type] = injectService.getRequest(`${config.dictUrl}/${type}`)
            }

            promises[type].then(resp => {
                const data = resp.data.map((p: any) => ({
                    label: p.label,
                    value: p.value,
                    elTagType: p.listClass,
                    elTagClass: p.cssClass,
                }))
                dicts.value[type] = data
                setStoredDict(type, data)
            }).catch(err => {
                console.error(`获取字典 ${type} 失败`, err)
            }).finally(() => {
                delete promises[type]
            })
        }
    })

    return dicts
}

