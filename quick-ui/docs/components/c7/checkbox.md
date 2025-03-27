# checkBox


## 基础
:::demo
checkbox/baseCheckbox

:::


## 初始化数据(结果用,分割)
:::demo


checkbox/initDataCheckBox

:::


## 初始化数据(结果为数组)
:::demo


checkbox/initDataCheckBox2

:::


## Attributes

| 属性名       | 说明           | 类型      | 可选值                           | 默认值  |
| ------------ | -------------- | --------- | -------------------------------- | ------- |
value | 绑定的值 | String/Array  |     |
params | 请求参数,配合url使用 | Object |    |   {}
url | 接口url | String  |   |
label | 按钮名称(必填) |  String |    |
dictType|  字典值type | String |    |
dataList| 字典数据 |  Array  |   | []
rangeMerge| 结果是否合并(,隔开) | Boolean | true/false | false
indeterminate| 当 indeterminate 为 true 时，显示全选/全不选复选框 | Boolean | true/false | false
