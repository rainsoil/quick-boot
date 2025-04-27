<template>
  <div class="app-container">
    <c7-table-search :columns="searchColumns" ref="searchRef" v-model="searchParam"
                     @handleSearch="tableRef.getDataList()" @handleReset="tableRef.handleReset()"></c7-table-search>
    <c7-table :tableProps="tableProps" :columns="jsonColumns" ref="tableRef" :tableParam="searchParam"
              :initParam="initParam"
              :selection="true" @addBtnHandle="addBtnHandle">
      <template #operate="scope">
        <el-button link type="primary" icon="Edit" @click="addBtnHandle(scope.row.id)"
                   v-hasPermi="['system:user:edit','system:user:query']">修改
        </el-button>
        <el-button link type="primary" icon="Delete" @click="tableRef.deleteBtnHandle(scope.row.id)"
                   v-hasPermi="['system:user:remove']">删除
        </el-button>
      </template>
    </c7-table>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="tableRef.getDataList()"></add-or-update>
  </div>


</template>
<script setup>
import {c7Table, c7TableSearch} from "c7-plus";
import {reactive, ref, toRefs} from "vue";
import AddOrUpdate from "./data-add-or-update.vue";

// 搜索
const searchParam = ref({
  dictType: ''
});
// 搜索字段
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
    type: 'select',
    dictType: "sys_normal_disable"
  }

]);


// 列表
const tableRef = ref();
const tableProps = reactive({
  getDataListURL: "/system/dict/data/page",
  getDataListIsPage: true,
  deleteURL: "/system/dict/data",
  deleteIsBatch: true,
  createdIsNeed: false

})

// 列表字段配置
const jsonColumns = ref([

  {
    prop: "dictType",
    label: "字典类型"

  },
  {
    prop: "label",
    label: "字典标签"

  },
  {
    prop: "value",
    label: "字典键值"
  },
  {
    prop: "dictSort",
    label: "字典排序"
  },
  {
    prop: "status",
    label: "状态",
    type: 'dict',

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
const addKey = ref(0);
const addOrUpdateRef = ref();
const addBtnHandle = (id) => {
  addKey.value++;
  nextTick(() => {
    addOrUpdateRef.value.init(id);
  });
}

const initParam = ref({
  dictType: ''
})
const init = (dictType) => {
  console.log(dictType)
  // initParam.value.dictType = dictType;
  searchParam.value.dictType = dictType;

  tableRef.value.getDataList();
}

defineExpose({
  init
})
</script>

<style scoped>


</style>


