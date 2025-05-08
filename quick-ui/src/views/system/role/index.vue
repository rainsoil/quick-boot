<template>
  <div class="app-container">
    <c7-table-search :columns="searchColumns" ref="searchRef" v-model="searchParam"
                     @handleSearch="tableRef.getDataList()" @handleReset="tableRef.handleReset()"></c7-table-search>
    <c7-table :tableProps="tableProps" :columns="jsonColumns" ref="tableRef" :tableParam="searchParam"
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
import AddOrUpdate from "./add-or-update.vue";

// 搜索
const searchParam = ref({});
// 搜索字段
const searchColumns = ref([
  /**
   {
   prop: "roleName",
   label: "角色名称",

   },
   {
   prop: "roleKey",
   label: "权限字符",

   },
   */
  {
    prop: "status",
    label: "状态",
    type: 'select',
    dictType: "sys_normal_disable",
    // order: 5, // 显示顺序为第一列
  },


]);


// 列表
const tableRef = ref();
const tableProps = reactive({
  getDataListURL: "/system/role/list",
  getDataListIsPage: true,
  deleteURL: "/system/role",
  deleteIsBatch: true

})

// 列表字段配置
const jsonColumns = ref([

  {
    prop: "roleName",
    label: "角色名称",
    "show-overflow-tooltip": true,
  },
  {
    prop: "roleKey",
    label: "权限字符",
    "show-overflow-tooltip": true,
  }, {
    prop: "roleSort",
    label: "显示顺序",

  },
  {
    prop: "status",
    label: "状态",
    "show-overflow-tooltip": true,
    type: 'dict',
    dictType: "sys_normal_disable",
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
</script>
