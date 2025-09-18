<template>
    <div class="app-container">
        <qtable-search :columns="searchColumns" :modelValue="state.dataForm" @handle-reset="state.handleReset"
                       @handle-search="state.getDataList"
                       @add-btn-handle="addOrUpdateHandle()"
                       @deleteHandle="state.deleteHandle"
                                    add-btn-perms="system:sysuser:add"
                    del-btn-perms="system:sysuser:remove"

        ></qtable-search>
        <qtable v-loading="state.dataListLoading" :tableData="state.dataList" :columns="jsonColumns"
                :page="state.page" :limit="state.limit" :total="state.total"
                @pageSizeChangeHandle="state.pageSizeChangeHandle"
                @pageCurrentChangeHandle="state.pageCurrentChangeHandle"
                @selection-change="state.dataListSelectionChangeHandle" :table-props={selection:true}>
            <!-- 自定义列, 可以通过 order 配置列的顺序 -->
            <el-table-column label="操作" order="99" width="150px">
                <template #default="scope">
                    <el-tooltip content="修改" placement="top">
                        <el-button link type="primary" icon="Edit" @click="addOrUpdateHandle(scope.row.id)"
                                   v-hasPermi="['system:sysuser:edit']"
                        >编辑
                        </el-button>
                    </el-tooltip>
                    <el-tooltip content="删除" placement="top">
                        <el-button link type="primary" icon="Delete" @click="state.deleteHandle(scope.row.id)"
                                   v-hasPermi="['system:sysuser:remove']"
                        >删除
                        </el-button>
                    </el-tooltip>
                </template>
            </el-table-column>
        </qtable>

        <!-- 弹窗, 新增 / 修改 -->
        <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="state.getDataList"></add-or-update>


    </div>
</template>


<script setup name="sysuser">
    import tableView from "@/hooks/tableView";
    import {reactive, ref, toRefs} from "vue";

    import AddOrUpdate from "./add-or-update.vue";

    const view = reactive({
        getDataListURL: "/system/sysuser/list",
        getDataListIsPage: true,
        deleteURL: "/system/sysuser",
        deleteIsBatch: true,
        exportURL: "/system/sysuser/export",
        dataForm: {}
    });
    const {proxy} = getCurrentInstance();

    const state = reactive({...tableView(view), ...toRefs(view)});


    // 列表字段配置
    const jsonColumns = ref([

    ]);

    // 搜索字段配置
    const searchColumns = ref([


    ]);

    const addKey = ref(0);
    const addOrUpdateRef = ref();
    const addOrUpdateHandle = (id) => {

        addKey.value++;
        nextTick(() => {
            addOrUpdateRef.value.init(id);
        });
    };
</script>