<template>


  <el-row :gutter="10">
    <el-col :span="8">
      <c7-select  dict-type="sex" v-model="choose"></c7-select>
    </el-col>

    <el-col :span="8">
      <span>选中的数据:{{ choose }}</span>
    </el-col>
  </el-row>


</template>
<script setup>
import {ref} from 'vue'
import {c7Select} from "c7-plus";
const choose = ref();
const dictList = ref([{
  label: "男",
  value: "0"
},
  {
    label: "女",
    value: "1"
  },


])

</script>
