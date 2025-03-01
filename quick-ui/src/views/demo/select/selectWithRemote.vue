<template>


  <el-row :gutter="10">
    <el-col :span="8">
      <c7-select v-model="choose" url="/mockapi/dict/111" remote></c7-select>
    </el-col>

    <el-col :span="8">
      <span>选中的数据:{{ choose }}</span>
    </el-col>
  </el-row>


</template>
<script setup>
import {ref} from 'vue'

const choose = ref();


</script>
