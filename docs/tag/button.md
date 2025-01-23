# c7-button按钮

常规的操作按钮

## 基础用户

按钮分为点击触发后台事件和点击触发前端事件两种模式

- 点击触发后台操作,需要执行 `url` 属性,该属性为后台方法上的接口路径,通过 `:datas` 指定参数
- 点击触发前端事件, 需要指定 `click-event`属性, 并通过 `@click` 指定点击触发的事件

```vue
xxx
```

## `Attributes`


| 参数              | 说明                                                          | 类型     | 可选值     | 默认值 |
| ----------------- | ------------------------------------------------------------- | -------- | ---------- | ------ |
| label             | 按钮名称                                                      | String   |            |        |
| url               | 点击触发的后台接口地址                                        | String   |            |        |
| datas             | 触发后端事件的参数                                            | Object   |            |        |
| clickEvent        | 是否启用前端点击事件                                          | Boolean  | true/false | false  |
| confirm           | 是否弹出框                                                    | Boolean  | true/false | false  |
| confirmMessage    | 弹出框提示信息                                                | String   |            |        |
| isSuccessCallback | 是否开启成功之后的回调                                        | Boolean  | true/false | false  |
| successMessage    | 成功之后的提示信息（当isSuccessCallback为false 的时候才生效） | String   |            |        |
| successCallbacl   | 回调函数                                                      | Function |            |        |
| href              | 路由跳转地址                                                  | String   |            |        |
