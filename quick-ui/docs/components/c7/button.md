# c7-button 按钮组件

常用的操作按钮，基于 Element Plus 的 `el-button` 组件进行封装，提供了更丰富的功能和更便捷的使用方式。

> 除了 Element Plus 的基础功能外，还额外增加了业务类型按钮、异步操作处理、表单验证等功能

## 基础用法

最简单的使用方式，直接使用插槽内容：

:::demo

button/demo1
:::

## 调用事件

支持传入异步函数，自动处理加载状态和结果反馈：

:::demo

button/demo2
:::

## 调用事件并弹框消息

支持二次确认功能，防止误操作：

:::demo

button/demo3
:::

## 内置简单按钮

新增、修改、删除、查询、重置等几个简单按钮，自动设置图标、文本和样式：

:::demo

button/demo4
:::

## 表单检验

支持与 Element Plus 表单组件集成，自动进行表单验证：

:::demo

button/demo5
:::

## 高级用法

### 自定义成功/错误回调

:::demo

button/demo6
:::

### 自定义成功判断逻辑

:::demo

button/demo7
:::

## 完整示例
:::demo

button/demo8
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

### btnType 类型说明

| 类型      | 图标    | 文本   | 按钮类型    | 说明     |
|-----------|---------|--------|-------------|----------|
| `add`     | Plus    | 新增   | primary     | 新增操作 |
| `edit`    | Edit    | 修改   | success     | 修改操作 |
| `delete`  | Delete  | 删除   | danger      | 删除操作 |
| `query`   | Search  | 查询   | primary     | 查询操作 |
| `refresh` | Refresh | 重置   | default     | 重置操作 |
| `upload`  | Upload  | 上传   | info        | 上传操作 |
| `download`| Download| 下载   | warning     | 下载操作 |
| `submit`  | -       | 确定   | primary     | 提交操作 |
| `cancel`  | -       | 取消   | info        | 取消操作 |

---

## Events（事件）

| 事件名              | 说明                                           | 回调参数                                      |
|---------------------|------------------------------------------------|-----------------------------------------------|
| `successCallback`   | 当接口调用成功且 `isSuccessCallback = true` 时触发 | `(result: any)`                               |
| `errorCallback`     | 当接口调用失败且 `isErrorCallback = true` 时触发 | `(result: any, errorMessage?: string)` <br>或表单校验失败时的 `fields` |

---

## 插槽

| 插槽名    | 说明           |
|-----------|----------------|
| `default` | 按钮内容插槽   |

---

## 注意事项

1. **异步函数要求**：`clickFunction` 必须返回 Promise 或支持 async/await 的函数
2. **表单验证**：使用 `validate` 功能时，需要确保 `validateRef` 是有效的 Element Plus 表单实例
3. **防抖处理**：组件内置了防抖机制，防止重复点击
4. **加载状态**：当 `clickFunction` 执行时，按钮会自动显示加载状态
5. **错误处理**：组件会自动捕获异常并显示错误信息，除非设置了 `isErrorCallback = true`

---




