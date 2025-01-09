<template>
  <div class="app-container">
    <qtable-search :columns="searchColumns" :modelValue="state.dataForm" @handle-reset="state.handleReset"
                   @handle-search="state.getDataList"

                   @add-btn-handle="addOrUpdateHandle()"
                   add-btn-perms="system:user:add"
                   del-btn-perms="system:user:remove"
                   @deleteHandle="state.deleteHandle"
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
                       v-hasPermi="['system:user:edit','system:user:query']">修改
            </el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button link type="primary" icon="Delete" @click="state.deleteHandle(scope.row.id)"
                       v-hasPermi="['system:user:remove']">删除
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
  getDataListURL: "/sys/user/page",
  getDataListIsPage: true,
  deleteURL: "/sys/user",
  deleteIsBatch: true,
  exportURL: "/system/sysuser/export",
  dataForm: {}
});
const {proxy} = getCurrentInstance();

const state = reactive({...tableView(view), ...toRefs(view)});


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

  // {
  //   label: "用户性别",
  //   prop: "sex",
  //   dictType: "sys_user_sex",
  //
  // },


  {
    label: "帐号状态",
    prop: "status",
    dictType: "COMMON_STATUS",

  },


  {
    label: "最后登录时间",
    prop: "loginDate",

  },


]);

// 搜索字段配置
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
    type: "dict",
    placeholder: "请输入帐号状态"
  },


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