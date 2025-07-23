import theme from "vitepress/theme";

import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import "element-plus/theme-chalk/dark/css-vars.css";
// 图标并进行全局注册
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import zhCn from 'element-plus/es/locale/lang/zh-cn'

import {NotFound, globals} from "../vitepress";
import './styles/var.css'
import {getDictByType, getRequest, deleteRequest, openNewTab, postRequest} from "../config/provideSerivce";
import {ref} from "vue";
import { createPinia } from 'pinia';
import {IC7Config} from "c7-plus";

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

        const c7Config: IC7Config = {
            dictUrl:'https://m1.apifoxmock.com/m1/4742948-4395863-default/mockapi/dict'
        }
        app.provide("c7Config", c7Config);
        app.config.globalProperties.$getRequest = getRequest;
        app.config.globalProperties.$postRequest = postRequest;
        app.config.globalProperties.$getDictByType = getDictByType
        app.config.globalProperties.$deleteRequest = deleteRequest
        app.config.globalProperties.$openNewTab = openNewTab
        if (typeof window !== 'undefined') {
            // 在浏览器中运行的代码
            window.$getRequest = getRequest;
            window.$postRequest = postRequest;
            window.$getDictByType = getDictByType
            window.$deleteRequest = deleteRequest
            window.$openNewTab = openNewTab
        }
    },
};
