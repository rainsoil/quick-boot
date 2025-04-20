// composables/useDict.ts
import {ref, Ref} from 'vue'
import {IDict, IDictHook, IObject} from "../types/ITypes";
import {getConfig} from "../config/C7Config";
import {injectService} from "../service/injectService";

const promises: Record<string, Promise<any>> = {}

// 本地存储统一 key
const STORAGE_KEY = 'dicts_cache'
// 缓存 TTL: 24 小时
const CACHE_TTL = 24 * 60 * 60 * 1000 // ms

interface CacheWrapper {
    timestamp: number
    data: IDict[]
}
interface CacheMap {
    [type: string]: CacheWrapper
}

function loadCache(): CacheMap {
    try {
        const raw = localStorage.getItem(STORAGE_KEY)
        return raw ? (JSON.parse(raw) as CacheMap) : {}
    } catch {
        return {}
    }
}

function saveCache(cache: CacheMap): void {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(cache))
}

function getStoredDict(type: string): IDict[] | null {
    const cache = loadCache()
    const wrap = cache[type]
    if (!wrap) return null
    if (Date.now() - wrap.timestamp > CACHE_TTL) {
        delete cache[type]
        saveCache(cache)
        return null
    }
    return wrap.data
}

function setStoredDict(type: string, data: IDict[]): void {
    const cache = loadCache()
    cache[type] = { timestamp: Date.now(), data }
    saveCache(cache)
}

function removeStoredDict(type: string): void {
    const cache = loadCache()
    if (cache[type]) {
        delete cache[type]
        saveCache(cache)
    }
}

interface UseDictResult {
    dicts: Ref<Record<string, IDict[]>>
    loading: Ref<boolean>
    refresh: (...types: string[]) => void
}

/**
 * useDict Hook
 * 支持：多类型、统一 localStorage 缓存（24h TTL）、并发控制、手动刷新
 * 同时提供：getDict、getDictByValue、getDict2
 */
export function useDict(...types: string[]): IDictHook & UseDictResult {
    const dicts = ref<Record<string, IDict[]>>({})
    const loading = ref(false)

    const loadOne = (type: string) => {
        dicts.value[type] = []

        const cached = getStoredDict(type)
        if (cached) {
            dicts.value[type] = cached
            return
        }

        const config = getConfig()
        if (!config.dictUrl) {
            throw new Error('请配置字典接口地址')
        }

        if (!promises[type]) {
            promises[type] = injectService.getRequest(`${config.dictUrl}/${type}`)
        }

        promises[type]
            .then(resp => {
                const data = resp.data.map((p: any) => ({
                    label: p.label,
                    value: p.value,
                    elTagType: p.listClass,
                    elTagClass: p.cssClass,
                }))
                dicts.value[type] = data
                setStoredDict(type, data)
            })
            .catch(err => {
                console.error(`加载字典 ${type} 失败:`, err)
            })
            .finally(() => {
                delete promises[type]
            })
    }

    // 初始加载
    loading.value = true
    types.forEach(loadOne)
    setTimeout(() => { loading.value = false }, 200)

    // 手动刷新
    const refresh = (...target: string[]) => {
        const list = target.length ? target : types
        list.forEach(type => {
            removeStoredDict(type)
            loadOne(type)
        })
    }

    // API 方法
    const getDict = (...dictTypes: string[]) => useDict(...dictTypes)

    const getDictByValue = (dictType: string, value: string) => {
        const hook = useDict(dictType)
        return hook.dicts.value[dictType]?.find(item => item.value === value)?.label
    }

    const getDict2 = (
        dictList: IObject,
        dictType: string,
        dictUrl: string,
        param: IObject,
        loadingFlag: boolean
    ): Promise<any> => {
        return new Promise((resolve, reject) => {
            if (dictUrl) {
                loadingFlag = true
                injectService.postRequest(dictUrl, param, {})
                    .then(res => resolve(res.data))
                    .catch(err => reject(err))
                    .finally(() => { loadingFlag = false })
            } else if (dictType) {
                const hook = useDict(dictType)
                const list = hook.dicts.value[dictType] || []
                resolve(list)
            } else {
                resolve(dictList)
            }
        })
    }

    return { dicts, loading, refresh, getDict, getDictByValue, getDict2 }
}
