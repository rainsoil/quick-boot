import C7Input from "./components/c7Input/src/index.vue";
import DemoInput from  "components/DemoInput.vue"
import c7Date from "components/C7-Input.vue";
import tableView from "./hooks/tableView";
const components = {
    C7Input,
    DemoInput,
    c7Date
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
