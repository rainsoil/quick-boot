import tableView from "./hooks/tableView";
import dictHook from "./hooks/dictHook"
import injectService from './service/injectService'
import c7Button from "./components/c7-button/index.js";
import c7Card from "./components/c7-card/index.js";
import c7Title from "./components/c7-title/index.js";
import c7DatePicker from "./components/c7-date-picker/index.js";
import c7Select from "./components/c7-select/index.js";
import c7Checkbox from "./components/c7-checkbox/index.js";
import c7Cascader from "./components/c7-cascader/index.js";
import C7Upload from "./components/c7-upload/index.js";
import C7Preview from "./components/c7-preview/index.js";
import C7Layer from "./components/c7-layer/index.js";
import c7Dialog from "./components/c7-dialog/index.js";
import c7TableSearch from "./components/c7-search/index.js";
import c7Table from "./components/c7-table/index.js";

const components = {
    c7Button,
    c7Card,
    c7Title,
    c7DatePicker,
    c7Select,
    c7Checkbox,
    c7Cascader,
    C7Upload,
    C7Preview,
    C7Layer,
    c7Dialog,
    c7TableSearch,
    c7Table
}
const install = (app) => {
    console.log(c7Cascader)
    for (const key in components) {
        const componentConfig = components[key];
        app.component(componentConfig.name, componentConfig);
    }
}
if (typeof window !== 'undefined' && window.Vue) {
    install(window.Vue)
}

export default {
    install,
    ...components,
    tableView,
    dictHook,
    injectService
}
