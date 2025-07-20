<!--

crud基础应用
-->

<template>


  <c7-crud
      ref="crud"
      :list-function="(param) =>getCrudData(param)"
      rowsKey="data.data.records"
      totalKey="data.data.total"

  >
    <template #search="{ queryParams }">

      <el-form-item label="名称" prop="name">
        <el-input placeholder="请输入名称" v-model="queryParams.name"></el-input>
      </el-form-item>


    </template>

    <template #operate>

      <el-button type="primary" @click="addHandler">新增</el-button>
    </template>

    <template #default>
      <el-table-column label="id" prop="id"></el-table-column>
      <el-table-column label="名称" prop="name"></el-table-column>
      <el-table-column label="性别" prop="sex"></el-table-column>
    </template>
  </c7-crud>

</template>

<script setup>
import {ref} from "vue";
import {c7Crud} from "c7-plus";
import {getCrudData} from "../api.js";


const addHandler = () => {
  alert("新增")
}
</script>

<style scoped>

</style>
