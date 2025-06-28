<!--select 默认值 -->
<template>


  <el-row :gutter="10">
    <el-col :span="8">
      <c7-select :data-list="dictList" v-model="choose" labelKey="name" valueKey="id"></c7-select>
    </el-col>

    <el-col :span="8">
      <span>选中的数据:{{ choose }}</span>
    </el-col>
  </el-row>


</template>
<script setup>
import {ref} from 'vue'
import {c7Select} from "c7-plus";

const choose = ref("0");
const dictList = ref([{
  name: "中学",
  id: "0"
},
  {
    name: "小学",
    id: "1"
  },


])

</script>
