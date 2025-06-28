<!--select 默认值 -->
<template>


  <el-row :gutter="10">
    <el-col :span="8">
      <c7-select :data-list="dictList" v-model="choose"

                 @change="change"
                 @visibleChange="visibleChange"
      ></c7-select>
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
  label: "中学",
  value: "0",
  type: 'success'
},
  {
    label: "小学",
    value: "1",
    type: 'info'
  },


])

const change = (val) => {
  console.log("change", val)
}

const visibleChange = (open) => {
  console.log("visibleChange", open)
}
</script>
