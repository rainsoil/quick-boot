<template>
  <div class="app-container">
    <qtable-search :columns="searchColumns" :modelValue="state.dataForm" @handle-reset="state.handleReset"
                   @handle-search="state.getDataList"

                   @add-btn-handle="addOrUpdateHandle()"
                   @deleteHandle="state.deleteHandle"
                   add-btn-perms="system:dict:add"
                   del-btn-perms="system:dict:remove"
    >


    </qtable-search>
    <qtable v-loading="state.dataListLoading" :tableData="state.dataList" :columns="jsonColumns"
            :page="state.page" :limit="state.limit" :total="state.total"
            @pageSizeChangeHandle="state.pageSizeChangeHandle" @pageCurrentChangeHandle="state.pageCurrentChangeHandle"
            @selection-change="state.dataListSelectionChangeHandle" selection:true>
      <el-table-column label="操作" order="99" width="150px">
        <template #default="scope">
          <el-tooltip content="修改" placement="top" >
            <el-button link type="primary" icon="Edit" @click="addOrUpdateHandle(scope.row.id)"
                       v-hasPermi="['system:dict:edit']">修改</el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top" >
            <el-button link type="primary" icon="Delete" @click="state.deleteHandle(scope.row.id)"
                       v-hasPermi="['system:dict:remove']">删除</el-button>
          </el-tooltip>
        </template>
      </el-table-column>


    </qtable>


    <data-add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="state.getDataList"></data-add-or-update>
  </div>
</template>


<script setup>
import tableView from "@/hooks/tableView";

import {reactive, ref, toRefs} from "vue";
import dataAddOrUpdate from "@/views/system/dict/data-add-or-update.vue";
const route = useRoute();
const view = reactive({
  getDataListURL: "/system/dict/data/page",
  getDataListIsPage: true,
  deleteURL: "/system/dict/data",
  deleteIsBatch: true,
  exportURL: "",
  dataForm: {
    dictType: route.params.dictType,

  }
});
const state = reactive({...tableView(view), ...toRefs(view)});
const jsonColumns = ref([
  {
    prop: "dictType",
    label: "字典类型"

  },
  {
    prop: "dictLabel",
    label: "字典标签"

  },
  {
    prop: "dictValue",
    label: "字典键值"
  },
  {
    prop: "dictSort",
    label: "字典排序"
  },
  {
    prop: "status",
    label: "状态",

    dictType: "sys_normal_disable"
  },
  {
    prop: "remark",
    label: "备注",
  },
  {
    prop: "createTime",
    label: "创建时间",
  },
]);
const searchColumns = ref([
  {
    label: "字典标签",
    prop: "dictLabel",


  },
  {
    label: "字典键值",
    prop: "dictValue",
  },
  {
    prop: "status",
    label: "状态",
    type: 'dict',
    dictType: "sys_normal_disable"
  }
])

const addKey = ref(0);
const addOrUpdateRef = ref();
const addOrUpdateHandle = (id) => {

  addKey.value++;
  nextTick(() => {
    addOrUpdateRef.value.init(route.params.dictType,id);
  });
};
</script>