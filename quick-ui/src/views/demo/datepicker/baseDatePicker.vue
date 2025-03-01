<template>
  <c7-title label="基础用法" label-size="h3"></c7-title>
  <el-row gutter="10">
    <el-col :span="12">
      <c7-date-picker v-model="value" placeholder="请选择日期" type="date"
      />

    </el-col>
    <el-col :span="8">
      <span>选中的数据:{{ value }}</span>
    </el-col>
  </el-row>


  <c7-title label="选择日期区间" label-size="h3"></c7-title>
  <el-row gutter="10">
    <el-col :span="12">
      <c7-date-picker v-model="value2" placeholder="请选择日期" type="daterange"
      />
    </el-col>
  </el-row>
  <el-row>
    <el-col :span="4">
    </el-col>
    <el-col :span="12">
      <span>选中的数据:{{ value2 }}</span>
    </el-col>
  </el-row>

  <c7-title label="选择日期区间(初始化值)" label-size="h3"></c7-title>
  <el-row>
    <el-col :span="12">
      <c7-date-picker v-model="value3" placeholder="请选择日期" type="daterange"
      />
    </el-col>

  </el-row>
  <el-row>
    <el-col :span="8">

    </el-col>
    <el-col :span="12">
      <span>选中的数据:{{ value3 }}</span>
    </el-col>
  </el-row>

  <c7-title label="选择日期区间(不合并)" label-size="h3"></c7-title>

  <el-row>
    <el-col :span="12">
      <c7-date-picker v-model="value4" placeholder="请选择日期" type="daterange" :rangeMerge=false
      />
    </el-col>
  </el-row>

  <el-row>
    <el-col :span="8">

    </el-col>
    <el-col :span="12">
        <span>选中的数据:{{ value4 }}</span>
    </el-col>
  </el-row>

</template>

<script setup>
import {ref} from 'vue'

const value = ref()
const value2 = ref()

const value3 = ref("2021-01-01,2024-09-23")

const value4 = ref(["2021-01-01", "2024-09-23"])
</script>
