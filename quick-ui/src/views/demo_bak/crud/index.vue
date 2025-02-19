<template>
  <div class="app-container">
    <qtable-search :columns="searchColumns" :modelValue="state.dataForm" @handle-reset="state.handleReset"
                   @handle-search="state.getDataList"
                   @add-btn-handle="addOrUpdateHandle()"
                   @deleteHandle="state.deleteHandle"
                   add-btn-perms="demo:crud:add"
                   del-btn-perms="demo:crud:remove"

    >
    </qtable-search>
    <qtable v-loading="state.dataListLoading" :tableData="state.dataList" :columns="jsonColumns"
            :page="state.page" :limit="state.limit" :total="state.total"
            @pageSizeChangeHandle="state.pageSizeChangeHandle" @pageCurrentChangeHandle="state.pageCurrentChangeHandle"
            @selection-change="state.dataListSelectionChangeHandle" selection:true>
      <el-table-column label="操作" order="99" width="150px">
        <template #default="scope">
          <el-tooltip content="修改" placement="top">
            <el-button link type="primary" icon="Edit" @click="addOrUpdateHandle(scope.row.id)"
                       v-hasPermi="['demo:crud:edit']">修改
            </el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button link type="primary" icon="Delete" @click="state.deleteHandle(scope.row.id)"
                       v-hasPermi="['demo:crud:remove']">删除
            </el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </qtable>
    <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="state.getDataList"></add-or-update>
  </div>
</template>


<script setup>
import tableView from "@/hooks/tableView";

import {reactive, ref, toRefs} from "vue";
import AddOrUpdate from "./add-or-update.vue";

const route = useRoute();
const view = reactive({
  getDataListURL: "/demo/crud/list",
  getDataListIsPage: true,
  deleteURL: "/demo/crud",
  deleteIsBatch: true,
  exportURL: "",
  dataForm: {
    dictType: route.params.dictType,

  }
});
const state = reactive({...tableView(view), ...toRefs(view)});
const jsonColumns = ref([
  {
    label: "input",
    prop: "input",
  },
  {
    label: "inputNumber",
    prop: "inputNumber",
  },
  {
    label: "radio",
    prop: "radio",
    dictType: 'sys_common_status'
  },
  {
    label: "select",
    prop: "select",
  },
  {
    label: "remoteSelect",
    prop: "remoteSelect",
  },
  {
    label: "tree",
    prop: "tree",
  },
  {
    label: "image",
    prop: "image",
    type: 'file',
    showButton: true,
    placeholder: "查看"


  },
  {
    label: "file",
    prop: "file",
    type: 'file',
    showButton: true,
    placeholder: "查看"
  },
  {
    label: "datetime",
    prop: "datetime",
  },
  {
    label: "checkBox",
    prop: "checkBox",
  },
  {
    label: "dataPicker",
    prop: "dataPicker",
  },
  {
    label: "dataTimePicker",
    prop: "dataTimePicker",
  },
  // {
  //   prop: "dictType",
  //   label: "字典类型"
  //
  // },
  // {
  //   prop: "status",
  //   label: "状态",
  //   dictType: "sys_normal_disable"
  // },

]);
const searchColumns = ref([

  {
    label: "input",
    prop: "input",
  },
  // {
  //   prop: "status",
  //   label: "状态",
  //   type: 'dict',
  //   dictType: "sys_normal_disable"
  // }
])

const addKey = ref(0);
const addOrUpdateRef = ref();
const addOrUpdateHandle = (id) => {
  addKey.value++;
  nextTick(() => {
    addOrUpdateRef.value.init(id);
  });
};
</script>