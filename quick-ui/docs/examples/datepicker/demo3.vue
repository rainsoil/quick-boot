<template>
  <el-row gutter="10">
    <el-col :span="12">
      <c7-date-picker v-model="value2" placeholder="请选择日期" type="daterange"
      />
    </el-col>
  </el-row>
  <el-row>

    <el-col :span="12">
      <span>选中的数据:{{ value2 }}</span>
    </el-col>
  </el-row>
</template>

<script setup>
import {ref} from 'vue'
import {c7DatePicker} from "c7-plus";
const value2 = ref("2021-01-01,2024-09-23")
</script>

<style scoped></style>
