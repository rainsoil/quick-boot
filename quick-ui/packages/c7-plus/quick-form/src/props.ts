import type { PropType } from "vue";
import type {
  CompTypes,
  FormItemRows,
  ItemOptions,
  BtnTypeUnit,
} from "./types";

/**
 * form 组件 props参数
 */
export const QFormProps = {
  model: {
    type: Object,
    required: true,
    default: () => { },
  },
  formOptions: {
    type: Array as PropType<Array<FormItemRows>>,
    required: true,
    default: () => [],
  },
  rules: {
    type: Object,
    required: false,
    default: () => { },
  },
  required: {
    type: Boolean,
    required: false,
  },
  gutter: {
    type: Number,
    required: false,
  },
  col: {
    type: Number,
    required: false,
  },
  buttons: {
    type: Array as PropType<Array<BtnTypeUnit>>,
    required: false,
    default: () => [],
  },
  readonly: {
    type: Boolean,
    required: false,
  },
};

/**
 * form-item 组件 props参数
 */
export const QFormItemProps = {
  formValue: {
    type: Object,
    default: () => { },
  },
  formOptions: {
    type: Object as PropType<Array<FormItemRows>>,
    required: false,
    default: () => [],
  },
  required: {
    type: Boolean,
    required: false,
  },
  isGrid: {
    type: Boolean,
    required: false,
  },
  globalCol: {
    type: Number,
    required: false,
  },
  readonly: {
    type: Boolean,
    required: false,
    default: false,
  },
  buttons: {
    type: Array as PropType<Array<BtnTypeUnit>>,
    required: false,
    default: () => [],
  },
};



/**
 * component 组件 props参数
 */
export const QFComponentProps = {
  // 组件类型
  label: String,
  type: {
    type: String as PropType<CompTypes>,
    required: true,
    default: "input",
  },
  prop: {
    required: true,
    type: String
  },
  formValue: {
    type: Object,
    default: () => { },
  },
  options: {
    type: Array as PropType<Array<ItemOptions>>,
    required: false,
    default: () => [],
  },
  orgAttrs: {
    type: Object,
    default: () => { },
  },
  readonly: {
    type: Boolean,
    required: false,
    default: false,
  },
};
