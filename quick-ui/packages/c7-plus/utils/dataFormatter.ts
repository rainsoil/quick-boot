// 数据格式化工具函数

// 选项数据接口
export interface OptionItem {
  label: string
  value: any
  disabled?: boolean
  children?: OptionItem[]
  [key: string]: any
}

// 数据格式化配置
export interface FormatterConfig {
  labelKey?: string
  valueKey?: string
  disabledKey?: string
  childrenKey?: string
  customFormatter?: (item: any) => OptionItem
}

// 默认配置
const defaultConfig: FormatterConfig = {
  labelKey: 'label',
  valueKey: 'value',
  disabledKey: 'disabled',
  childrenKey: 'children'
}

/**
 * 格式化数组数据为选项格式
 * @param data 原始数据数组
 * @param config 格式化配置
 * @returns 格式化后的选项数组
 */
export const formatOptionsData = (
  data: any[],
  config: FormatterConfig = {}
): OptionItem[] => {
  if (!Array.isArray(data)) {
    console.warn('formatOptionsData: data must be an array')
    return []
  }

  const finalConfig = { ...defaultConfig, ...config }

  return data.map(item => {
    // 如果提供了自定义格式化函数，优先使用
    if (finalConfig.customFormatter) {
      return finalConfig.customFormatter(item)
    }

    // 标准格式化
    const option: OptionItem = {
      label: getNestedValue(item, finalConfig.labelKey!) || '',
      value: getNestedValue(item, finalConfig.valueKey!) || item
    }

    // 可选字段
    if (finalConfig.disabledKey && item[finalConfig.disabledKey] !== undefined) {
      option.disabled = Boolean(item[finalConfig.disabledKey])
    }

    // 递归处理子项
    if (finalConfig.childrenKey && Array.isArray(item[finalConfig.childrenKey])) {
      option.children = formatOptionsData(item[finalConfig.childrenKey], config)
    }

    // 保留原始数据的其他字段
    Object.keys(item).forEach(key => {
      if (![finalConfig.labelKey, finalConfig.valueKey, finalConfig.disabledKey, finalConfig.childrenKey].includes(key)) {
        option[key] = item[key]
      }
    })

    return option
  })
}

/**
 * 获取嵌套对象的值
 * @param obj 对象
 * @param path 路径，支持点分隔符
 * @returns 值
 */
export const getNestedValue = (obj: any, path: string): any => {
  if (!obj || !path) return undefined
  
  return path.split('.').reduce((current, key) => {
    return current && current[key] !== undefined ? current[key] : undefined
  }, obj)
}

/**
 * 设置嵌套对象的值
 * @param obj 对象
 * @param path 路径，支持点分隔符
 * @param value 值
 */
export const setNestedValue = (obj: any, path: string, value: any): void => {
  if (!obj || !path) return

  const keys = path.split('.')
  const lastKey = keys.pop()!
  
  const target = keys.reduce((current, key) => {
    if (!current[key] || typeof current[key] !== 'object') {
      current[key] = {}
    }
    return current[key]
  }, obj)

  target[lastKey] = value
}

/**
 * 格式化树形数据
 * @param data 原始数据
 * @param config 配置
 * @returns 格式化后的树形数据
 */
export const formatTreeData = (
  data: any[],
  config: FormatterConfig & {
    parentKey?: string
    rootValue?: any
  } = {}
): OptionItem[] => {
  const { parentKey = 'parentId', rootValue = null, ...formatterConfig } = config

  if (!Array.isArray(data)) return []

  // 构建父子关系映射
  const itemMap = new Map()
  const rootItems: any[] = []

  // 先创建所有节点的映射
  data.forEach(item => {
    itemMap.set(getNestedValue(item, formatterConfig.valueKey || 'value'), {
      ...item,
      children: []
    })
  })

  // 建立父子关系
  data.forEach(item => {
    const parentValue = getNestedValue(item, parentKey)
    const currentValue = getNestedValue(item, formatterConfig.valueKey || 'value')
    
    if (parentValue === rootValue || parentValue === null || parentValue === undefined) {
      rootItems.push(itemMap.get(currentValue))
    } else {
      const parent = itemMap.get(parentValue)
      if (parent) {
        parent.children.push(itemMap.get(currentValue))
      } else {
        // 如果找不到父节点，当作根节点处理
        rootItems.push(itemMap.get(currentValue))
      }
    }
  })

  return formatOptionsData(rootItems, formatterConfig)
}

