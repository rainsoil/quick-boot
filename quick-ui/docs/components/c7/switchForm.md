# c7-switch-form 表单切换组件

一个用于在不同表单或页面之间切换的组件，支持带返回按钮的页面头部。

## 基础用法

在不同内容之间进行切换：

:::demo
switch-form/demo
:::

## Attributes

| 属性名 | 说明 | 类型 | 默认值 |
|--------|------|------|--------|
| `showIndexs` | 显示配置数组 | Array | `[]` |
| `modelValue` | 当前显示的页面名称 | String | `''` |

### showIndexs 配置项

| 属性名 | 说明 | 类型 | 默认值 |
|--------|------|------|--------|
| `title` | 页面标题 | String | - |
| `name` | 页面名称（用于插槽名和切换标识） | String | - |
| `header` | 是否显示页面头部（带返回按钮） | Boolean | `false` |
| `closeIndex` | 返回时切换到哪个页面 | String | - |

## Events

| 事件名 | 说明 | 回调参数 |
|--------|------|----------|
| `update:modelValue` | 当前显示页面变化时触发 | `(pageName: string)` |

## Slots

| 插槽名 | 说明 |
|--------|------|
| `[name]` | 动态插槽，name 为 showIndexs 中配置的 name 值 |

## 使用说明

1. **配置页面**：通过 `showIndexs` 数组配置所有可切换的页面
2. **动态插槽**：每个页面对应一个插槽，插槽名为配置中的 `name` 值
3. **页面头部**：当 `header` 为 `true` 时，会显示带返回按钮的页面头部
4. **切换控制**：通过 `v-model` 控制当前显示的页面

### 基础切换功能

展示各种基础切换功能的使用：

:::demo
switch-form/demo1
:::

### 高级切换功能

展示高级切换功能的使用：

:::demo
switch-form/demo2
:::

### 实际应用场景

展示在实际应用中的使用场景：

:::demo
switch-form/demo3
:::
