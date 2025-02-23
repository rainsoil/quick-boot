import DefaultTheme from 'vitepress/theme'
import 'vitepress-theme-demoblock/dist/theme/styles/index.css'
import {useComponents} from './useComponents'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import locale from 'element-plus/es/locale/lang/zh-cn'
import c7Input  from "../../../src/packages/components/c7Input/src/index.vue";
import c7Ui from '../../../src/packages/index'
// 全局注入

import {getDictByType , getRequest} from "../configs/provideSerivce";

export default {
    extends: DefaultTheme,
    enhanceApp({app}) {


        // register global components
        useComponents(app)
        app.use(ElementPlus)
        app.use(c7Ui)


        // 注册所有 Element Plus 图标
        for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
            app.component(key, component)
        }
        app.component("c7Input",c7Input);

        app.provide("getRequest", getRequest)
        app.provide("getDictByType", getDictByType )

    }
}
