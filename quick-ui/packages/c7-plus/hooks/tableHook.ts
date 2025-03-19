import {IViewHooks, IViewHooksOptions} from "../types/ITableHook";
import {computed, onActivated, onMounted, ref} from "vue";
import {IObject} from "../types/ITypes";
import {injectService} from "../service/injectService";
import {ElMessage, ElMessageBox} from "element-plus";

export const tableHook = (props: IViewHooksOptions | IObject): IViewHooks => {

    // 默认的配置
    const defaultOptions: IViewHooksOptions = {
        /**
         * 此页面是否在创建时，调用查询数据列表接口？
         */
        createdIsNeed: true,
        /**
         * 此页面是否在激活（进入）时，调用查询数据列表接口？
         */
        activatedIsNeed: false,
        /**
         * 数据列表接口，API地址
         */
        getDataListURL: '',


        /**
         * 数据列表接口，是否需要分页？
         */
        getDataListIsPage: true,
        /**
         * 删除接口，API地址
         */
        deleteURL: "",
        /**
         * 删除接口，是否需要批量？
         */
        deleteIsBatch: true,
        /**
         * 删除接口，批量状态下由那个key进行标记操作？比如：pid，uid...
         */
        deleteIsBatchKey: 'id',
        /**
         * 导出接口，API地址
         */
        exportURL: '',

        /**
         * 查询条件
         */
        dataForm: {},
        /**
         * 数据列表
         */
        dataList: [],
        /**
         * 排序，asc／desc
         */
        order: '',
        /**
         * 排序，字段
         */
        orderField: '',
        /**
         * 当前页码
         */
        page: 1,
        /**
         * 每页数
         */
        limit: 10,
        /**
         * 总条数
         */
        total: 0,
        /**
         * 数据列表，loading状态
         */
        dataListLoading: false,
        /**
         * 数据列表，多选项
         */
        dataListSelections: [],
        /**
         * 多选的id集合
         */
        dataListSelectionsIds: []
    }


}

