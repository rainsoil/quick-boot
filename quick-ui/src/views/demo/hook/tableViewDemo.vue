<!--演示tableView Hook的demo类 -->


<template>
  <el-table :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle()">
    <el-table-column label="id" prop="id"></el-table-column>
    <el-table-column label="dataPicker" prop="dataPicker"></el-table-column>
    <el-table-column label="dataTimePicker" prop="dataTimePicker"></el-table-column>
    <el-table-column label="datetime" prop="datetime"></el-table-column>
    <el-table-column label="input" prop="input"></el-table-column>
    <el-table-column label="inputNumber" prop="inputNumber"></el-table-column>
    <el-table-column label="radio" prop="radio"></el-table-column>
    <el-table-column label="remoteSelect" prop="remoteSelect"></el-table-column>
    <el-table-column label="select" prop="select"></el-table-column>
  </el-table>
  <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit"
                 :total="state.total" layout="total, sizes, prev, pager, next, jumper"
                 @size-change="state.pageSizeChangeHandle"
                 @current-change="state.pageCurrentChangeHandle"></el-pagination>

</template>

<script setup>
import {reactive, ref, toRefs} from "vue";
import tableView from "@/packages/hooks/tableView";


const view = reactive({
  getDataListURL: "/mockapi/crud/list2",
  getDataListIsPage: true,
  deleteURL: "/sys/schedule",
  deleteIsBatch: true,
  exportURL: "/system/role/export",
  dataForm: {
    beanName: ""
  }
});
const state = reactive({...tableView(view), ...toRefs(view)});

</script>

<style scoped>

</style>
