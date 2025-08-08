// 类型定义统一导出
export * from './global'
export * from './button'
export * from './upload'
export * from './select'

// 组件实例类型
import type { DefineComponent } from 'vue'
import type { ButtonProps, ButtonEmits } from './button'
import type { UploadProps, UploadEmits } from './upload'
import type { SelectProps, SelectEmits } from './select'

// 组件实例类型定义
export type ButtonInstance = DefineComponent<ButtonProps, ButtonEmits>
export type UploadInstance = DefineComponent<UploadProps, UploadEmits>
export type SelectInstance = DefineComponent<SelectProps, SelectEmits>

// 导出常用类型别名
export type {
  // 基础类型
  ComponentSize,
  ComponentStatus,
  ButtonType,
  ButtonPresetType,
  DateType,
  ThemeType,
  
  // 数据类型
  Option,
  TreeData,
  TableColumn,
  PaginationConfig,
  ApiResponse,
  RequestParams,
  FileInfo,
  
  // 函数类型
  FetchDataFunction,
  DataFormatterFunction,
  ValidatorFunction,
  EventHandler,
  ChangeEventHandler,
  
  // 接口类型
  BaseComponentProps,
  BaseFormComponentProps,
  ComponentExposed
} from './global'