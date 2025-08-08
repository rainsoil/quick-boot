// 错误处理工具函数
import { ElMessage, ElNotification } from 'element-plus'

// 错误类型枚举
export enum ErrorType {
  NETWORK = 'network',
  VALIDATION = 'validation',
  BUSINESS = 'business',
  SYSTEM = 'system',
  UNKNOWN = 'unknown'
}

// 错误级别枚举
export enum ErrorLevel {
  INFO = 'info',
  WARNING = 'warning',
  ERROR = 'error',
  CRITICAL = 'critical'
}

// 错误信息接口
export interface ErrorInfo {
  type: ErrorType
  level: ErrorLevel
  message: string
  code?: string | number
  context?: string
  details?: any
  timestamp?: number
}

// 错误处理配置
export interface ErrorHandlerConfig {
  showMessage?: boolean
  showNotification?: boolean
  logToConsole?: boolean
  reportToServer?: boolean
  customHandler?: (error: ErrorInfo) => void
}

// 默认配置
const defaultConfig: ErrorHandlerConfig = {
  showMessage: true,
  showNotification: false,
  logToConsole: true,
  reportToServer: false
}

// 创建错误处理器
export const createErrorHandler = (config: ErrorHandlerConfig = {}) => {
  const finalConfig = { ...defaultConfig, ...config }

  const handleError = (
    error: any,
    context?: string,
    type: ErrorType = ErrorType.UNKNOWN,
    level: ErrorLevel = ErrorLevel.ERROR
  ) => {
    const errorInfo: ErrorInfo = {
      type,
      level,
      message: extractErrorMessage(error),
      code: extractErrorCode(error),
      context,
      details: error,
      timestamp: Date.now()
    }

    // 控制台日志
    if (finalConfig.logToConsole) {
      logError(errorInfo)
    }

    // 显示消息提示
    if (finalConfig.showMessage) {
      showErrorMessage(errorInfo)
    }

    // 显示通知
    if (finalConfig.showNotification) {
      showErrorNotification(errorInfo)
    }

    // 自定义处理器
    if (finalConfig.customHandler) {
      finalConfig.customHandler(errorInfo)
    }

    // 上报到服务器（可选）
    if (finalConfig.reportToServer) {
      reportError(errorInfo)
    }

    return errorInfo
  }

  return { handleError }
}

// 提取错误消息
const extractErrorMessage = (error: any): string => {
  if (typeof error === 'string') return error
  if (error?.message) return error.message
  if (error?.msg) return error.msg
  if (error?.data?.message) return error.data.message
  if (error?.response?.data?.message) return error.response.data.message
  return '未知错误'
}

// 提取错误代码
const extractErrorCode = (error: any): string | number | undefined => {
  if (error?.code) return error.code
  if (error?.status) return error.status
  if (error?.response?.status) return error.response.status
  if (error?.data?.code) return error.data.code
  return undefined
}

// 控制台日志
const logError = (errorInfo: ErrorInfo) => {
  const { level, message, context, code, details } = errorInfo
  const logMessage = `[${level.toUpperCase()}] ${context ? `[${context}] ` : ''}${message}${code ? ` (${code})` : ''}`
  
  switch (level) {
    case ErrorLevel.INFO:
      console.info(logMessage, details)
      break
    case ErrorLevel.WARNING:
      console.warn(logMessage, details)
      break
    case ErrorLevel.ERROR:
    case ErrorLevel.CRITICAL:
      console.error(logMessage, details)
      break
    default:
      console.log(logMessage, details)
  }
}

// 显示错误消息
const showErrorMessage = (errorInfo: ErrorInfo) => {
  const { level, message } = errorInfo
  
  switch (level) {
    case ErrorLevel.INFO:
      ElMessage.info(message)
      break
    case ErrorLevel.WARNING:
      ElMessage.warning(message)
      break
    case ErrorLevel.ERROR:
    case ErrorLevel.CRITICAL:
      ElMessage.error(message)
      break
    default:
      ElMessage(message)
  }
}

// 显示错误通知
const showErrorNotification = (errorInfo: ErrorInfo) => {
  const { level, message, context } = errorInfo
  
  ElNotification({
    title: context || '错误提示',
    message,
    type: level === ErrorLevel.WARNING ? 'warning' : 'error',
    duration: level === ErrorLevel.CRITICAL ? 0 : 4500
  })
}

// 上报错误到服务器
const reportError = async (errorInfo: ErrorInfo) => {
  try {
    // 这里应该调用实际的错误上报 API
    // await api.reportError(errorInfo)
    console.log('Error reported to server:', errorInfo)
  } catch (reportErr) {
    console.error('Failed to report error:', reportErr)
  }
}

// 默认错误处理器实例
export const useErrorHandler = createErrorHandler()

// 业务错误处理器
export const useBusinessErrorHandler = createErrorHandler({
  showMessage: true,
  logToConsole: true,
  reportToServer: true
})

// 网络错误处理器
export const useNetworkErrorHandler = createErrorHandler({
  showMessage: true,
  showNotification: true,
  logToConsole: true,
  reportToServer: true
})

// 验证错误处理器
export const useValidationErrorHandler = createErrorHandler({
  showMessage: true,
  logToConsole: false,
  reportToServer: false
})