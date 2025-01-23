//  c7组件需要的接口类
import router from '@/router'
import BaseService from "@/service/baseService.js";

export default {

    /**
     * 打开新的tab
     * @param url 地址
     * @param title 标题
     */
    openNewTab(url, title) {
        router.push(url)
    },
    /**
     * 删除
     * @param path
     * @param params
     * @returns
     */
    delete(path, params) {
        return BaseService.delete({
            url: path,
            data: params,
            method: "DELETE"
        });
    },
    get(path, params, headers) {
        return BaseService.get(path, params, headers);
    },

    getDow(path, params, headers) {
        return BaseService.getDow(path, params, headers)
    },

    put(path, params, headers) {
        return BaseService.put(path, params, headers)
    },

    /**
     * 通用post方法
     * @param path
     * @param body
     * @returns
     */
    post(path, body, headers) {
        return BaseService.post(path, body, headers)
    },

    /**
     * 用于文件上传的post方法
     * @param path
     * @param file
     * @returns
     */
    postfile(path, file, headers) {

        return BaseService.postfile(path, file, headers);
    }

}
