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
      <el-table :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle()">
        <el-table-column  label="id" prop="id"></el-table-column>
        <el-table-column  label="dataPicker" prop="dataPicker"></el-table-column>
        <el-table-column  label="dataTimePicker" prop="dataTimePicker"></el-table-column>
        <el-table-column  label="datetime" prop="datetime"></el-table-column>
        <el-table-column  label="input" prop="input"></el-table-column>
        <el-table-column  label="inputNumber" prop="inputNumber"></el-table-column>
        <el-table-column  label="radio" prop="radio"></el-table-column>
        <el-table-column  label="remoteSelect" prop="remoteSelect"></el-table-column>
        <el-table-column  label="select" prop="select"></el-table-column>
      </el-table>
      <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit"
                     :total="state.total" layout="total, sizes, prev, pager, next, jumper"
                     @size-change="state.pageSizeChangeHandle"
                     @current-change="state.pageCurrentChangeHandle"></el-pagination>

    </el-card>



  </div>
</template>

<script setup>

import tableView from "@/hooks/tableView";

import {reactive, ref, toRefs} from "vue";

const view = reactive({
  getDataListURL: "/demo/crud/list",
  getDataListIsPage: true,
  deleteURL: "/demo/crud",
  deleteIsBatch: true,
  exportURL: "",
  dataForm: {


  }
});
const state = reactive({...tableView(view), ...toRefs(view)});

</script>