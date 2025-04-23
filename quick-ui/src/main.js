import {createApp} from 'vue'

import Cookies from 'js-cookie'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import locale from 'element-plus/es/locale/lang/zh-cn'
import '@/assets/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import directive from './directive' // directive
import c7Plus from 'c7-plus'
// 加载组件
// 注册指令
import plugins from './plugins' // plugins
import {download} from '@/utils/request'
// svg图标
import 'virtual:svg-icons-register'
import SvgIcon from '@/components/SvgIcon'
import elementIcons from '@/components/SvgIcon/svgicon'
import './permission' // permission control

import {useDict} from '@/utils/dict'
import {parseTime, resetForm, addDateRange, handleTree, selectDictLabel, selectDictLabels} from '@/utils/ruoyi'

// 分页组件
import Pagination from '@/components/Pagination'
// 自定义表格工具组件
import RightToolbar from '@/components/RightToolbar'
// 富文本组件
import Editor from "@/components/Editor"
// 文件上传组件
import FileUpload from "@/components/FileUpload"

// 图片预览组件
import ImagePreview from "@/components/ImagePreview"
// 自定义树选择组件
import TreeSelect from '@/components/TreeSelect'
// 字典标签组件
import DictTag from '@/components/DictTag'

import formLayout from '@/components/form-layout/index.vue'


const app = createApp(App)

// 全局方法挂载
app.config.globalProperties.useDict = useDict


app.config.globalProperties.download = download
app.config.globalProperties.parseTime = parseTime
app.config.globalProperties.resetForm = resetForm
app.config.globalProperties.handleTree = handleTree
app.config.globalProperties.addDateRange = addDateRange
app.config.globalProperties.selectDictLabel = selectDictLabel
app.config.globalProperties.selectDictLabels = selectDictLabels

// 全局组件挂载
app.component('DictTag', DictTag)
app.component('Pagination', Pagination)
app.component('TreeSelect', TreeSelect)
app.component('FileUpload', FileUpload)
app.component('ImagePreview', ImagePreview)
app.component('RightToolbar', RightToolbar)
app.component('Editor', Editor)
app.component('formLayout', formLayout)

const pinia = createPinia();

app.use(pinia); // 注册 Pinia
// 全局注入
import {getRequest, deleteRequest, postRequest, openNewTab} from "./service/provideSerivce";
import {createPinia} from "pinia";

window.$postRequest = postRequest
app.config.globalProperties.$getRequest = getRequest;
app.config.globalProperties.$deleteRequest = deleteRequest

const c7Config = {
    dictUrl: '/system/dict/type'
}
app.provide("c7Config", c7Config);
app.config.globalProperties.$getRequest = getRequest;
app.config.globalProperties.$postRequest = postRequest;
app.config.globalProperties.$deleteRequest = deleteRequest
app.config.globalProperties.$openNewTab = openNewTab
if (typeof window !== 'undefined') {
    // 在浏览器中运行的代码
    window.$getRequest = getRequest;
    window.$postRequest = postRequest;
    window.$deleteRequest = deleteRequest
    window.$openNewTab = openNewTab
}

app.use(router)
app.use(store)
app.use(plugins)
app.use(elementIcons)
app.use(c7Plus)
app.component('svg-icon', SvgIcon)


// const requestFn = ref(getRequest);
//
// app.provide("getRequest",requestFn)
// app.provide("getDictByType",getDictByType)
// app.provide("deleteRequest",deleteRequest)
//
// let servicePath = '../../service/provideSerivce.js';
// app.provide("servicePath", servicePath)
//
// app.provide("getRequest", getRequest);
// directive(app)

// 使用element-plus 并且设置全局的大小
app.use(ElementPlus, {
    locale: locale,
    // 支持 large、default、small
    size: Cookies.get('size') || 'default'
})

app.mount('#app')
