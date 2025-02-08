import C7Input from "@/packages/components/c7Input/index.js";

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
    ...components
}
