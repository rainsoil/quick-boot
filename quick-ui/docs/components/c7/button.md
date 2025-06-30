# c7-button 按钮组件

常用的操作按钮
> 除了官网的操作之后额外加的


## 基础用法
:::demo

button/demo1
:::



## 调用事件
:::demo

button/demo2
:::


## 调用事件并弹框消息
:::demo

button/demo3
:::


## 内置简单按钮
新增、修改、删除、查询、重置等几个简单按钮


:::demo

button/demo4
:::


## 表单检验



:::demo

button/demo5
:::


---

## Attributes（属性）

| 属性名              | 说明                                           | 类型                     | 默认值      |
|---------------------|-----------------------------------------------|--------------------------|-------------|
| `type`              | Element Plus 按钮类型                         | `string`                 | `'primary'` |
| `btnType`           | 按钮业务类型，自动设置 `icon`、`label`、`type` | `'add'` \| `'edit'` \| `'delete'` \| `'query'` \| `'refresh'` \| `'upload'` \| `'download'` \| `'submit'` \| `'cancel'` | `''` |
| `plain`             | 是否朴素按钮                                   | `boolean`                | `true`      |
| `label`             | 按钮文本内容                                   | `string`                 | `''`        |
| `icon`              | 图标名称（自动根据 `btnType` 设置）            | `string`                 | 根据 `btnType` 自动生成 |
| `clickFunction`     | 点击按钮时执行的异步函数（支持 `Promise`）     | `Function`               | `null`      |
| `confirm`           | 是否启用二次确认框                             | `boolean`                | `false`     |
| `confirmMessage`    | 二次确认框提示内容                             | `string`                 | `''`        |
| `isSuccessCallback` | 是否使用成功回调而不是自动弹框                 | `boolean`                | `false`     |
| `successMessage`    | 成功提示内容（当不使用回调时生效）             | `string`                 | `''`        |
| `isErrorCallback`   | 是否使用错误回调而不是自动错误提示             | `boolean`                | `false`     |
| `errorMessageType`  | 错误提示信息来源（`msg`：固定文案，`res`：从结果中取） | `'msg'` \| `'res'`     | `'res'`     |
| `errorMessage`      | 错误提示信息字段（可为路径字符串）             | `string`                 | `'msg'`     |
| `checkSuccess`      | 判断返回结果是否成功的函数                     | `(res: any) => boolean`  | `(res) => res.code === 200` |
| `validate`          | 是否进行参数校验（用于表单）                   | `boolean`                | `false`     |
| `validateRef`       | Element Plus 表单实例引用（`ref`）             | `FormInstance` \| `null` | `null`      |

---

##  Events（事件）

| 事件名              | 说明                                           | 回调参数                                      |
|---------------------|------------------------------------------------|-----------------------------------------------|
| `successCallback`   | 当接口调用成功且 `isSuccessCallback = true` 时触发 | `(result: any)`                               |
| `errorCallback`     | 当接口调用失败且 `isErrorCallback = true` 时触发 | `(result: any, errorMessage?: string)` <br>或表单校验失败时的 `fields` |

---

