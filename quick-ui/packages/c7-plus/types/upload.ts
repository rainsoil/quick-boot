// c7-upload 组件类型定义
import type { FileInfo, ComponentSize } from './global'

// 上传 Props 接口
export interface UploadProps {
  // 基础属性
  modelValue?: string | string[]
  uploadUrl: string
  deleteUrl?: string
  headers?: Record<string, any>
  
  // 文件限制
  fileSize?: number // MB
  fileType?: string // 逗号分隔的扩展名
  limit?: number
  
  // 显示配置
  listType?: 'text' | 'picture' | 'picture-card'
  showTip?: boolean
  tipText?: string
  
  // 其他配置
  disabled?: boolean
  multiple?: boolean
  autoUpload?: boolean
}

// 上传事件接口
export interface UploadEmits {
  'update:modelValue': (value: string) => void
  success: (response: any, file: File, fileList: FileInfo[]) => void
  error: (error: any, file: File, fileList: FileInfo[]) => void
  progress: (event: ProgressEvent, file: File, fileList: FileInfo[]) => void
  remove: (file: File, fileList: FileInfo[]) => void
  preview: (file: File) => void
}

// 上传文件接口
export interface UploadFile extends FileInfo {
  fileId?: string | number
  raw?: File
  percentage?: number
}

// 上传暴露的方法
export interface UploadExposed {
  clearFiles: () => void
  abort: (file?: File) => void
  submit: () => void
}