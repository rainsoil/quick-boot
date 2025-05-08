<template>
  <div class="app-container">
    <c7-switch-form
        :showIndexs="showIndexs"
        v-model="showIndex"
        @update:modelValue="console.log($event)"
    >
      <template #table>
        <c7-table-search :columns="searchColumns" ref="searchRef" v-model="searchParam"
                         @handleSearch="tableRef.getDataList()" @handleReset="tableRef.handleReset()"></c7-table-search>
        <c7-table :tableProps="tableProps" :columns="jsonColumns" ref="tableRef" :tableParam="searchParam"
                  :buttons="buttons"
                  :selection="true">
          <template #appendButton>

            <el-row :gutter="20" style="margin-left: 14px">
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
            </el-row>
          </template>


          <template #operate="scope">
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
              <el-button link type="primary" icon="Delete" @click="tableRef.deleteBtnHandle(scope.row.id)"
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
        </c7-table>


        <!-- 导入-->
        <import-table ref="importRef" :key="addKey" @refreshDataList="tableRef.getDataList()"></import-table>
        <!-- 创建表-->
        <create-table ref="createTableRef" :key="addKey" @refreshDataList="tableRef.getDataList()"
                      @close="showIndex ='table'"></create-table>
      </template>

      <template #edit>
        <edit-table :key="addKey" ref="editTableRef" @refreshDataList="tableRef.getDataList()"
                    @close="showIndex ='table'"></edit-table>

      </template>

      <template #preview>

        <preview-code :key="addKey" ref="previewCodeRef" @close="showIndex ='table'"></preview-code>
      </template>

    </c7-switch-form>
  </div>
</template>
<script setup>
import {c7Table, c7TableSearch, c7SwitchForm} from "c7-plus";
import ImportTable from "@/views/tool/gen/ImportTable.vue";
import EditTable from "@/views/tool/gen/editTable.vue";
import {ref, nextTick} from "vue";
import {getToken} from "@/utils/auth.js";
import PreviewCode from "@/views/tool/gen/previewCode.vue";
import createTable from "@/views/tool/gen/createTable.vue";

const buttons = ref({
  enable: true,
  deleteBtn: {
    enable: true
  },

})
const showIndexs = ref([{
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
  }])

const showIndex = ref("table")


const {proxy} = getCurrentInstance();
import baseService from "@/service/baseService.js";
import {ElMessage, ElMessageBox} from "element-plus";
// 搜索
const searchParam = ref({});
// 搜索字段
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

  }


]);


// 列表
const tableRef = ref();
const tableProps = reactive({
  getDataListURL: "/generator/gentable/list",
  getDataListIsPage: true,
  deleteURL: "/generator/gentable",
  deleteIsBatch: true

})

// 列表字段配置
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
const addKey = ref(0);

const importRef = ref(false);

const createTableRef = ref();
const open = (type) => {
  addKey.value++;
  if (type == '1') {
    // 打开创建页面
    nextTick(() => {
      createTableRef.value.init();
    })
  } else if (type == '2') {
    nextTick(() => {
      importRef.value.init();
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

const editTableRef = ref()

const editTableHandler = (id) => {
  showIndex.value = 'edit'
  nextTick(() => {
    editTableRef.value.init(id)
  })
}


// 代码生成
const generatorHandler = (row) => {

  console.log(tableRef.value.dataListSelections())
  if (
      tableProps.deleteIsBatch &&
      !row &&
      tableRef.value.dataListSelections() &&
      tableRef.value.dataListSelections().length <= 0
  ) {
    ElMessage.warning({
      message: '请选择操作项',
      duration: 500
    });
    return;
  }
  let tableIds = (row
      ? [row.id]
      : tableRef.value.dataListSelectionsIds().join(","));
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
                    tableRef.value.getDataList();
                  }
                });
              });
        }
      })
      .catch(() => {
        //
      });
}

// 预览代码
const previewCodeRef = ref();

const previewCodeHandler = (id) => {
  showIndex.value = 'preview'
  nextTick(() => {
    previewCodeRef.value.init(id)
  })
}
</script>
<style scoped>

</style>
