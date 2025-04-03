// stores/dict.ts
import { IDict } from "../types/ITypes";
import { getConfig } from "../config/C7Config";
import { injectService } from "../service/injectService";

// 用于缓存进行中的请求记录（Promise 缓存，仅保存在内存中）
const promises: Record<string, Promise<any>> = {};

/**
 * 从 localStorage 获取缓存的字典数据
 * @param type 字典类型
 */
function getStoredDict(type: string): IDict[] | null {
    const stored = localStorage.getItem(`dict_${type}`);
    if (stored) {
        try {
            return JSON.parse(stored) as IDict[];
        } catch (e) {
            console.error("解析 localStorage 中的字典数据失败", e);
            return null;
        }
    }
    return null;
}

/**
 * 将字典数据存入 localStorage
 * @param type 字典类型
 * @param data 字典数据
 */
function setStoredDict(type: string, data: IDict[]): void {
    localStorage.setItem(`dict_${type}`, JSON.stringify(data));
}

/**
 * 获取字典数据
 * @param type 字典类型
 */
export async function fetchDict(type: string): Promise<IDict[]> {
    // 先尝试从 localStorage 读取缓存数据
    const cachedData = getStoredDict(type);
    if (cachedData) {
        return cachedData;
    }

    // 检查是否已有进行中的请求
    if (promises[type]) {
        return await promises[type].then((res) => res.data);
    }

    const config = getConfig();
    if (!config.dictUrl) {
        throw new Error("请配置字典接口地址");
    }

    try {
        // 创建新请求并记录 Promise
        const promise = injectService.getRequest(`${config.dictUrl}/${type}`);
        promises[type] = promise;

        const response = await promise;
        if (response.code === 200) {
            // 缓存到 localStorage
            setStoredDict(type, response.data);
            return response.data;
        } else {
            throw new Error(response.msg || "获取字典数据失败");
        }
    } catch (error) {
        throw error;
    } finally {
        // 清理 Promise 记录
        delete promises[type];
    }
}

export type { IDict };
export default { fetchDict ,getStoredDict};
