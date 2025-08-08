// 文件验证工具函数
import { ElMessage } from 'element-plus'

// 文件类型配置
export interface FileTypeConfig {
  extensions: string[]
  mimeTypes?: string[]
  description: string
}

// 验证规则接口
export interface ValidationRule {
  maxSize?: number // MB
  minSize?: number // MB
  allowedTypes?: string[] | FileTypeConfig[]
  maxCount?: number
  customValidator?: (file: File) => boolean | string
}

// 验证结果接口
export interface ValidationResult {
  valid: boolean
  errors: string[]
  warnings: string[]
}

// 常见文件类型配置
export const FILE_TYPES = {
  IMAGE: {
    extensions: ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp'],
    mimeTypes: ['image/jpeg', 'image/png', 'image/gif', 'image/bmp', 'image/webp'],
    description: '图片文件'
  },
  DOCUMENT: {
    extensions: ['pdf', 'doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'txt'],
    mimeTypes: [
      'application/pdf',
      'application/msword',
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
      'application/vnd.ms-excel',
      'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      'application/vnd.ms-powerpoint',
      'application/vnd.openxmlformats-officedocument.presentationml.presentation',
      'text/plain'
    ],
    description: '文档文件'
  },
  VIDEO: {
    extensions: ['mp4', 'avi', 'mov', 'wmv', 'flv', 'webm'],
    mimeTypes: ['video/mp4', 'video/avi', 'video/quicktime', 'video/x-ms-wmv', 'video/x-flv', 'video/webm'],
    description: '视频文件'
  },
  AUDIO: {
    extensions: ['mp3', 'wav', 'flac', 'aac', 'ogg'],
    mimeTypes: ['audio/mpeg', 'audio/wav', 'audio/flac', 'audio/aac', 'audio/ogg'],
    description: '音频文件'
  },
  ARCHIVE: {
    extensions: ['zip', 'rar', '7z', 'tar', 'gz'],
    mimeTypes: [
      'application/zip',
      'application/x-rar-compressed',
      'application/x-7z-compressed',
      'application/x-tar',
      'application/gzip'
    ],
    description: '压缩文件'
  }
} as const

// 文件大小格式化
export const formatFileSize = (bytes: number): string => {
  if (bytes === 0) return '0 B'
  
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB', 'TB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i]
}

// 获取文件扩展名
export const getFileExtension = (filename: string): string => {
  return filename.split('.').pop()?.toLowerCase() || ''
}

// 检查文件类型
export const checkFileType = (file: File, allowedTypes: string[] | FileTypeConfig[]): boolean => {
  if (!allowedTypes || allowedTypes.length === 0) return true
  
  const fileExtension = getFileExtension(file.name)
  const fileMimeType = file.type.toLowerCase()
  
  return allowedTypes.some(type => {
    if (typeof type === 'string') {
      return type.toLowerCase() === fileExtension
    } else {
      // FileTypeConfig 类型
      const extensionMatch = type.extensions.some(ext => ext.toLowerCase() === fileExtension)
      const mimeTypeMatch = type.mimeTypes ? type.mimeTypes.some(mime => mime.toLowerCase() === fileMimeType) : false
      return extensionMatch || mimeTypeMatch
    }
  })
}

// 检查文件大小
export const checkFileSize = (file: File, maxSize?: number, minSize?: number): boolean => {
  const fileSizeInMB = file.size / 1024 / 1024
  
  if (maxSize && fileSizeInMB > maxSize) return false
  if (minSize && fileSizeInMB < minSize) return false
  
  return true
}

// 验证单个文件
export const validateFile = (file: File, rules: ValidationRule): ValidationResult => {
  const result: ValidationResult = {
    valid: true,
    errors: [],
    warnings: []
  }
  
  // 文件大小验证
  if (rules.maxSize || rules.minSize) {
    const fileSizeInMB = file.size / 1024 / 1024
    
    if (rules.maxSize && fileSizeInMB > rules.maxSize) {
      result.valid = false
      result.errors.push(`文件大小不能超过 ${rules.maxSize}MB，当前文件大小为 ${formatFileSize(file.size)}`)
    }
    
    if (rules.minSize && fileSizeInMB < rules.minSize) {
      result.valid = false
      result.errors.push(`文件大小不能小于 ${rules.minSize}MB，当前文件大小为 ${formatFileSize(file.size)}`)
    }
  }
  
  // 文件类型验证
  if (rules.allowedTypes && rules.allowedTypes.length > 0) {
    if (!checkFileType(file, rules.allowedTypes)) {
      result.valid = false
      const typeDescriptions = rules.allowedTypes.map(type => {
        if (typeof type === 'string') {
          return type
        } else {
          return type.extensions.join(', ')
        }
      }).join(', ')
      result.errors.push(`不支持的文件类型，支持的类型：${typeDescriptions}`)
    }
  }
  
  // 自定义验证
  if (rules.customValidator) {
    const customResult = rules.customValidator(file)
    if (customResult !== true) {
      result.valid = false
      result.errors.push(typeof customResult === 'string' ? customResult : '自定义验证失败')
    }
  }
  
  return result
}

// 验证文件列表
export const validateFileList = (files: File[], rules: ValidationRule): ValidationResult => {
  const result: ValidationResult = {
    valid: true,
    errors: [],
    warnings: []
  }
  
  // 文件数量验证
  if (rules.maxCount && files.length > rules.maxCount) {
    result.valid = false
    result.errors.push(`文件数量不能超过 ${rules.maxCount} 个，当前选择了 ${files.length} 个文件`)
    return result
  }
  
  // 逐个验证文件
  files.forEach((file, index) => {
    const fileResult = validateFile(file, rules)
    if (!fileResult.valid) {
      result.valid = false
      fileResult.errors.forEach(error => {
        result.errors.push(`文件 "${file.name}": ${error}`)
      })
    }
    fileResult.warnings.forEach(warning => {
      result.warnings.push(`文件 "${file.name}": ${warning}`)
    })
  })
  
  return result
}

// 创建文件验证器
export const createFileValidator = (rules: ValidationRule, showErrors = true) => {
  return {
    validate: (file: File | File[]): boolean => {
      const files = Array.isArray(file) ? file : [file]
      const result = validateFileList(files, rules)
      
      if (showErrors && !result.valid) {
        result.errors.forEach(error => ElMessage.error(error))
      }
      
      if (showErrors && result.warnings.length > 0) {
        result.warnings.forEach(warning => ElMessage.warning(warning))
      }
      
      return result.valid
    },
    
    validateSilent: (file: File | File[]): ValidationResult => {
      const files = Array.isArray(file) ? file : [file]
      return validateFileList(files, rules)
    }
  }
}

// 预设验证器
export const imageValidator = createFileValidator({
  allowedTypes: [FILE_TYPES.IMAGE],
  maxSize: 10
})

export const documentValidator = createFileValidator({
  allowedTypes: [FILE_TYPES.DOCUMENT],
  maxSize: 50
})

export const videoValidator = createFileValidator({
  allowedTypes: [FILE_TYPES.VIDEO],
  maxSize: 500
})

// 通用文件验证 Hook
export const useFileValidator = (rules: ValidationRule) => {
  const validator = createFileValidator(rules)
  
  return {
    validate: validator.validate,
    validateSilent: validator.validateSilent,
    rules,
    formatFileSize,
    getFileExtension,
    FILE_TYPES
  }
}