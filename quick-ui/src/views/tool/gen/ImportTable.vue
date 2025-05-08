<!--导入表 -->
<template>

  <c7-dialog :visible="visibleRef" mode="dialog" title="导入表" @close="visibleRef = false"
             :footer="true">
    <div class="app-container">
      <c7-table-search :columns="searchColumns" ref="searchRef" v-model="searchParam"
                       @handleSearch="tableRef.getDataList()" @handleReset="tableRef.handleReset()"></c7-table-search>
      <c7-table :tableProps="tableProps" :columns="jsonColumns" ref="tableRef" :tableParam="searchParam"
                :buttons="buttons"
                :selection="true">
        <template #appendButton>
          <c7-button label="导入" type="primary" @click="batchImport" :clickEvent="true">

          </c7-button>
        </template>
        <template #operate="scope">
        </template>
      </c7-table>

    </div>
  </c7-dialog>


</template>

<script setup>
import {c7Dialog, c7Table, c7TableSearch, c7Button} from 'c7-plus'

const visibleRef = ref(false);

import {reactive, ref, toRefs, nextTick} from "vue";
import baseService from "@/service/baseService.js";
const {proxy} = getCurrentInstance();
const emit = defineEmits(["refreshDataList"]);

// 搜索
const searchParam = ref({});
// 搜索字段
const searchColumns = ref([
  {
    prop: "tableName",
    label: "表名称"

  },

  {
    prop: "tableComment",
    label: "表注释"

  },
  /**
   {
   prop: "roleKey",
   label: "权限字符",

   },
   {
   prop: "status",
   label: "状态",
   type: 'select',
   dictType: "sys_normal_disable",

   },

   */
]);


// 列表
const tableRef = ref();
const tableProps = reactive({
  getDataListURL: "/generator/gentable/dbTables",
  getDataListIsPage: true,
  createdIsNeed: false,
  deleteIsBatchKey:"tableName"

})

// 列表字段配置
const jsonColumns = ref([
  {
    prop: "tableName",
    label: "表名称"

  },
  {
    prop: "tableComment",
    label: "表注释"

  },

]);

// 按钮
const buttons = ref({
  enable: true,
  addBtn: {
    enable: false,
  },
  deleteBtn: {
    enable: false,
  },
  exportBtn: {
    enable: false,
  },
})
const init = () => {
  visibleRef.value = true;
  nextTick(() => {
    tableRef.value.getDataList();
  })
}

const batchImport = () => {
  let ids = tableRef.value.dataListSelectionsIds();
  if (ids.length == 0) {
    proxy.$modal.msgError(`请先选中!`);
    return false;
  }
  baseService.post("/generator/gentable/importTable", ids).then(res => {
    proxy.$modal.msgSuccess("操作成功");
    visibleRef.value = false;
    emit("refreshDataList");
  })
}

defineExpose({
  init
})
</script>
<style scoped></style>
