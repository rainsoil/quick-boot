<template>
  <div>


    <c7-json-table
        ref="crud"
        :list-function="(param) =>getCrudData(param)"
        :searchParam="searchParam"
        @cleanParam="searchParam={}"
        rowsKey="data.data.records"
        totalKey="data.data.total"
        :searchColumns="searchColumns"
        :tableColumns="tableColumns"

    >


      <template #operate>

        <el-button type="primary" @click="addHandler">新增</el-button>
      </template>


    </c7-json-table>
  </div>

</template>

<script setup>
import {c7JsonTable, c7Select} from 'c7-plus'
import {getCrudData} from "../api.js";
import {ref} from "vue";


const searchParam = ref({
  name: ''
})
const addHandler = () => {
  alert("新增")
}
const sexDict = [
  {
    label: '男',
    value: '0'
  },
  {
    label: '女',
    value: '1'
  }
]
const searchColumns = [
  {
    label: '姓名',
    prop: 'name',
    type: 'input',
    placeholder: '请输入姓名',
    span:12,
  },
  {
    label: '性别',
    prop: 'sex',
    type: 'select',
    placeholder: '请选择性别',
    dataList: sexDict,
    span:12,
  }
]
const tableColumns = [
  {
    label: '姓名',
    prop: 'name',
    type: 'text'
  },
  {
    label: '性别',
    prop: 'sex',
    type: 'tag',
    options: sexDict
  }
]

</script>

<style scoped>

</style>
