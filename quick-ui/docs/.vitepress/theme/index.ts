import theme from "vitepress/theme";

import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import "element-plus/theme-chalk/dark/css-vars.css";
import zhCn from 'element-plus/es/locale/lang/zh-cn'

// 图标并进行全局注册
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import {NotFound, globals} from "../vitepress";
import {getDictByType, getRequest, deleteRequest, openNewTab, postRequest} from "../config/provideSerivce";
import './styles/var.css'
// import c7Ui from '../../../src/packages/index.js'
import c7Ui from "c7-plus"
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
        globals.forEach(([name, Comp]) => {
            app.component(name, Comp)
        })

        app.use(c7Ui)

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
