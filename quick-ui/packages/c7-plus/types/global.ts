// 全局类型定义

// 基础类型
export type Primitive = string | number | boolean | null | undefined
export type DeepPartial<T> = {
  [P in keyof T]?: T[P] extends object ? DeepPartial<T[P]> : T[P]
}

// 组件大小
export type ComponentSize = 'large' | 'default' | 'small'

// 组件状态
export type ComponentStatus = 'success' | 'warning' | 'info' | 'danger' | ''

// 按钮类型
export type ButtonType = 'primary' | 'success' | 'warning' | 'danger' | 'info' | 'text' | 'default'

// 按钮预设类型
export type ButtonPresetType = 'add' | 'edit' | 'delete' | 'query' | 'refresh' | 'upload' | 'download' | 'submit' | 'cancel'

// 表单验证规则
export interface FormRule {
  required?: boolean
  message?: string
  trigger?: string | string[]
  min?: number
  max?: number
  pattern?: RegExp
  validator?: (rule: any, value: any, callback: (error?: Error) => void) => void
}

// 选项接口
export interface Option {
  label: string
  value: any
  disabled?: boolean
  children?: Option[]
  [key: string]: any
}

// 树形数据接口
export interface TreeData {
  id: any
  label: string
  children?: TreeData[]
  disabled?: boolean
  [key: string]: any
}

// 表格列配置
export interface TableColumn {
  prop: string
  label: string
  width?: string | number
  minWidth?: string | number
  fixed?: boolean | 'left' | 'right'
  sortable?: boolean | 'custom'
  align?: 'left' | 'center' | 'right'
  headerAlign?: 'left' | 'center' | 'right'
  showOverflowTooltip?: boolean
  formatter?: (row: any, column: any, cellValue: any, index: number) => any
  [key: string]: any
}

// 分页配置
export interface PaginationConfig {
  currentPage: number
  pageSize: number
  total: number
  pageSizes?: number[]
  layout?: string
  background?: boolean
  small?: boolean
}

// API 响应接口
export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
  success?: boolean
  timestamp?: number
}

// 请求参数接口
export interface RequestParams {
  [key: string]: any
}

// 文件信息接口
export interface FileInfo {
  name: string
  url: string
  size?: number
  type?: string
  uid?: string
  status?: 'ready' | 'uploading' | 'success' | 'fail'
  [key: string]: any
}

// 上传配置接口
export interface UploadConfig {
  action: string
  headers?: Record<string, string>
  data?: Record<string, any>
  name?: string
  withCredentials?: boolean
  showFileList?: boolean
  drag?: boolean
  accept?: string
  multiple?: boolean
  limit?: number
  autoUpload?: boolean
  listType?: 'text' | 'picture' | 'picture-card'
  beforeUpload?: (file: File) => boolean | Promise<boolean>
  onSuccess?: (response: any, file: File, fileList: FileInfo[]) => void
  onError?: (error: any, file: File, fileList: FileInfo[]) => void
  onProgress?: (event: ProgressEvent, file: File, fileList: FileInfo[]) => void
  onRemove?: (file: File, fileList: FileInfo[]) => boolean | Promise<boolean>
  onPreview?: (file: File) => void
}

// 日期时间类型
export type DateType = 'year' | 'years' | 'month' | 'months' | 'date' | 'dates' | 'datetime' | 'week' | 'datetimerange' | 'daterange' | 'monthrange' | 'yearrange'

// 主题类型
export type ThemeType = 'default' | 'primary' | 'success' | 'warning' | 'danger'

// 事件处理器类型
export type EventHandler<T = any> = (value: T) => void
export type ChangeEventHandler<T = any> = (value: T, oldValue?: T) => void

// 组件 Props 基础接口
export interface BaseComponentProps {
  size?: ComponentSize
  disabled?: boolean
  readonly?: boolean
}

// 表单组件 Props 基础接口
export interface BaseFormComponentProps extends BaseComponentProps {
  modelValue?: any
  placeholder?: string
  clearable?: boolean
  validateEvent?: boolean
}

// 异步数据获取函数类型
export type FetchDataFunction = (params?: RequestParams) => Promise<any>

// 数据格式化函数类型
export type DataFormatterFunction<T = any, R = any> = (data: T) => R

// 验证函数类型
export type ValidatorFunction = (value: any) => boolean | string | Promise<boolean | string>

// 组件暴露的方法接口
export interface ComponentExposed {
  focus?: () => void
  blur?: () => void
  clear?: () => void
  validate?: () => Promise<boolean>
  resetValidation?: () => void
  [key: string]: any
}