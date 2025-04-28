<template>
  <div class="app-container">
    <c7-table-search :columns="searchColumns" ref="searchRef" v-model="searchParam"
                     @handleSearch="tableRef.getDataList()" @handleReset="tableRef.handleReset()"></c7-table-search>
    <c7-table :tableProps="tableProps" :columns="jsonColumns" ref="tableRef" :tableParam="searchParam"
              :selection="true" @addBtnHandle="addOrUpdateHandle()">
      <template #appendButton>
        <el-col :span="1.5" style="margin-left: 10px">
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
      <template #slot_status="scope">

        <!--        {{scope.row}}-->
        <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
        ></el-switch>
      </template>


      <template #operate="scope">
        <el-button link type="primary" icon="Edit" @click="addOrUpdateHandle(scope.row.id)"
                   v-hasPermi="['system:user:edit','system:user:query']">修改
        </el-button>
        <el-button link type="primary" icon="Delete" @click="tableRef.deleteBtnHandle(scope.row.id)"
                   v-hasPermi="['system:user:remove']">删除
        </el-button>
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
    </c7-table>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="tableRef.getDataList()"></add-or-update>

    <!-- 日志-->
    <sys-job-log :key="addKey" ref="sysJobLogRef"></sys-job-log>
  </div>


</template>


<script setup>
import {c7Table, c7TableSearch} from "c7-plus";
import {reactive, ref, toRefs} from "vue";
import AddOrUpdate from "./add-or-update.vue";
import sysJobLog from '../sysjoblog/index.vue'

const {proxy} = getCurrentInstance();
import baseService from "@/service/baseService.js";
// 搜索
const searchParam = ref({});
// 搜索字段
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
    type: "select",
    placeholder: "请输入任务组名"
  },


  {
    label: "状态",
    prop: "status",
    dictType: "sys_job_status",
    type: "select",
    placeholder: "请输入状态"
  },


]);


// 列表
const tableRef = ref();
const tableProps = reactive({
  getDataListURL: "/quartz/sysjob/list",
  getDataListIsPage: true,
  deleteURL: "/quartz/sysjob",
  deleteIsBatch: true

})

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
    type: 'dict'

  },


  {
    label: "状态",
    prop: "status",
    // dictType: "sys_job_status",
    isSlot: true,

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
      tableRef.value.getDataList();
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
