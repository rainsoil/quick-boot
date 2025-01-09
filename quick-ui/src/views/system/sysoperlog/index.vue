<template>
  <div class="app-container">
    <qtable-search :columns="searchColumns" :modelValue="state.dataForm" @handle-reset="state.handleReset"
                   @handle-search="state.getDataList"

                   @add-btn-handle="addOrUpdateHandle()"
                   @deleteHandle="state.deleteHandle"
                   :del-btn="false"
                   :add-btn="false"
    ></qtable-search>
    <qtable v-loading="state.dataListLoading" :tableData="state.dataList" :columns="jsonColumns"
            :page="state.page" :limit="state.limit" :total="state.total"
            @pageSizeChangeHandle="state.pageSizeChangeHandle"
            @pageCurrentChangeHandle="state.pageCurrentChangeHandle"
            @selection-change="state.dataListSelectionChangeHandle" :selection="false">

      <template #status="{item}">
        <span :style="item.status !=200 ? 'color: red':''">{{ item.status }}</span>
      </template>
      <!-- 自定义列, 可以通过 order 配置列的顺序 -->
      <el-table-column label="操作" order="99" width="80px">
        <template #default="scope">
          <el-tooltip content="查看" placement="top">
            <el-button link type="primary" icon="View" @click="addOrUpdateHandle(scope.row.id)"
                       v-hasPermi="['system:operlog:query']">查看
            </el-button>
          </el-tooltip>
          <!--          <el-tooltip content="删除" placement="top">-->
          <!--            <el-button link type="primary" icon="Delete" @click="state.deleteHandle(scope.row.id)"-->
          <!--                       v-hasPermi="['system:sysoperlog:remove']">删除-->
          <!--            </el-button>-->
          <!--          </el-tooltip>-->
        </template>
      </el-table-column>

    </qtable>

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="state.getDataList"></add-or-update>


  </div>
</template>


<script setup name="sysoperlog">
import tableView from "@/hooks/tableView";
import {reactive, ref, toRefs} from "vue";

import AddOrUpdate from "./add-or-update.vue";

const view = reactive({
  getDataListURL: "/system/sysoperlog/list",
  getDataListIsPage: true,
  deleteURL: "/system/sysoperlog",
  deleteIsBatch: true,
  exportURL: "/system/sysoperlog/export",
  dataForm: {}
});
const {proxy} = getCurrentInstance();

const state = reactive({...tableView(view), ...toRefs(view)});


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
    ])
;

// 搜索字段配置
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

const addKey = ref(0);
const addOrUpdateRef = ref();
const addOrUpdateHandle = (id) => {

  addKey.value++;
  nextTick(() => {
    addOrUpdateRef.value.init(id);
  });
};
</script>