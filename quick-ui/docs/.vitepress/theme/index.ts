import theme from "vitepress/theme";

import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import "element-plus/theme-chalk/dark/css-vars.css";
// 图标并进行全局注册
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import zhCn from 'element-plus/es/locale/lang/zh-cn'

import {NotFound, globals} from "../vitepress";
import './styles/var.css'
import {ref} from "vue";
import { createPinia } from 'pinia';

export default {
    ...theme,
    NotFound,
    enhanceApp({app}) {
        app.use(ElementPlus, {
            locale: zhCn,
        })
        // 注册所有图标
        for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
            app.component(key, component);
        }
        // app.component("Demo", VPDemo);
        globals.forEach(([name, Comp]) => {
            app.component(name, Comp)
        })
        const pinia = createPinia();

        app.use(pinia); // 注册 Pinia


    },
};
