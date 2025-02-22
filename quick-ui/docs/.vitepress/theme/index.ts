import theme from "vitepress/theme";

import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import "element-plus/theme-chalk/dark/css-vars.css";
// 图标并进行全局注册
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import {NotFound, globals} from "../vitepress";
import {getDictByType, getRequest} from "../config/provideSerivce";
import {inject, ref, reactive} from 'vue'
import './styles/var.css'

export default {
    ...theme,
    NotFound,
    enhanceApp({app}) {
        app.use(ElementPlus);
        // 注册所有图标
        for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
            app.component(key, component);
        }
        // app.component("Demo", VPDemo);
        globals.forEach(([name, Comp]) => {
            app.component(name, Comp)
        })
        // app.config.globalProperties.$myGlobalValue = 'Hello VitePress!'
        app.config.globalProperties.$getRequest = getRequest;
        app.config.globalProperties.$getDictByType = getDictByType
        // //
        // let servicePath = '../../../docs/.vitepress/config/provideSerivce.js';
        // app.provide("servicePath", servicePath)
        //
        // const servicePath2 = inject('servicePath2');
        // console.log("servicePath2", servicePath2)
        // const globalData = reactive({
        //     message: 'Global message'
        // })
        // app.provide('globalData', globalData)

        // app.provide("getDictByType", getDictByType )
    },
};
