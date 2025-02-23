import tableView from "./hooks/tableView";
import c7Input from '../packages/components/c7Input/index.js';
import  c7DatePicker from '../packages/components/c7DatePicker/index.js'
const components = {
    c7Input,
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
