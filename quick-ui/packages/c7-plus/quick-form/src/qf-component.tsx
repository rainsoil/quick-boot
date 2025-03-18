import { defineComponent, renderSlot, toRefs, inject } from "vue";
import type { ItemOptions, DatePickerType, CompTypes } from "./types";
import { defaultPlaceholder } from "./utils";
import { QFComponentProps } from "./props";
import { has, isObject } from "lodash-es";
import { ElInput, ElInputNumber, ElSelect, ElOption, ElSelectV2, ElCascader, ElTreeSelect, ElTimeSelect, ElRadioGroup, ElRadio, ElRadioButton, ElCheckboxGroup, ElCheckbox, ElCheckboxButton, ElDatePicker, ElTimePicker, ElSlider, ElRate, ElColorPicker, ElSwitch } from 'element-plus'
const defaultStyle = {
  width: "100%",
};
export default defineComponent({
  name: "App",
  props: QFComponentProps,
  setup(props, { slots }) {
    const { onChange, onInput, onClear, onBlur, onFocus, formSlots }: Indexable = inject(
      "formObserver"
    ) as any;

    const { formValue, prop, type, label, orgAttrs, options, }: {
      prop: string,
      type: CompTypes,
      label?: string,
      orgAttrs?: Indexable,
      options?: ItemOptions[],
      [K: string]: any
    } = props as any
    const { readonly } = toRefs(props)
    const InputEventAblity: CompTypes[] = ['input', 'textarea', 'select', 'select-v2', "slider"]
    const BlurWithFocusEventAblity: CompTypes[] = ['input', 'textarea', 'input-number', 'select', 'select-v2', "time-select", "time-picker", 'date-picker', "color-picker"]
    const ClearEventAblity: CompTypes[] = ['input', 'textarea', 'select', 'select-v2']

    const compEventRow = () => {
      let eventRow: any = {
        onChange: (...arg: any) => {
          onChange(...arg, prop)
        },
      }
      // 具备 input 事件
      if (InputEventAblity.includes(type)) {
        eventRow['onInput'] = (value: any) => onInput(value, prop)
      }

      // 具备 onBlur 和 onFocus 事件
      if (BlurWithFocusEventAblity.includes(type)) {
        eventRow['onBlur'] = (e: FocusEvent) => onBlur(e, prop)
        eventRow['onFocus'] = (e: FocusEvent) => onFocus(e, prop)
      }

      // 具备 clear 事件
      if (ClearEventAblity.includes(type)) {
        eventRow['onClear'] = () => onClear(prop)
      }
      return eventRow
    }


    const compRenderer: any = {
      input: () => {
        return (
          <ElInput
            v-model={formValue[prop]}
            clearable
            placeholder={defaultPlaceholder(type, label)}
            style={defaultStyle}
            {...compEventRow()}
            readonly={readonly.value}
            {...orgAttrs}
          >
          </ElInput>
        );
      },
      "input-number": () => {
        return (
          <ElInputNumber
            v-model={formValue[prop]}
            style={defaultStyle}
            readonly={readonly.value}
            {...compEventRow()}
            {...orgAttrs}
          />
        );
      },
      textarea: () => (
        <ElInput
          type="textarea"
          v-model={formValue[prop]}
          clearable
          placeholder={defaultPlaceholder(type, label)}
          style={defaultStyle}
          readonly={readonly.value}
          {...compEventRow()}
          {...orgAttrs}
        ></ElInput>
      ),
      select: () => {
        return (
          <ElSelect
            v-model={formValue[prop]}
            clearable
            placeholder={defaultPlaceholder(type, label)}
            style={defaultStyle}
            disabled={readonly.value}
            class={{ 'qform-item-comp-readonly': readonly.value }}
            {...compEventRow()}
            {...orgAttrs}
          >
            {options!.map((item: ItemOptions) => {
              return (
                <ElOption value={item.value} label={item.label}></ElOption>
              );
            })}
          </ElSelect>
        );
      },
      "select-v2": () => {
        return (
          <ElSelectV2
            v-model={formValue[prop]}
            placeholder={defaultPlaceholder(type, label)}
            options={options}
            clearable
            style={defaultStyle}
            disabled={readonly.value}
            class={{ 'qform-item-comp-readonly': readonly.value }}
            {...compEventRow()}
            {...orgAttrs}
          />
        );
      },
      cascader: () => {
        return (
          <ElCascader
            v-model={formValue[prop]}
            options={options}
            clearable
            placeholder={defaultPlaceholder(type, label)}
            style={defaultStyle}
            disabled={readonly.value}
            class={{ 'qform-item-comp-readonly': readonly.value }}
            {...compEventRow()}
            {...orgAttrs}
          />
        );
      },
      'tree-select': () => {
        return (
          <ElTreeSelect
            v-model={formValue[prop]}
            data={options}
            clearable
            placeholder={defaultPlaceholder(type, label)}
            style={defaultStyle}
            disabled={readonly.value}
            class={{ 'qform-item-comp-readonly': readonly.value }}
            {...compEventRow()}
            {...orgAttrs}
          />
        )
      },

      "time-select": () => {
        return (
          <ElTimeSelect
            v-model={formValue[prop]}
            placeholder={defaultPlaceholder(type, label)}
            clearable
            style={defaultStyle}
            disabled={readonly.value}
            class={{ 'qform-item-comp-readonly': readonly.value }}
            {...compEventRow()}
            {...orgAttrs}
          />
        );
      },
      radio: () => {
        const isBtnModel = isObject(orgAttrs) && has(orgAttrs, 'button') && orgAttrs.button == true;
        return (
          <ElRadioGroup
            v-model={formValue[prop]}
            disabled={readonly.value}
            class={{ 'qform-item-comp-readonly': readonly.value }}
            {...compEventRow()}
            {...orgAttrs}
          >
            {options!.map((item: ItemOptions) => {
              return isBtnModel ? <ElRadioButton {...item} label={item.value} >{item.label}</ElRadioButton> : <ElRadio {...item} label={item.value}>{item.label}</ElRadio>;
            })}
          </ElRadioGroup>
        );
      },
      checkbox: () => {
        const isBtnModel = isObject(orgAttrs) && has(orgAttrs, 'button') && orgAttrs.button == true
        return (
          <ElCheckboxGroup
            v-model={formValue[prop]}
            disabled={readonly.value}
            class={{ 'qform-item-comp-readonly': readonly.value }}
            {...compEventRow()}
            {...orgAttrs}
          >
            {options!.map((item: ItemOptions) => {
              return (
                isBtnModel ? <ElCheckboxButton label={item.value}>{item.label}</ElCheckboxButton> : <ElCheckbox label={item.value}>{item.label}</ElCheckbox>
              );
            })}
          </ElCheckboxGroup>
        );
      },
      "date-picker": () => {
        const DEFAULT_FORMATS_TIME = "HH:mm:ss";
        const DEFAULT_FORMATS_DATE = "YYYY-MM-DD";
        const dateFormat: { [K in DatePickerType]: string } = {
          date: DEFAULT_FORMATS_DATE,
          dates: DEFAULT_FORMATS_DATE,
          year: "YYYY",
          month: "YYYY-MM",
          week: "YYYY-ww",
          datetime: `${DEFAULT_FORMATS_DATE} ${DEFAULT_FORMATS_TIME}`,
          daterange: DEFAULT_FORMATS_DATE,
          monthrange: "YYYY-MM",
          datetimerange: `${DEFAULT_FORMATS_DATE} ${DEFAULT_FORMATS_TIME}`,
        };
        const compType: DatePickerType = isObject(orgAttrs) && has(orgAttrs, 'type') ? orgAttrs.type : 'date' as any
        return (
          <ElDatePicker
            v-model={formValue[prop]}
            clearable
            placeholder={defaultPlaceholder(type, label)}
            type={compType ? dateFormat[compType] : "date"}
            format={dateFormat[compType]}
            value-format={compType && compType !== "week" ? dateFormat[compType] : null}
            style={defaultStyle}
            readonly={readonly.value}
            {...compEventRow()}
            {...orgAttrs}
          />
        );
      },
      "time-picker": () => {
        const format = isObject(orgAttrs) && has(orgAttrs, 'format') ? orgAttrs.format : 'HH:mm:ss'
        return (
          <ElTimePicker
            v-model={formValue[prop]}
            placeholder={defaultPlaceholder(type, label)}
            clearable
            style={defaultStyle}
            readonly={readonly.value}
            format={format}
            value-format={format}
            {...compEventRow()}
            {...orgAttrs}
          />
        );
      },
      "color-picker": () => {
        return (
          <ElColorPicker
            v-model={formValue[prop]}
            disabled={readonly.value}
            class={{ 'qform-item-comp-readonly': readonly.value }}
            {...compEventRow()}
            {...orgAttrs}
          />
        );
      },
      rate: () => {
        return (
          <ElRate
            v-model={formValue[prop]}
            disabled={readonly.value}
            {...compEventRow()}
            {...orgAttrs}
          />
        );
      },
      slider: () => {
        return (
          <ElSlider
            v-model={formValue[prop]}
            style={{ ...defaultStyle, minWidth: "100px" }}
            disabled={readonly.value}
            class={{ 'qform-item-comp-readonly': readonly.value }}
            {...compEventRow()}
            {...orgAttrs}
          />
        );
      },
      switch: () => {
        return (
          <ElSwitch
            v-model={formValue[prop]}
            disabled={readonly.value}
            class={{ 'qform-item-comp-readonly': readonly.value }}
            {...compEventRow()}
            {...orgAttrs}
          />
        );
      },
      text: () => {
        return formValue[prop];
      },
      html: () => {
        return <div v-html={formValue[prop]}></div>
      },
      slot: () => {
        return renderSlot(formSlots, prop!);
      },
    };

    return () => (<>{compRenderer[type]()}</>)
  },
});
