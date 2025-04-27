<template>
  <div class="app-container">
    <c7-table-search :columns="searchColumns" ref="searchRef" v-model="searchParam"
                     @handleSearch="tableRef.getDataList()" @handleReset="tableRef.handleReset()"></c7-table-search>
    <c7-table :tableProps="tableProps" :columns="jsonColumns" ref="tableRef" :tableParam="searchParam"
              :buttons="buttons"
              :selection="false" @addBtnHandle="addBtnHandle">

      <template #status="{item}">
        <span :style="item.status !=200 ? 'color: red':''">{{ item.status }}</span>
      </template>
      <template #operate="scope">
        <el-button link type="primary" icon="View" @click="addBtnHandle(scope.row.id)"
                   v-hasPermi="['system:operlog:query']">查看
        </el-button>
      </template>
    </c7-table>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="tableRef.getDataList()"></add-or-update>
  </div>


</template>


<script setup lang="ts">
import {reactive, ref, nextTick} from "vue";

import AddOrUpdate from "./add-or-update.vue";

// 按钮配置
const buttons = ref({
  enable: false,
})
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


// 列表
const tableRef = ref();
const tableProps = reactive({
  getDataListURL: "/system/syslogininfor/list",
  getDataListIsPage: true,
  deleteURL: "/system/syslogininfor",
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
const addKey = ref(0);
const addOrUpdateRef = ref();
const addBtnHandle = (id) => {
  addKey.value++;
  nextTick(() => {
    addOrUpdateRef.value.init(id);
  });
}

</script>

<style scoped>


</style>

