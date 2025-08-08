// c7-select 组件类型定义
import type { Option, ComponentSize, FetchDataFunction, DataFormatterFunction, RequestParams } from './global'

// 选择器 Props 接口
export interface SelectProps {
  // 基础属性
  modelValue?: any
  multiple?: boolean
  disabled?: boolean
  clearable?: boolean
  placeholder?: string
  size?: ComponentSize
  
  // 数据配置
  dataList?: Option[]
  fetchData?: FetchDataFunction
  fetchParams?: RequestParams
  dataFormatter?: DataFormatterFunction
  
  // 字段映射
  labelKey?: string
  valueKey?: string
  resultKey?: string
  
  // 功能配置
  remote?: boolean
  filterable?: boolean
  allowCreate?: boolean
  tag?: boolean
  separator?: boolean
  autoLoad?: boolean
  
  // 分组配置
  group?: boolean
  
  // 其他配置
  loading?: boolean
  noDataText?: string
  noMatchText?: string
  loadingText?: string
  popperClass?: string
  reserveKeyword?: boolean
  defaultFirstOption?: boolean
  automaticDropdown?: boolean
}

// 选择器事件接口
export interface SelectEmits {
  'update:modelValue': (value: any) => void
  change: (value: any) => void
  'visible-change': (visible: boolean) => void
  'remove-tag': (tag: any) => void
  clear: () => void
  blur: (event: FocusEvent) => void
  focus: (event: FocusEvent) => void
}

// 选择器暴露的方法
export interface SelectExposed {
  focus: () => void
  blur: () => void
  clear: () => void
  refresh: () => void
  getOptions: () => Option[]
}