import type {App, Component} from "vue";
import c7Button from "./components/c7-button";

import 'element-plus/dist/index.css';
import ElementPlus from "element-plus";
import zhCn from "element-plus/es/locale/lang/zh-cn";

export * from './components'
const components = [c7Button,

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
