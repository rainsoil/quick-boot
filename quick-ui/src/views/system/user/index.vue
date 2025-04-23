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

  {
    label: "用户账号",
    prop: "userName",
    type: "input",
    placeholder: "请输入用户账号"
  },


  {
    label: "用户昵称",
    prop: "nickName",
    type: "input",
    placeholder: "请输入用户昵称"
  },


  {
    label: "帐号状态",
    prop: "status",
    dictType: "COMMON_STATUS",
    type: "select",
    placeholder: "请输入帐号状态"
  },


]);


// 列表
const tableRef = ref();
const tableProps = reactive({
  getDataListURL: "/sys/user/page",
  getDataListIsPage: true,
  deleteURL: "/sys/user",
  deleteIsBatch: true,
  dataForm: searchParam

})

// 列表字段配置
const jsonColumns = ref([

  {
    label: "用户账号",
    prop: "userName",

  },


  {
    label: "用户昵称",
    prop: "nickName",

  },


  {
    label: "用户邮箱",
    prop: "email",

  },


  {
    label: "手机号码",
    prop: "phonenumber",

  },
  {
    label: "角色",
    prop: "roleNames",

  },


  {
    label: "帐号状态",
    prop: "status",
    type: 'dict',
    dictType: "COMMON_STATUS",

  },


  {
    label: "最后登录时间",
    prop: "loginDate",

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
<style scoped lang="scss">

</style>
