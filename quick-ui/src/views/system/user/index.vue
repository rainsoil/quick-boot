<template>
  <div class="app-container">
    <!--    <c7-table-search :columns="searchColumns" ref="searchRef" v-model="searchParam"-->
    <!--                     @handleSearch="tableRef.getDataList()" @handleReset="tableRef.handleReset()"></c7-table-search>-->
    <!--    <c7-table :tableProps="tableProps" :columns="jsonColumns" ref="tableRef" :tableParam="searchParam"-->
    <!--              :selection="true" @addBtnHandle="addBtnHandle">-->
    <!--      <template #operate="scope">-->
    <!--        <el-button link type="primary" icon="Edit" @click="addBtnHandle(scope.row.id)"-->
    <!--                   v-hasPermi="['system:user:edit','system:user:query']">修改-->
    <!--        </el-button>-->
    <!--        <el-button link type="primary" icon="Delete" @click="tableRef.deleteBtnHandle(scope.row.id)"-->
    <!--                   v-hasPermi="['system:user:remove']">删除-->
    <!--        </el-button>-->
    <!--      </template>-->
    <!--    </c7-table>-->
    <!--    &lt;!&ndash; 弹窗, 新增 / 修改 &ndash;&gt;-->
    <!--    <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="tableRef.getDataList()"></add-or-update>-->
    <c7-json-table :listFunction="param =>listUser(param)" :tableColumns="tableColumns" :searchColumns="searchColumns"
                   rowsKey="data.records" totalKey="data.total"></c7-json-table>
  </div>


</template>


<script setup>
import {c7JsonTable} from "c7-plus";
import {reactive, ref, toRefs} from "vue";
import AddOrUpdate from "./add-or-update.vue";
import {listUser} from '@/api/system/user.js'

const {proxy} = getCurrentInstance();
const {sys_normal_disable, sys_user_sex} = proxy.useDict("sys_normal_disable", "sys_user_sex");

console.log(ref(sys_normal_disable))
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
    dataList: sys_normal_disable,
    type: "select",
    placeholder: "请输入帐号状态"
  },


]);


// 列表字段配置
const tableColumns = ref([

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
    columnType: 'tag',
    dictList: sys_normal_disable,
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
