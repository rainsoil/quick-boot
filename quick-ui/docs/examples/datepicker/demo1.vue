<template>
  <el-row gutter="10">
    <el-col :span="12">
      <c7-date-picker v-model="value" placeholder="请选择日期" type="date"
      />

    </el-col>
    <el-col :span="8">
      <span>选中的数据:{{ value }}</span>
    </el-col>
  </el-row>
</template>

<script setup>
import {ref} from 'vue'
const value = ref('')
</script>

<style scoped></style>
