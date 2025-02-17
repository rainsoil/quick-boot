import C7Input from "./components/c7Input/index.js";
import tableView from "./hooks/tableView";
const components = {
    C7Input
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
