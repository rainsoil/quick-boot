<template>
  <div class="app-container">
    <qtable-search :columns="searchColumns" :modelValue="state.dataForm" @handle-reset="state.handleReset"
                   @handle-search="state.getDataList"

                   @add-btn-handle="addOrUpdateHandle()"
                   @deleteHandle="state.deleteHandle"
                   :add-btn="false"
                   :del-btn="false"

    ></qtable-search>
    <qtable v-loading="state.dataListLoading" :tableData="state.dataList" :columns="jsonColumns"
            :selection="false"
            :page="state.page" :limit="state.limit" :total="state.total"
            @pageSizeChangeHandle="state.pageSizeChangeHandle"
            @pageCurrentChangeHandle="state.pageCurrentChangeHandle"
            @selection-change="state.dataListSelectionChangeHandle" >
      <!-- 自定义列, 可以通过 order 配置列的顺序 -->
      <el-table-column label="操作" order="99" width="80px">
        <template #default="scope">
          <el-tooltip content="查看" placement="top">
            <el-button link type="primary" icon="Edit" @click="addOrUpdateHandle(scope.row.id)"
                       v-hasPermi="['system:logininfor:query']">查看
            </el-button>
          </el-tooltip>

        </template>
      </el-table-column>
    </qtable>

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="state.getDataList"></add-or-update>


  </div>
</template>


<script setup name="syslogininfor">
import tableView from "@/hooks/tableView";
import {reactive, ref, toRefs} from "vue";

import AddOrUpdate from "./add-or-update.vue";

const view = reactive({
  getDataListURL: "/system/syslogininfor/list",
  getDataListIsPage: true,
  deleteURL: "/system/syslogininfor",
  deleteIsBatch: true,
  exportURL: "/system/syslogininfor/export",
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
    label: "登录IP地址",
    prop: "ipaddr",

  },

  {
    label: "客户端",
    prop: "clientId",

  },
  {
    label: "登录地点",
    prop: "loginLocation",

  },
  {
    label: "浏览器类型",
    prop: "browser",

  },
  {
    label: "操作系统",
    prop: "os",

  },
  {
    label: "登录时间",
    prop: "loginTime",
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
    label: "登录IP地址",
    prop: "ipaddr",
    type: "input",
    placeholder: "请输入登录IP地址"
  },


  {
    label: "登录时间",
    prop: "loginTimes",
    type: "daterange",
    placeholder: "请选择登录时间",
    rangeSeparator: "至",
    startPlaceholder: "开始日期",
    endPlaceholder: "结束日期",
    valueFormat: "YYYY-MM-DD"

  }


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