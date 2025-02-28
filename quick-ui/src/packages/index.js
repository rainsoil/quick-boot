import tableView from "./hooks/tableView";
import c7Button from "../packages/components/c7-button/index.js";
import c7Input from "../packages/components/c7-input/index.js";
import c7Card from "../packages/components/c7-card/index.js";
import c7Title from "../packages/components/c7-title/index.js";
import c7DatePicker from "../packages/components/c7-date-picker/index.js";


const components = {
    c7Button,
    c7Input,
    c7Card,
    c7Title,
    c7DatePicker
}
const install = (app) => {
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
    tableView
}
