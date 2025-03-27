# ct-title 标题(工具栏)
> 标题工具栏

## 基础用法
:::demo
c7Title/baseTitle
:::

## 增加色块
:::demo
c7Title/titleWithColorBlock
:::


## 卡槽
:::demo
c7Title/titleWithSlots
:::

## Attributes

| 属性名       | 说明           | 类型      | 可选值                           | 默认值  |
| ------------ | -------------- | --------- | -------------------------------- | ------- |
label | 标题 | String |    |
backgroundColor | 背景色(为空则不显示) | String | 颜色 | `#f0f2f5`
isShowColorBlock | 是否展示色块 | Boolean | true/false | false
colorBlockColor | 色块颜色 | String | 颜色 | `#409eff`
textSize| 字体大小|String | h1/h2/h3/h4/h5 | h2
isBold| 是否字体加粗| Boolean | true/false | true

## Slots
| 卡槽名 |  说明
| ------ | ------- |
| default | 标题右边区域
