// 工具类
// useFormSubmit.ts
import {Ref} from 'vue'

// 简易深度取值函数：支持 'a.b.c' 格式
export function jsonGet(obj, path, defaultValue = undefined) {
    if (!obj || typeof obj !== 'object') return defaultValue
    return path.split('.').reduce((res, seg) => (res == null ? undefined : res[seg]), obj) ?? defaultValue
}


