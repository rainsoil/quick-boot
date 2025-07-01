# 日期



## 基础用法
:::demo
datepicker/demo1
:::

## 结果合并
:::demo
datepicker/demo2
:::


## 默认
:::demo
datepicker/demo3
:::

## 结果为数组

:::demo
datepicker/demo4
:::


## Attributes

| 属性名         | 说明                                      | 类型                       | 可选值                                                                 | 默认值                |
|----------------|-------------------------------------------|----------------------------|------------------------------------------------------------------------|-----------------------|
| modelValue     | 绑定值，支持字符串、数组或日期对象        | `String \| Array \| Date` | —                                                                      | —                     |
| type           | 日期选择器类型                            | `String`                   | `'year'` `'month'` `'date'` `'datetime'` `'daterange'` `'datetimerange'` | `'date'`              |
| format         | 显示在输入框中的格式                      | `String`                   | —                                                                      | 根据 `type` 推断     |
| valueFormat    | 绑定值的格式                              | `String`                   | —                                                                      | 根据 `type` 推断     |
| rangeMerge     | 是否将范围值合并为逗号分隔字符串（仅限 range 类型） | `Boolean`                  | `true` `false`                                                         | `true`                |

---

## Events（事件）


---

## 插槽（Slots）

| 插槽名 | 说明                     |
|--------|--------------------------|
| 默认   | 自定义 `el-date-picker` 内部插槽内容 |

---

