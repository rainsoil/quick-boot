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

      <!-- 操作栏卡槽-->

      <template #operate>

        <el-button type="primary" @click="addHandler">新增</el-button>
      </template>

      <!-- 搜索栏卡槽-->
      <template #slotTest="{ item ,searchParam}">
        <el-select v-model="searchParam.status" placeholder="状态" clearable style="width: 200px">
          <el-option label="启用" value="1"/>
          <el-option label="禁用" value="0"/>
        </el-select>
      </template>
      <!-- 自定义插槽列内容 -->
      <template #customAction="{ row, index }">
        <el-button @click="handleCustomAction(row, index)">操作{{ index }}</el-button>
      </template>

      <!-- 自定义插槽列内容 -->
      <template #customAction2="{ row, index }">
        <span>id=>{{ row.id }}</span>
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
    span: 12,
  },
  {
    label: '性别',
    prop: 'sex',
    type: 'select',
    placeholder: '请选择性别',
    dataList: sexDict,
    span: 12,
  },
  {
    label: '状态',
    prop: 'status',
    type: 'slot',
    placeholder: '请选择状态',
    span: 12,
    slotName: 'slotTest'
  }
]
const tableColumns = [
  {
    label: '姓名',
    prop: 'name',
    columnType: 'text'
  },
  {
    label: '性别',
    prop: 'sex',
    columnType: 'tag',
    dictList: sexDict
  },
  {
    label: 'id',
    prop: 'id',
    columnType: "text",
  },
  {
    columnType: "slot",
    label: '自定义操作',
    width: 120,
    slotName: 'customAction'
  },
  {
    columnType: "slot",
    label: '自定义卡槽',
    width: 120,
    slotName: 'customAction2'
  }
]
// 自定义操作处理
const handleCustomAction = (row, index) => {
  alert(`点击了第${index}行操作按钮，数据：${JSON.stringify(row)}`)
}
</script>

<style scoped>

</style>
