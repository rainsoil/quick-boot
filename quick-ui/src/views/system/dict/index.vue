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
            @selection-change="state.dataListSelectionChangeHandle" >
      <el-table-column label="操作" order="99">
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
      <template #dictType="{item}">
        <router-link :to="'/system/dict-data/index/' + item.dictType" class="link-type">
          <span>{{ item.dictType }}</span>
        </router-link>
<!--        {{item.dictName}}-->
<!--        <input :value="item.prop"/>-->
      </template>

    </qtable>


    <dict-add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="state.getDataList"></dict-add-or-update>
  </div>
</template>


<script setup>
import tableView from "@/hooks/tableView";

import {reactive, ref, toRefs} from "vue";
import DictAddOrUpdate from "@/views/system/dict/dict-add-or-update.vue";

const view = reactive({
  getDataListURL: "/system/dict/type/page",
  getDataListIsPage: true,
  deleteURL: "/system/dict/type",
  deleteIsBatch: true,
  exportURL: "/system/dict/export",
  dataForm: {
    dictName: "",
    dictType: "",
    status: "",
    createTime: "",
    startTime: "",
    endTime: "",

  }
});
const state = reactive({...tableView(view), ...toRefs(view)});
const jsonColumns = ref([
  {
    prop: "dictName",
    label: "字典名称",

  },
  {
    prop: "dictType",
    label: "字典类型",
    isSolt: true
  },
  {
    prop: "status",
    label: "状态",
    dictType: "sys_normal_disable"
  },

  {
    prop: "remark",
    label: "备注",
    "show-overflow-tooltip": true,
  },
  {
    prop: "createTime",
    label: "创建时间",
  },
]);
const searchColumns = ref([
    {
      label: '字典名称',
      prop: 'dictName',
      type: 'input',
      // columnsWidth: "80px"
    },
  {
    label: '字典类型',
    prop: 'dictType',
    type: 'input',
  },
  {
    prop: "status",
    label: "状态",
    type: 'dict',
    dictType: "sys_normal_disable"
  },
  // {
  //   prop: "createTime",
  //   label: "创建时间",
  //   type: 'date',
  //   format: 'YYYY/MM/DD',
  //   valueFormat: "YYYY-MM-DD",
  // },
  // {
  //   prop: "createTime2",
  //   label: "创建时间区间",
  //   defaultValue: '',
  //   format: 'YYYY/MM/DD',
  //   valueFormat: "YYYY-MM-DD",
  //   type: 'daterange',
  //   formatter: (value) => {
  //
  //     // console.log(value)
  //     if (value.length > 1) {
  //       state.dataForm.startTime = value[0];
  //       state.dataForm.endTime = value[1];
  //
  //     }
  //
  //   }
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