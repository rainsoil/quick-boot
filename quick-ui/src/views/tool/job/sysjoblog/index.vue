<template>
  <c7-dialog :visible="visibleRef" mode="dialog" title="日志查看"
             @close="visibleRef = false" :footer="false" :modalProps="{
               width:'80%'
             }">
    <div class="app-container">
      <c7-table-search :columns="searchColumns" ref="searchRef" v-model="searchParam"
                       @handleSearch="tableRef.getDataList()" @handleReset="tableRef.handleReset()"></c7-table-search>
      <c7-table :tableProps="tableProps" :columns="jsonColumns" ref="tableRef" :tableParam="searchParam"
                :initParam="initParam" :buttons="buttons"
                :selection="false" @addBtnHandle="addOrUpdateHandle()">
        <template #appendButton>
          <el-col :span="1.5" style="margin-left: 10px">
            <el-button
                type="primary"
                plain
                icon="delete"

                @click="clearHandler()"

            >清空
            </el-button>

          </el-col>
        </template>


        <template #operate="scope">
          <el-tooltip content="查看" placement="top">
            <el-button link type="primary" icon="View" @click="addOrUpdateHandle(scope.row.id)"
                       v-hasPermi="['quartz:sysjoblog:info']">查看
            </el-button>
          </el-tooltip>
        </template>
      </c7-table>
      <!-- 弹窗, 新增 / 修改 -->
      <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="tableRef.getDataList()"></add-or-update>
    </div>

  </c7-dialog>

</template>


<script setup>
import {c7Table, c7TableSearch, c7Dialog} from "c7-plus";
import {reactive, ref, toRefs, nextTick} from "vue";
import AddOrUpdate from "./add-or-update.vue";

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
    label: "调用对象",
    prop: "invokeTarget",
    type: "input",
    placeholder: "请输入调用对象"
  },


  {
    label: "执行状态",
    prop: "status",
    type: "select",
    dictType: "sys_common_status",
  },


]);


// 列表
const tableRef = ref();
const tableProps = reactive({
  getDataListURL: "/quartz/sysjoblog/list",
  getDataListIsPage: true,
  deleteURL: "/quartz/sysjoblog",
  deleteIsBatch: true,
  createdIsNeed: false

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
    type:'dict'

  },


  {
    label: "调用对象",
    prop: "invokeTarget",

  }, {
    label: "参数",
    prop: "params",

  },


  {
    label: "执行状态",
    prop: "status",
    dictType: "sys_common_status",
    type:'dict'

  },

  {
    label: "耗时",
    prop: "timeConsuming",

  },


  {
    label: "开始时间",
    prop: "startTime",

  },
  {
    label: "结束时间",
    prop: "endTime",

  },


]);
const buttons = ref({
  enable: true,
  addBtn: {
    // 是否开启
    enable: false,
  },
  deleteBtn:{
    enable: false,
  },exportBtn:{
    enable: false,
  }
})
const addKey = ref(0);
const addOrUpdateRef = ref();
const addOrUpdateHandle = (id) => {

  addKey.value++;
  nextTick(() => {
    addOrUpdateRef.value.init(id);
  });
};

const jobIdRef = ref('');
const visibleRef = ref(false);
const initParam = ref({
  jobId: ''
})
const init = (jobId) => {
  console.log("jobId", jobId)
  visibleRef.value = true;
  if (jobId) {
    jobIdRef.value = jobId;
    initParam.value.jobId = jobId;
  } else {
    initParam.value.jobId = ''
    jobIdRef.value = ''
  }
  console.log("initParam", initParam.value)

  nextTick(() => {
    tableRef.value.getDataList();
  })
}


defineExpose({
  init
})
// 清空所有
const clearHandler = () => {
  // 确定要清空所有的日志
  proxy.$confirm('确定要清空所有的日志吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    baseService.delete("/quartz/sysjoblog/clean?jobId=" + jobIdRef.value).then(res => {
      proxy.$message({
        type: 'success',
        message: '清空成功!'
      });
      tableRef.value.getDataList();
    })
  }).catch(() => {
    proxy.$message({})
  })
}
</script>
