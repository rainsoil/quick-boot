import axios from 'axios'
import {ElNotification, ElMessageBox, ElMessage, ElLoading} from 'element-plus'

// 是否显示重新登录

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const service = axios.create({
    // axios中请求配置有baseURL选项，表示请求URL公共部分

    baseURL:  'https://m1.apifoxmock.com/m1/4742948-4395863-default/',
    // 超时
    timeout: 10000
})

// request拦截器
service.interceptors.request.use(config => {


    return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {

        return Promise.resolve(res.data)

    },
    error => {

        return Promise.reject(error)
    }
)

export default service