/**
 * 扁平化树形数据
 * @param treeData 树形数据
 * @param childrenKey 子节点键名
 * @returns 扁平化后的数组
 */
export const flattenTreeData = (
  treeData: any[],
  childrenKey: string = 'children'
): any[] => {
  const result: any[] = []

  const flatten = (nodes: any[]) => {
    nodes.forEach(node => {
      const { [childrenKey]: children, ...nodeData } = node
      result.push(nodeData)
      
      if (Array.isArray(children) && children.length > 0) {
        flatten(children)
      }
    })
  }

  flatten(treeData)
  return result
}

/**
 * 根据值查找选项
 * @param options 选项数组
 * @param value 要查找的值
 * @param valueKey 值字段名
 * @param childrenKey 子节点字段名
 * @returns 找到的选项
 */
export const findOptionByValue = (
  options: OptionItem[],
  value: any,
  valueKey: string = 'value',
  childrenKey: string = 'children'
): OptionItem | null => {
  for (const option of options) {
    if (option[valueKey] === value) {
      return option
    }
    
    if (option[childrenKey] && Array.isArray(option[childrenKey])) {
      const found = findOptionByValue(option[childrenKey], value, valueKey, childrenKey)
      if (found) return found
    }
  }
  
  return null
}

/**
 * 获取选项路径
 * @param options 选项数组
 * @param value 目标值
 * @param valueKey 值字段名
 * @param childrenKey 子节点字段名
 * @returns 路径数组
 */
export const getOptionPath = (
  options: OptionItem[],
  value: any,
  valueKey: string = 'value',
  childrenKey: string = 'children'
): OptionItem[] => {
  const findPath = (nodes: OptionItem[], target: any, path: OptionItem[] = []): OptionItem[] | null => {
    for (const node of nodes) {
      const currentPath = [...path, node]
      
      if (node[valueKey] === target) {
        return currentPath
      }
      
      if (node[childrenKey] && Array.isArray(node[childrenKey])) {
        const result = findPath(node[childrenKey], target, currentPath)
        if (result) return result
      }
    }
    
    return null
  }

  return findPath(options, value) || []
}

/**
 * 数组去重
 * @param array 数组
 * @param key 去重依据的键名，如果不提供则按值去重
 * @returns 去重后的数组
 */
export const uniqueArray = <T>(array: T[], key?: keyof T): T[] => {
  if (!Array.isArray(array)) return []

  if (key) {
    const seen = new Set()
    return array.filter(item => {
      const value = item[key]
      if (seen.has(value)) return false
      seen.add(value)
      return true
    })
  } else {
    return Array.from(new Set(array))
  }
}

/**
 * 数组分组
 * @param array 数组
 * @param key 分组依据的键名或函数
 * @returns 分组后的对象
 */
export const groupBy = <T>(
  array: T[],
  key: keyof T | ((item: T) => string | number)
): Record<string, T[]> => {
  if (!Array.isArray(array)) return {}

  return array.reduce((groups, item) => {
    const groupKey = typeof key === 'function' ? key(item) : String(item[key])
    if (!groups[groupKey]) {
      groups[groupKey] = []
    }
    groups[groupKey].push(item)
    return groups
  }, {} as Record<string, T[]>)
}

/**
 * 深拷贝
 * @param obj 要拷贝的对象
 * @returns 拷贝后的对象
 */
export const deepClone = <T>(obj: T): T => {
  if (obj === null || typeof obj !== 'object') return obj
  if (obj instanceof Date) return new Date(obj.getTime()) as any
  if (obj instanceof Array) return obj.map(item => deepClone(item)) as any
  if (typeof obj === 'object') {
    const copy = {} as T
    Object.keys(obj).forEach(key => {
      copy[key as keyof T] = deepClone(obj[key as keyof T])
    })
    return copy
  }
  return obj
}

/**
 * 数据格式化 Hook
 */
export const useDataFormatter = (config: FormatterConfig = {}) => {
  const formatOptions = (data: any[]) => formatOptionsData(data, config)
  const formatTree = (data: any[], treeConfig = {}) => formatTreeData(data, { ...config, ...treeConfig })

  return {
    formatOptions,
    formatTree,
    flattenTreeData,
    findOptionByValue,
    getOptionPath,
    uniqueArray,
    groupBy,
    deepClone,
    getNestedValue,
    setNestedValue
  }
}