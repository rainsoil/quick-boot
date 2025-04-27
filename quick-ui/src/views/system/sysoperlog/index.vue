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
    label: "方法名称",
    prop: "method",
    type: "input",
    placeholder: "请输入方法名称"
  },


  {
    label: "操作人员",
    prop: "operName",
    type: "input",
    placeholder: "请输入操作人员"
  },


  {
    label: "请求URL",
    prop: "operUrl",
    type: "input",
    placeholder: "请输入请求URL"
  },


  {
    label: "主机地址",
    prop: "operIp",
    type: "input",
    placeholder: "请输入主机地址"
  },
  {
    label: "状态",
    prop: "status",
    type: "dict",
    dictType: "sys_common_status",
  },

  {
    label: "操作时间",
    prop: "operTimes",
    type: "daterange",
    placeholder: "请选择操作时间",
    rangeSeparator: "至",
    startPlaceholder: "开始日期",
    endPlaceholder: "结束日期",
    valueFormat: "YYYY-MM-DD"

  }

]);


// 列表
const tableRef = ref();
const tableProps = reactive({
  getDataListURL: "/system/sysoperlog/list",
  getDataListIsPage: true,
  deleteURL: "/system/sysoperlog",
  deleteIsBatch: true,
  dataForm: searchParam

})

// 列表字段配置
const jsonColumns = ref([

  {
    label: "方法名称",
    prop: "method",
    showOverflowTooltip: true,
  },

  {
    label: "操作人员",
    prop: "operName",

    width: "120px",
  },


  {
    label: "请求URL",
    prop: "operUrl",
    showOverflowTooltip: true,
    formatter: (item, row) => {
      return row.requestMethod + " " + row.operUrl;
    }

  },


  {
    label: "主机地址",
    prop: "operIp",
    width: "130px",

  }
  ,

  {
    label: "状态码",
    prop: "status",
    width: "80px",
    isSolt: true
  },
  {
    label: "操作时间",
    prop: "operTime",
    width: "170px",

  },
  {
    label: "耗时",
    prop: "costTime",
    width: "60px",
  }

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

