# c7-button 按钮组件
> 常规的操作按钮

## 基础用法
:::demo

button/baseButton
:::

## 点击确认

:::demo

button/buttonWithConfirm
:::
## 按钮类型

:::demo
button/buttonWithType

:::

## Attributes

| 属性名       | 说明           | 类型      | 可选值                           | 默认值  |
| ------------ | -------------- | --------- | -------------------------------- | ------- |
clickEvent | 是否开启前端点击事件 | Boolean  | true/false    |  false
params | 请求参数,配合url使用 | Object |    |   {}
url | 接口url | String  |   |
label | 按钮名称(必填) |  String |    |
confirm| 是否弹出确认框(搭配api使用)|Boolean | true/false  |false
confirmMessage| 确认框弹出信息| String |  |
isSuccessCallback| 是否开启成功之后的回调 | Boolean | true/false | false
successMessage | 成功之后提示信息(当isSuccessCallback为false的时候,才启动) | String |  |
href| 跳转链接(当isSuccessCallback为false的时候,才启动) | String |  |
successCallback| 回调返回,当isSuccessCallback为true的时候,才启动 | Function |  |
