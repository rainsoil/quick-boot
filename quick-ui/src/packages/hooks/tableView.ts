import {IObject, IViewHooks, IViewHooksOptions} from "./interface";
import {onActivated, onMounted} from "vue";
import baseService from "../service/injectService";
import {ElMessage, ElMessageBox} from "element-plus";
import {inject} from "vue";

const tableView = (props: IViewHooksOptions | IObject): IViewHooks => {
        const defaultOptions: IViewHooksOptions = {
            createdIsNeed: true,
            activatedIsNeed: false,
            getDataListURL: "",
            getDataListIsPage: false,
            deleteURL: "",
            deleteIsBatch: false,
            deleteIsBatchKey: "id",
            exportURL: "",
            dataForm: {},
            dataList: [],
            order: "",
            orderField: "",
            page: 1,
            limit: 10,
            total: 0,
            dataListLoading: false,
            dataListSelections: [],
            elTable: {},
            dataListSelectionsIds: []
        };
        const mergeDefaultStateToPageState = (options: IObject, props: IObject): IViewHooksOptions => {
            for (const key in options) {
                if (!Object.getOwnPropertyDescriptor(props, key)) {
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

                if (null == dictType || undefined == dictType || null == dictValue || undefined == dictValue) {
                    return "";
                }
                // let dict = baseService.getDictLabel(dictType, dictValue);
                let dict = null;
                return dict == null ? dictValue : dict.dictLabel;
            }
        };

        const viewFns = {
            // 获取数据列表
            query() {
                if (!state.getDataListURL) {
                    return;
                }
                state.dataListLoading = true;
                baseService.getRequest(state.getDataListURL, {
                    order: state.order,
                    orderField: state.orderField,
                    page: state.getDataListIsPage ? state.page : null,
                    limit: state.getDataListIsPage ? state.limit : null,
                    ...state.dataForm
                }).then((res) => {
                    state.dataListLoading = false;
                    state.dataList = state.getDataListIsPage ? res.data.records : res.data;
                    state.total = state.getDataListIsPage ? res.data.total : 0;
                }).catch((error) => {
                    state.dataListLoading = false;
                })
            },
            // 多选
            dataListSelectionChangeHandle(val: IObject[]) {
                console.log("dataListSelectionChangeHandle", val)
                state.dataListSelections = val;
                state.dataListSelectionsIds = state.dataListSelections
                    ? state.dataListSelections.map(
                        (item: IObject) => state.deleteIsBatchKey && item[state.deleteIsBatchKey]
                    )
                    : [];
                console.log(state.dataListSelectionsIds)
            },
            // 排序
            dataListSortChangeHandle(data: IObject) {
                if (!data.order || !data.prop) {
                    state.order = "";
                    state.orderField = "";
                    return false;
                }
                state.order = data.order.replace(/ending$/, "");
                state.orderField = data.prop.replace(/([A-Z])/g, "_$1").toLowerCase();
                viewFns.query();
            },
            // 分页
            pageSizeChangeHandle(val: number) {
                console.log(123)
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
            handleReset(param: IObject) {
                state.dataForm = {}
                Object.assign(state.dataForm, param)
                state.page = 1;
                viewFns.query();
            },
            // 删除
            deleteHandle(id?: string): Promise<any> {
                return new Promise((resolve, reject) => {
                    console.log("id", id);
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

                    ElMessageBox.confirm("确定进行删除操作?", "提示", {
                        confirmButtonText: "确认",
                        cancelButtonText: "取消",
                        type: "warning"
                    })
                        .then(() => {
                            baseService
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
                // download(state.exportURL, {
                //     ...state.dataForm
                // })
                // window.location.href = `${app.api}${state.exportURL}?${qs.stringify({
                //     ...state.dataForm,
                //     token: getToken()
                // })}`;
            }
            ,
        };

        return {
            ...viewFns,
            ...rejectFns
        };
    }
;
export default tableView;
