# ct-cascader

## 基础用法
:::demo
cascader/cascader1
:::



## 根据api获取数据
:::demo
cascader/cascader2
:::



## 懒加载
:::demo
cascader/cascader3
:::




## Attributes

| 属性名       | 说明           | 类型      | 可选值                           | 默认值  |
| ------------ | -------------- | --------- | -------------------------------- | ------- |
modelValue | 绑定的值 | String/Array  |     |
params | 请求参数,配合url使用 | Object |    |   {}
api | 接口url | String  |   |
label | 按钮名称(必填) |  String |    |
resultType|  结果类型：1：绑定值为原生数组（如：[1,2,3]）,2：绑定值为逗号分隔字符串（如："1,2,3"）,3：绑定值为数组的最后一个元素（如：3），反显时需根据此值查找完整路径 | Number | 1   |
dataList| 字典数据 |  Array  |   | []
lazy| 懒加载：当为 true 时，开启动态加载下级节点 | Boolean | true/false | false
parentUrl| 当 lazy 为 true 且 resultType = 3 时，反显可能无法匹配上级数据，此时可传入 parent_url，根据当前节点获取所有父级节点数据 | String |  |
