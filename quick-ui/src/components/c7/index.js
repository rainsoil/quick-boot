// C7 组件库导出文件
import C7Button from './c7-button/index.vue'
import C7Card from './c7-card/index.vue'
import C7Cascader from './c7-cascader/index.vue'
import C7Checkbox from './c7-checkbox/index.vue'
import C7DatePicker from './c7-date-picker/index.vue'
import C7Dialog from './c7-dialog/index.vue'
import C7DictTag from './c7-dict-tag/index.vue'
import C7JsonForm from './c7-json-form/index.vue'
import C7JsonTable from './c7-json-table/index.vue'
import C7JsonTableColumn from './c7-json-table-column/index.vue'
import C7Layer from './c7-layer/index.vue'
import C7Preview from './c7-preview/index.vue'
import C7Radio from './c7-radio/index.vue'
import C7Select from './c7-select/index.vue'
import C7SwitchForm from './c7-switch-form/index.vue'
import C7Title from './c7-title/index.vue'
import C7Upload from './c7-upload/index.vue'
import C7ButtonGroup from './c7-button-group/index.vue'

// 组件列表
const components = [
  C7Button,
  C7Card,
  C7Cascader,
  C7Checkbox,
  C7DatePicker,
  C7Dialog,
  C7DictTag,
  C7JsonForm,
  C7JsonTable,
  C7JsonTableColumn,
  C7Layer,
  C7Preview,
  C7Radio,
  C7Select,
  C7SwitchForm,
  C7Title,
  C7Upload,
  C7ButtonGroup
]

// 安装函数
const install = (app) => {
  components.forEach(component => {
    app.component(component.name, component)
  })
}

// 单独导出
export {
  C7Button,
  C7Card,
  C7Cascader,
  C7Checkbox,
  C7DatePicker,
  C7Dialog,
  C7DictTag,
  C7JsonForm,
  C7JsonTable,
  C7JsonTableColumn,
  C7Layer,
  C7Preview,
  C7Radio,
  C7Select,
  C7SwitchForm,
  C7Title,
  C7Upload,
  C7ButtonGroup
}

// 默认导出
export default {
  install
}
