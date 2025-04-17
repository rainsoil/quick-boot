import {IViewHooks, IViewHooksOptions} from "../types/ITableHook";
import {onActivated, onMounted} from "vue";
import {injectService} from "../service/injectService";
import {ElMessage, ElMessageBox} from "element-plus";
import {IObject} from "../types/ITypes";
import {dictHook} from "./dictHook";

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

    /**
     * 配置合并
     * @param options
     * @param props
     */
    const mergeDefaultStateToPageState = (options: IObject, props: IObject): IViewHooksOptions => {
        if (!options || !props) {
            throw new Error("options and props must be valid objects");
        }

        for (const key in options) {
            if (!props.hasOwnProperty(key)) {
                props[key] = options[key];
            }
        }
        return props;
    };
    const state = mergeDefaultStateToPageState(defaultOptions, props);
    onMounted(() => {
        if (state.createdIsNeed && !state.activatedIsNeed) {
            viewFns.query();
        }
    });
    onActivated(() => {
        if (state.activatedIsNeed) {
            viewFns.query();
        }
    })
    const rejectFns = {
        getDictLabel(dictType: string, dictValue: string) {
            if (!dictType || !dictValue) {
                return "";
            }
            return dictHook().getDictByValue(dictType, dictValue);
        }
    };

    const viewFns = {

        // 获取数据列表
        query() {
            if (!state.getDataListURL) {
                console.warn('getDataListURL is undefined or null, request not sent.');
                return;
            }
            state.dataListLoading = true;
            // 请求
            injectService.getRequest(state.getDataListURL, {
                // 排序参数
                order: state.order,
                // 排序字段
                orderField: state.orderField,
                page: state.getDataListIsPage ? state.page : null,
                limit: state.getDataListIsPage ? state.limit : null,
                ...state.dataForm
            }).then(res => {
                state.dataListLoading = false;
                state.dataList = state.getDataListIsPage ? res.data.records : res.data;
                state.total = state.getDataListIsPage ? res.data.total : (Array.isArray(res.data) ? res.data.length : 0);
            }).catch(error => {
                state.dataListLoading = false;
                console.error('Error occurred during data list query:', error);
            })
        },
        // 多选
        dataListSelectionChangeHandle(val: IObject[]) {
            state.dataListSelections = val;
            console.log(state.dataListSelections)
            state.dataListSelectionsIds = state.dataListSelections
                ? state.dataListSelections.map(
                    (item: IObject) => state.deleteIsBatchKey && item[state.deleteIsBatchKey]
                )
                : [];
        },
        // 排序
        dataListSortChangeHandle(sort: IObject) {
            if (!sort.order || !sort.prop) {
                state.order = "";
                state.orderField = "";
                return false;
            }
            state.order = sort.order.replace(/ending$/, "");
            state.orderField = sort.prop.replace(/([A-Z])/g, "_$1").toLowerCase();
            viewFns.query();
        },
        // 分页
        pageSizeChangeHandle(val: number) {
            state.page = 1;
            state.limit = val;
            viewFns.query();
        },
        // 分页(当前页)
        pageCurrentChangeHandle(val: number) {
            state.page = val;
            viewFns.query();
        },
        // 搜索
        getDataList() {

            state.page = 1;
            viewFns.query();
        },
        //重置
        handleReset(param?: IObject) {
            state.dataForm = {}
            Object.assign(state.dataForm, param)
            state.page = 1;
            viewFns.query();
        },
        // 删除
        deleteHandle(id?: string): Promise<any> {
            return new Promise((resolve, reject) => {
                console.log(state.deleteIsBatch, id, !id, state.dataListSelections, state.dataListSelections.length <= 0)
                if (
                    state.deleteIsBatch &&
                    !id &&
                    state.dataListSelections &&
                    state.dataListSelections.length <= 0
                ) {
                    ElMessage.warning({
                        message: '请选择操作项',
                        duration: 500
                    });
                    return;
                }

                console.log(state.dataListSelectionsIds)
                ElMessageBox.confirm("确定进行删除操作?", "提示", {
                    confirmButtonText: "确认",
                    cancelButtonText: "取消",
                    type: "warning"
                })
                    .then(() => {
                        injectService
                            .deleteRequest(
                                `${state.deleteURL}${state.deleteIsBatch ? "" : "/" + id}`,
                                state.deleteIsBatch
                                    ? id
                                        ? [id]
                                        : state.dataListSelectionsIds
                                    : {}
                            )
                            .then((res) => {
                                ElMessage.success({
                                    message: "成功",
                                    duration: 500,
                                    onClose: () => {
                                        viewFns.query();
                                        resolve(true);
                                    }
                                });
                            });
                    })
                    .catch(() => {
                        //
                    });
            });

        },
        // 导出
        exportHandle() {
            injectService.download(state.exportURL, {
                ...state.dataForm
            }, "", {})
        },

    }
    return {
        ...rejectFns,
        ...viewFns,

    }
}

