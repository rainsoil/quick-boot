<template>
  <div class="app-container">
    <qtable-search :columns="searchColumns" :modelValue="state.dataForm" @handle-reset="state.handleReset"
                   @handle-search="state.getDataList"

                   @add-btn-handle="addOrUpdateHandle()"
                   @deleteHandle="state.deleteHandle"
                   add-btn-perms="quartz:sysjob:add"
                   del-btn-perms="quartz:sysjob:remove"
    >
      <template #extra>
        <el-col :span="1.5">
          <el-button
              type="primary"
              plain
              icon="View"

              @click="sysJobLogHandler()"
              v-hasPermi="['quartz:sysjob:list']"
          >调度日志
          </el-button>

        </el-col>
      </template>
    </qtable-search>
    <qtable v-loading="state.dataListLoading" :tableData="state.dataList" :columns="jsonColumns"
            :page="state.page" :limit="state.limit" :total="state.total"
            @pageSizeChangeHandle="state.pageSizeChangeHandle"
            @pageCurrentChangeHandle="state.pageCurrentChangeHandle"
            @selection-change="state.dataListSelectionChangeHandle" :table-props={selection:true}>
      <!-- 自定义列, 可以通过 order 配置列的顺序 -->
      <el-table-column label="操作" order="99" width="250px">
        <template #default="scope">
          <el-tooltip content="修改" placement="top">
            <el-button link type="primary" icon="Edit" @click="addOrUpdateHandle(scope.row.id)"
                       v-hasPermi="['quartz:sysjob:edit']">编辑
            </el-button>
          </el-tooltip>

          <el-tooltip content="删除" placement="top">
            <el-button link type="primary" icon="Delete" @click="state.deleteHandle(scope.row.id)"
                       v-hasPermi="['quartz:sysjob:remove']">删除
            </el-button>
          </el-tooltip>


          <el-tooltip content="任务详细" placement="top">
            <el-button link type="primary" icon="View" @click="addOrUpdateHandle(scope.row.id,'2')"
                       v-hasPermi="['quartz:sysjob:edit']">任务详细
            </el-button>
          </el-tooltip>

          <el-tooltip content="修改" placement="top">
            <el-button link type="primary" icon="CaretRight" @click="runHandle(scope.row.id)"
                       v-hasPermi="['quartz:sysjob:edit']">执行一次
            </el-button>
          </el-tooltip>

          <el-tooltip content="删除" placement="top">
            <el-button link type="primary" icon="Operation" @click="sysJobLogHandler(scope.row.id)"
                       v-hasPermi="['quartz:sysjob:remove']">调度日志
            </el-button>
          </el-tooltip>
        </template>
      </el-table-column>

      <template #status="{item}">

        <el-switch
            v-model="item.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(item)"
        ></el-switch>
      </template>
    </qtable>

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="state.getDataList"></add-or-update>

    <!-- 日志-->

    <sys-job-log :key="addKey" ref="sysJobLogRef"></sys-job-log>
  </div>
</template>


<script setup name="sysjob">
import tableView from "@/hooks/tableView";
import {reactive, ref, toRefs} from "vue";

import AddOrUpdate from "./add-or-update.vue";
import sysJobLog from '../sysjoblog/index.vue'
import baseService from "@/service/baseService.js";

const view = reactive({
  getDataListURL: "/quartz/sysjob/list",
  getDataListIsPage: true,
  deleteURL: "/quartz/sysjob",
  deleteIsBatch: true,
  exportURL: "/quartz/sysjob/export",
  dataForm: {}
});
const {proxy} = getCurrentInstance();

const state = reactive({...tableView(view), ...toRefs(view)});


// 列表字段配置
const jsonColumns = ref([

  {
    label: "任务名称",
    prop: "jobName",

  },


  {
    label: "任务组名",
    prop: "jobGroup",
    dictType: "sys_job_group",

  },


  {
    label: "调用对象",
    prop: "invokeTarget",

  },
  {
    label: "参数",
    prop: "params",

  },


  {
    label: "cron执行表达式",
    prop: "cronExpression",

  },


  {
    label: "是否并发执行",
    prop: "concurrent",
    dictType: "sys_yes_no",

  },


  {
    label: "状态",
    prop: "status",
    // dictType: "sys_job_status",
    isSolt: true

  },


]);

// 搜索字段配置
const searchColumns = ref([

  {
    label: "任务名称",
    prop: "jobName",
    type: "input",
    placeholder: "请输入任务名称"
  },


  {
    label: "任务组名",
    prop: "jobGroup",
    dictType: "sys_job_group",
    type: "dict",
    placeholder: "请输入任务组名"
  },


  {
    label: "状态",
    prop: "status",
    dictType: "sys_job_status",
    type: "dict",
    placeholder: "请输入状态"
  },


]);

const addKey = ref(0);
const addOrUpdateRef = ref();
const addOrUpdateHandle = (id, type) => {
  if (!type) {
    type = '1';
  }

  addKey.value++;
  nextTick(() => {
    addOrUpdateRef.value.init(id, type);
  });
};
// 修改状态
const handleStatusChange = (row) => {
  let id = row.id;
  if (!id) {
    return;
  }
  let status = row.status;
  let msg = '';
  if (status == '0') {

    msg = '启用'
  } else {
    msg = '暂停'
  }
  proxy.$confirm('确认要' + (msg) + '任务' + row.jobName + '吗？', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function () {
    baseService.get("/quartz/sysjob/changeStatus/" + id + "/" + status).then(res => {
      proxy.$message({
        message: msg + '成功',
        type: "success"
      });
    }).finally(() => {
      state.getDataList();
    })
  })

}

// 立即执行
const runHandle = (id) => {

  proxy.$confirm('确认要立即执行一次任务吗？', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function () {
    baseService.get("/quartz/sysjob/run/" + id).then(res => {
      proxy.$message({
        message: '立即执行成功',
        type: "success"
      });
    })
  })
}
// 日志
const sysJobLogRef = ref();
const sysJobLogHandler = (jobId) => {
  nextTick(() => {
    sysJobLogRef.value.init(jobId)
  })
}
</script>