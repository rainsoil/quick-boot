<template>

  <el-row :gutter="10">
    <el-col :span="8">
      <c7-select :data-list="dictList" v-model="choose"></c7-select>
    </el-col>

    <el-col :span="8">
      <span>选中的数据:{{ choose }}</span>
    </el-col>
  </el-row>

</template>
<script setup>
import {ref, toRefs} from 'vue'
const choose = ref();
const dictList = toRefs([{
  label: "中学",
  value: "0"
},
  {
    label: "小学",
    value: "1"
  },

])

</script>
