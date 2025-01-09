<template>
  <q-modal :visible="visibleRef" mode="dialog" title="日志查看" :width="'90%'"
           @close="visibleRef = false" :footer=false>
    <div class="app-container">
      <qtable-search :columns="searchColumns" :modelValue="state.dataForm" @handle-reset="handleReset"
                     @handle-search="state.getDataList"
                     :add-btn="false"
                     :del-btn="false"

                     @add-btn-handle="addOrUpdateHandle()"
                     @deleteHandle="state.deleteHandle"
      >
        <template #extra>
          <el-col :span="1.5">
            <el-button
                type="primary"
                plain
                icon="delete"

                @click="clearHandler()"

            >清空
            </el-button>

          </el-col>
        </template>
      </qtable-search>
      <qtable v-loading="state.dataListLoading" :tableData="state.dataList" :columns="jsonColumns"
              :page="state.page" :limit="state.limit" :total="state.total"
              @pageSizeChangeHandle="state.pageSizeChangeHandle"
              @pageCurrentChangeHandle="state.pageCurrentChangeHandle"
              @selection-change="state.dataListSelectionChangeHandle" :selection="false">
        <!-- 自定义列, 可以通过 order 配置列的顺序 -->
        <el-table-column label="操作" order="99" width="80px">
          <template #default="scope">

            <el-tooltip content="查看" placement="top">
              <el-button link type="primary" icon="View" @click="addOrUpdateHandle(scope.row.id)"
                         v-hasPermi="['quartz:sysjoblog:info']">查看
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </qtable>

      <!-- 弹窗, 新增 / 修改 -->
      <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="state.getDataList"></add-or-update>


    </div>
  </q-modal>
</template>


<script setup name="sysjoblog">
import tableView from "@/hooks/tableView";
import {reactive, ref, toRefs} from "vue";
import qModal from '@/components/qModal/index.vue'
import AddOrUpdate from "./add-or-update.vue";
import baseService from "@/service/baseService.js";

const view = reactive({
  getDataListURL: "/quartz/sysjoblog/list",
  getDataListIsPage: true,
  deleteURL: "/quartz/sysjoblog",
  deleteIsBatch: false,
  createdIsNeed: false,
  exportURL: "/quartz/sysjoblog/export",
  dataForm: {
    jobId: ""
  }
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

  }, {
    label: "参数",
    prop: "params",

  },


  {
    label: "执行状态",
    prop: "status",
    dictType: "sys_common_status",

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
    label: "调用对象",
    prop: "invokeTarget",
    type: "input",
    placeholder: "请输入调用对象"
  },


  {
    label: "执行状态",
    prop: "status",
    type: "dict",
    dictType: "sys_common_status",
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
const jobIdRef = ref('');
const visibleRef = ref(false);

const init = (jobId) => {
  visibleRef.value = true;
  if (jobId) {
    jobIdRef.value = jobId;
    view.dataForm.jobId = jobId;
  } else {
    view.dataForm.jobId = ''
    jobIdRef.value = ''
  }

  state.getDataList();
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
      state.getDataList();
    })
  }).catch(() => {
    proxy.$message({})
  })
}
const handleReset = () => {
  state.handleReset({
    jobId: jobIdRef.value
  })
}
</script>