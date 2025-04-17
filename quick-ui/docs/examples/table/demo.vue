<template>

  <c7-table-search :columns="tableSearchProps" ref="searchRef" v-model="searchParam"
                   @handleSearch="tableRef.getDataList()" @handleReset="tableRef.handleReset()"></c7-table-search>
  <c7-table :tableProps="tableProps" :columns="columns" ref="tableRef" @addBtnHandle="handle('新增')"
            :selection="true">
    <template #operate="scope">
      <el-button type="primary" @click="tableRef.deleteBtnHandle(scope.row.id)">删除</el-button>
    </template>
  </c7-table>
</template>

<script setup lang="ts">
import {c7Table, ITableColumn, IViewHooksOptions, c7TableSearch, IColumn} from "c7-plus";
import {ref} from "vue";

const searchParam = ref({})
const tableSearchProps = ref<IColumn[]>([
  {
    label: '姓名',
    prop: 'name',
  },
  {
    label: '年龄',
    prop: 'age',
  },
]);
const columns = ref<ITableColumn[]>([

  {
    label: '姓名',
    prop: 'name',
  },
  {
    label: '年龄',
    prop: 'age',
  },
  {
    label: '性别',
    prop: 'sex',
  },
  {
    label: '地址',
    prop: 'address',
  }
]);

const tableData = ref([
  {
    name: '张三',
    age: 18,
    sex: '男',
    address: '北京'
  },
  {
    name: '李四',
    age: 19,
    sex: '女',
    address: '上海'
  }
])
const tableProps = ref<IViewHooksOptions>({
  getDataListURL: '/mockapi/crud/tableView',
  deleteURL: '/mockapi/crud/tableView',
  dataForm: searchParam


})
const tableRef = ref<InstanceType<typeof c7Table>>();


const handle = (msg) => {
  alert(msg)
}

</script>
