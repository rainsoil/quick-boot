import tableView from "./hooks/tableView";
import dictHook from "./hooks/dictHook"
import c7Button from "../packages/components/c7-button/index.js";
import c7Input from "../packages/components/c7-input/index.js";
import c7Card from "../packages/components/c7-card/index.js";
import c7Title from "../packages/components/c7-title/index.js";
import c7DatePicker from "../packages/components/c7-date-picker/index.js";
import c7Select from "../packages/components/c7-select/index.js";
import c7Checkbox from "../packages/components/c7-checkbox/index.js";
import c7Cascader from "../packages/components/c7-cascader/index.js";
import C7Upload from "../packages/components/c7-upload/index.js";
import C7Preview from "../packages/components/c7-preview/index.js";

const components = {
    c7Button,
    c7Input,
    c7Card,
    c7Title,
    c7DatePicker,
    c7Select,
    c7Checkbox,
    c7Cascader,
    C7Upload,
    C7Preview
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
    dictHook
}
