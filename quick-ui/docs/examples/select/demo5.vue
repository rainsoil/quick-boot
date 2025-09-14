<!--select 默认值 -->
<template>

  <el-row :gutter="10">
    <el-col :span="8">
      <c7-select :data-list="dictList" v-model="choose"
                 multiple
                 collapse-tags
                 collapse-tags-tooltip></c7-select>
    </el-col>

    <el-col :span="8">
      <span>选中的数据:{{ choose }}</span>
    </el-col>
  </el-row>

  <el-row style="margin-top: 30px">
    <el-col :span="8">
      <c7-select :data-list="dictList" v-model="choose2"
                 multiple
                 collapse-tags
                 collapse-tags-tooltip
                 separator></c7-select>
    </el-col>
    <el-col :span="8">
      <span>选中的数据:{{ choose2 }}</span>
    </el-col>
  </el-row>

  <el-row style="margin-top: 30px">
    <el-col :span="8">
      <c7-select :data-list="dictList" v-model="choose3"
                 multiple
                 collapse-tags
                 collapse-tags-tooltip
                 :separator="false"
                 ></c7-select>
    </el-col>
    <el-col :span="8">
      <span>选中的数据:{{ choose3 }}</span>
    </el-col>
  </el-row>

</template>
<script setup>
import {ref} from 'vue'
const choose = ref("0");

const choose2 = ref("0,1");

const choose3 = ref(["0", "1"])
const dictList = ref([{
  label: "中学",
  value: "0"
},
  {
    label: "小学",
    value: "1"
  },

])

</script>
