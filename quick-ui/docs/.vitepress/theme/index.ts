import theme from "vitepress/theme";

import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import "element-plus/theme-chalk/dark/css-vars.css";
// 图标并进行全局注册
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import { NotFound, globals } from "../vitepress";
import './styles/var.css'

import {getDictByType, getRequest, deleteRequest, openNewTab, postRequest} from "../config/provideSerivce";

export default {
  ...theme,
  NotFound,
  enhanceApp({ app }) {
    app.use(ElementPlus);
    // 注册所有图标
    for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
      app.component(key, component);
    }
    // app.component("Demo", VPDemo);
    globals.forEach(([name, Comp]) => {
      app.component(name, Comp)
    })

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
