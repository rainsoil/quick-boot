import {
  defineComponent,
  ref,
  toRefs,
  defineExpose,
  watch,
  provide,
  onMounted,
  computed,
  effect,
  reactive,
} from "vue";
import QFormItem from "./qf-item";
import type { FormItemRows, Arrayable, BtnType, BtnTypeUnit } from "./types";
import {
  type FormInstance,
  type FormValidateCallback,
  type FormItemProp,
  type FormRules,
  ElConfigProvider,
  ElForm,
  ElRow
} from "element-plus";
import { findFirstHaveColFormItem } from "./utils";
import { QFormProps } from "./props";
import zhCn from "element-plus/es/locale/lang/zh-cn";
import { has, isFunction, isString } from "lodash-es";
import '../style/index.scss'

export default defineComponent({
  name: 'q-form',
  props: QFormProps,
  emits: ["change", "input", "validate", 'clear', 'blur', 'focus', "search", "reset", "cancel", "submit"],
  setup(props, { attrs, slots, emit, expose }) {
    const {
      col,
      formOptions,
      required,
      rules,
    } = toRefs(props);
    // const { gutter } = toRefs(attrs);
    const formRef = ref<FormInstance>();
    let newRules = reactive<FormRules<Record<string, any>>>({});

    const fCol = findFirstHaveColFormItem(
      formOptions.value
    ); /* 第一个设置栅格的表单项 */

    const globalCol = computed(() => {
      return col.value || fCol;
    }); /* 计算栅格布局值 */

    watch(() => props.formOptions, (vals) => {
      console.log(vals);
    }, { deep: true })

    const isGrid = ref(globalCol.value ? true : false); /* 是否开启layout布局 */

    // 处理 rules 副作用
    effect(() => {
      if (required.value && !rules.value) {
        formOptions.value.forEach((item) => {
          newRules[item.prop] = [
            {
              required: has(item, "required") ? isFunction(item.required) ? item.required() : item.required : true,
              message: `${isString(item.label) ? item.label : item.prop
                }不能为空`,
              trigger: ["blur", "change"],
            },
          ];
        });
      } else {
        newRules = { ...rules.value };
      }
    });

    /* ================================== form 事件触发 start ===================================== */
    const onValidate = (
      prop: FormItemProp,
      isValid: boolean,
      message: string
    ): void => {
      emit("validate", prop, isValid, message);
    };

    /* ================================== form 事件触发 end ===================================== */

    /* ================================== form 实例化方法 start ================================== */
    const validateField = (
      props?: Arrayable<FormItemProp> | undefined,
      callback?: FormValidateCallback | undefined
    ) => formRef.value?.validateField(props, callback);
    const validate = async (callback?: FormValidateCallback) => {
      formRef.value?.validate(callback);
    };
    const resetFields = (props?: Arrayable<FormItemProp> | undefined) => {
      formRef.value?.resetFields(props);
    };
    const scrollToField = (prop: FormItemProp) =>
      formRef.value?.scrollToField(prop);
    const clearValidate = (props?: Arrayable<FormItemProp> | undefined) =>
      formRef.value?.clearValidate(props);

    expose({
      validate,
      validateField,
      resetFields,
      scrollToField,
      clearValidate,
    });
    /* ================================== form 实例化方法 end ================================== */

    provide<{
      formSlots: Record<string, any>;
      validate: (callback?: FormValidateCallback) => Promise<void>;
      onChange: (...args: any) => void;
      btnEvent: (event: BtnType) => void;
      onInput: (value: any, prop: string) => void;
      onClear: (prop: string) => void;
      onBlur: (e: FocusEvent, prop: string) => void;
      onFocus: (e: FocusEvent, prop: string) => void;
    }>("formObserver", {
      formSlots: slots,
      // 表单验证触发
      validate: validate,
      // 某一表单项值改变时触发
      onChange: (...args) => {
        // console.log('qf-', ...args);
        emit("change", ...args)
      },
      // 默认按钮事件触发
      btnEvent: (event: BtnType): void => {
        if (event === "reset") {
          formRef.value?.resetFields();
        }

        emit(event);
      },
      // 输入框事件触发
      onInput: (value: any, prop: string) => emit("input", value, prop),
      // 清除事件触发
      onClear: (prop: string) => emit("clear", prop),
      onBlur: (e: FocusEvent, prop: string) => emit('blur', e, prop),
      onFocus: (e: FocusEvent, prop: string) => emit('focus', e, prop),
    });

    return {
      formRef,
      ...toRefs(props),
      attrs,
      isGrid,
      globalCol,
      newRules,
      onValidate,
    };
  },
  render() {
    const {
      model,
      newRules,
      formOptions,
      isGrid,
      attrs,
      gutter,
      globalCol,
      readonly,
      buttons,
      onValidate,
    } = this;

    // 渲染formitem项
    const rowRenderer = () => {
      // console.log(formOptions);

      return (
        <>
          <QFormItem
            formValue={model}
            formOptions={formOptions}
            isGrid={!!globalCol}
            globalCol={globalCol}
            buttons={buttons}
            readonly={readonly}
          />
        </>
      );
    };

    return (
      <>
        <ElConfigProvider locale={zhCn}>
          <ElForm
            ref="formRef"
            size="default"
            model={model}
            rules={newRules}
            {...attrs}
            onValidate={onValidate}
          >
            {isGrid ? (
              <ElRow gutter={gutter}>{rowRenderer()}</ElRow>
            ) : (
              rowRenderer()
            )}
          </ElForm>
        </ElConfigProvider>
      </>
    );
  },
});
