// c7-button 组件类型定义
import type { ButtonType, ButtonPresetType, ComponentSize } from './global'

// 按钮配置接口
export interface ButtonConfig {
  icon?: string
  label?: string
  type?: ButtonType
  plain?: boolean
}

// 按钮 Props 接口
export interface ButtonProps {
  // 基础属性
  type?: ButtonType
  btnType?: ButtonPresetType
  plain?: boolean
  label?: string
  icon?: string
  size?: ComponentSize
  disabled?: boolean
  loading?: boolean
  
  // 功能属性
  clickFunction?: () => Promise<any> | any
  confirm?: boolean
  confirmMessage?: string
  
  // 回调配置
  isSuccessCallback?: boolean
  successMessage?: string
  isErrorCallback?: boolean
  errorMessage?: string
  errorMessageType?: 'msg' | 'res'
  
  // 验证配置
  validate?: boolean
  validateRef?: any
  checkSuccess?: (response: any) => boolean
}

// 按钮事件接口
export interface ButtonEmits {
  successCallback: (result: any) => void
  errorCallback: (error: any, message?: string) => void
}

// 按钮暴露的方法
export interface ButtonExposed {
  // 可以添加按钮特有的方法
}