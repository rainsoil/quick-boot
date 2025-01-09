<template>
  <div class="app-container">

    <switch-form v-model="currentIndex" :show-indexs="showIndexs">
      <template #table>
        <qtable-search :columns="searchColumns" :modelValue="state.dataForm" @handle-reset="state.handleReset"
                       @handle-search="state.getDataList"
                       @deleteHandle="state.deleteHandle"
                       :add-btn=false
                       del-btn-perms="generator:gen:remove"

        >
          <template #extra>
            <el-col :span="1.5">
              <el-button
                  type="primary"
                  plain
                  icon="Download"
                  :disabled="multiple"
                  @click="generatorHandler()"
                  v-hasPermi="['generator:gen:code']"
              >生成
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                  type="primary"
                  plain
                  icon="Plus"
                  @click="open('1')"
                  v-hasPermi="['generator:gen:import']"
              >创建
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                  type="info"
                  plain
                  icon="Upload"
                  @click="open('2')"
                  v-hasPermi="['generator:gen:import']"
              >导入
              </el-button>
            </el-col>


          </template>


        </qtable-search>
        <qtable v-loading="state.dataListLoading" :tableData="state.dataList" :columns="jsonColumns"
                :page="state.page" :limit="state.limit" :total="state.total"
                @pageSizeChangeHandle="state.pageSizeChangeHandle"
                @pageCurrentChangeHandle="state.pageCurrentChangeHandle"
                @selection-change="state.dataListSelectionChangeHandle" :selection=true
        >
          <!-- 自定义列, 可以通过 order 配置列的顺序 -->
          <el-table-column label="操作" order="99" width="400">
            <template #default="scope">
              <el-tooltip content="修改" placement="top">
                <el-button link type="primary" icon="View" @click="previewCodeHandler(scope.row.id)"
                           v-hasPermi="['generator:gen:preview']">预览
                </el-button>
              </el-tooltip>
              <el-tooltip content="修改" placement="top">
                <el-button link type="primary" icon="Edit" @click="editTableHandler(scope.row.id)"
                           v-hasPermi="['generator:gen:edit']">修改
                </el-button>
              </el-tooltip>
              <el-tooltip content="删除" placement="top">
                <el-button link type="primary" icon="Delete" @click="state.deleteHandle(scope.row.id)"
                           v-hasPermi="['generator:gen:remove']">删除
                </el-button>
              </el-tooltip>

              <el-tooltip content="删除" placement="top">
                <el-button link type="primary" icon="Refresh" @click="syncTableHandler(scope.row.id)"
                           v-hasPermi="['generator:gen:edit']">同步
                </el-button>
              </el-tooltip>

              <el-tooltip content="删除" placement="top">
                <el-button link type="primary" icon="Download" @click="generatorHandler(scope.row)"
                           v-hasPermi="['generator:gen:code']">生成代码
                </el-button>
              </el-tooltip>
            </template>
          </el-table-column>
        </qtable>
        <!-- 导入-->
        <import-table :key="addKey" ref="importTableRef" @refreshDataList="state.getDataList"></import-table>
      </template>

      <template #edit>
        <edit-table :key="addKey" ref="editTableRef" @refreshDataList="state.getDataList"
                    @close="currentIndex ='table'"></edit-table>
      </template>

      <template #preview>

        <preview-code :key="addKey" ref="previewCodeRef" @close="currentIndex ='table'"></preview-code>
      </template>


    </switch-form>

  </div>
</template>

<script setup name="Role">
import tableView from "@/hooks/tableView";
import {reactive, ref, toRefs} from "vue";
import SwitchForm from "@/components/SwitchForm/index.vue"
import QtableSearch from "@/components/qtableSearch/index.vue";
import EditTable from "@/views/tool/gen/editTable.vue";
import baseService from "@/service/baseService.js";
import ImportTable from "@/views/tool/gen/importTable.vue";
import {ElMessage, ElMessageBox} from "element-plus";
import PreviewCode from "@/views/tool/gen/previewCode.vue";
import {getToken} from "@/utils/auth.js";

const showSearch = ref(true);

const view = reactive({
  getDataListURL: "/generator/gentable/list",
  getDataListIsPage: true,
  deleteURL: "/generator/gentable",
  deleteIsBatch: true,
  exportURL: "/generator/gentable/export",
  dataForm: {}
});
const {proxy} = getCurrentInstance();

const state = reactive({...tableView(view), ...toRefs(view)});


// 搜索字段配置
const searchColumns = ref([

  {
    label: "表名称",
    prop: "tableName",
  },


  {
    label: "表描述",
    prop: "tableComment",
  },


  {
    label: "实体类名称",
    prop: "className",


  },
]);

//列表字段配置
const jsonColumns = ref([


  {
    label: "表名称",
    prop: "tableName",
  },


  {
    label: "表描述",
    prop: "tableComment",
  },


  {
    label: "实体类名称",
    prop: "className",


  },


  {
    label: "创建时间",
    prop: "createTime",
  },


]);
const showIndexs = ref([
  {
    name: 'table',
    header: false
  },
  {
    name: 'edit',
    header: true,
    title: "修改表",
    closeIndex: "table"
  },
  {
    name: 'preview',
    header: true,
    title: "预览代码",
    closeIndex: "table"
  }
])
const currentIndex = ref("table")
const editTableRef = ref()

const editTableHandler = (id) => {
  currentIndex.value = 'edit'
  nextTick(() => {
    editTableRef.value.init(id)
  })
}
const addKey = ref(0);

// 创建表
const createTableRef = ref();
// 导入表
const importTableRef = ref()

// 预览代码
const previewCodeRef = ref();

const previewCodeHandler = (id) => {
  currentIndex.value = 'preview'
  nextTick(() => {
    previewCodeRef.value.init(id)
  })
}


const open = (type) => {
  addKey.value++;
  if (type == '1') {
    // 打开创建页面
    nextTick(() => {
      createTableRef.value.init();
    })
  } else if (type == '2') {
    nextTick(() => {
      importTableRef.value.init();
    })
  }
}

const syncTableHandler = (tableId) => {
  proxy.$modal.confirm('确认要强制同步数据吗？').then(function () {
    return baseService.get("/generator/gentable/tableSyn/" + tableId);
  }).then(() => {
    proxy.$modal.msgSuccess("同步成功")
  })
}

// 代码生成
const generatorHandler = (row) => {
  if (
      state.deleteIsBatch &&
      !row &&
      state.dataListSelections &&
      state.dataListSelections.length <= 0
  ) {
    ElMessage.warning({
      message: '请选择操作项',
      duration: 500
    });
    return;
  }
  let tableIds = (row
      ? [row.id]
      : state.dataListSelectionsIds.join(","));
  console.log(tableIds)
  ElMessageBox.confirm("确定进行代码生成操作?", "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning"
  })
      .then(() => {
        let genType = "0";
        if (row) {
          genType = row.genType;
        }
        if (genType == '0') {
          let token = getToken();
          window.location.href = import.meta.env.VITE_APP_BASE_API + "/generator/gentable/generator/download?tableIds=" + tableIds + "&Authorization=Bearer " + token
        } else {
          baseService
              .get(
                  "/generator/gentable/generator?tableIds=" + tableIds
              )
              .then((res) => {
                ElMessage.success({
                  message: "成功",
                  duration: 500,
                  onClose: () => {
                    state.getDataList();
                  }
                });
              });
        }
      })
      .catch(() => {
        //
      });
}

</script>
