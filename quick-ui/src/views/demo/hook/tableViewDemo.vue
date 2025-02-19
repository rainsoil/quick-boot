<!-- tableView 表格hook-->

<template>

  <div class="app-container">
    <el-card style="max-width: 100%">
      <template #header>
        <div class="card-header" style="font-size: 30px">
          <span>tableView</span>
        </div>
      </template>
      <span>
            tableView 是对table组件的一个hook
          </span>

      <el-row>
        <el-col :span="12">
          <el-button
              type="primary"
              plain
              icon="Plus"
              @click="alertDataListSelections"
          >选中的数据
          </el-button>

          <el-button
              type="primary"
              plain
              icon="Plus"
              @click="alertDataListSelectionsId"
          >选中的数据id
          </el-button>



        </el-col>
      </el-row>
      <el-table :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle">
        <el-table-column type="selection"></el-table-column>
        <el-table-column label="id" prop="id"></el-table-column>
        <el-table-column label="姓名" prop="name"></el-table-column>
        <el-table-column label="性别" prop="sex">

          <template #default="scope">
            {{ state.getDictLabel("sys_user_sex", scope.row.sex) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" order="99" width="80px">
          <template #default="scope">

            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="state.deleteHandle(scope.row.id)"
                        >删除
              </el-button>
            </el-tooltip>
          </template>
        </el-table-column>

      </el-table>
      <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit"
                     :total="state.total" layout="total, sizes, prev, pager, next, jumper"
                     @size-change="state.pageSizeChangeHandle"
                     @current-change="state.pageCurrentChangeHandle"></el-pagination>

    </el-card>


  </div>
</template>

<script setup>

import tableView from "../../../packages/hooks/tableView";

import {reactive, ref, toRefs} from "vue";

const view = reactive({
  getDataListURL: "/mockapi/crud/tableView",
  getDataListIsPage: true,
  deleteURL: "/demo/crud",
  deleteIsBatch: true,
  exportURL: "",
  dataForm: {}
});
const state = reactive({...tableView(view), ...toRefs(view)});


const alertDataListSelections = () => {
  alert(JSON.stringify(state.dataListSelections))
}

const alertDataListSelectionsId = () => {
  alert(JSON.stringify(state.dataListSelectionsIds))
}
</script>
