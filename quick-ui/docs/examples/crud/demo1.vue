<!--

crud基础应用
-->

<template>


  <c7-crud
      ref="crud"
      :list-function="(param) =>getCrudData(param)"
      :searchParam="searchParam"
      @cleanParam="searchParam={}"
      rowsKey="data.data.records"
      totalKey="data.data.total"

  >
    <template #search>

      <el-form-item label="名称" prop="name">
        <el-input placeholder="请输入名称" v-model="searchParam.name"></el-input>
      </el-form-item>

      <el-form-item label="性别" prop="sex">
        <c7-select
            :dataList="sexDict"
            v-model="searchParam.sex"
            placeholder="请选择性别"
        ></c7-select>
      </el-form-item>


    </template>

    <template #operate>

      <el-button type="primary" @click="addHandler">新增</el-button>
    </template>

    <template #default>
      <el-table-column label="id" prop="id"></el-table-column>
      <el-table-column label="名称" prop="name"></el-table-column>
      <el-table-column label="性别" prop="sex">
        <template #default="scope">
          <c7-dict-tag :options="sexDict" v-model="scope.row.sex"></c7-dict-tag>
        </template>
      </el-table-column>
    </template>
  </c7-crud>

</template>

<script setup>
import {ref} from "vue";
import {c7Crud, c7Select, c7DictTag} from "c7-plus";
import {getCrudData} from "../api.js";

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
</script>

<style scoped>

</style>
