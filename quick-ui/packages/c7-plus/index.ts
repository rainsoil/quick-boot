import type {App, Component} from "vue";
import c7Button from "./components/c7-button";
import c7Card from "./components/c7-card";
import c7Checkbox from "./components/c7-checkbox";
import c7Cascader from "./components/c7-cascader";
import c7DatePicker from "./components/c7-date-picker";

import c7Dialog from "./components/c7-dialog";
import C7Radio from "./components/c7-radio";
import c7Crud from "./components/c7-crud";
import c7DictTag from "./components/c7-dict-tag";
import c7Upload from "./components/c7-upload";
import C7Preview from "./components/c7-preview";

import c7JsonTable from "./components/c7-json-table";
import c7Select from "./components/c7-select";
import c7Title from "./components/c7-title";
import c7JsonForm from './components/c7-json-form'
import C7Layer from "./components/c7-layer";
import c7JsonTableColumn from "./components/c7-json-table-column";

import 'element-plus/dist/index.css';
import ElementPlus from "element-plus";
import zhCn from "element-plus/es/locale/lang/zh-cn";

// 导出组件
export * from './components'

// 导出类型定义
export * from './types'

// 导出工具函数
export * from './utils'

// 导出 hooks
export * from './hooks'
const components = [c7Button,
    c7Card,
    c7Title,
    c7DatePicker,
    c7Select,
    c7Checkbox,
    c7Cascader,
    c7Upload,
    C7Preview,
    C7Layer,
    c7Dialog,
    C7Radio,
    c7Crud,
    c7DictTag,
    c7JsonForm,
    c7JsonTableColumn,
    c7JsonTable

];
const install = (app: App) => {
    app.use(ElementPlus, {
        locale: zhCn,
    })
    components.forEach((comp) => {
        app.component(comp.name, comp);
    });

};
if (typeof window !== 'undefined' && window.Vue) {
    install(window.Vue)
}

export default {
    install
};
