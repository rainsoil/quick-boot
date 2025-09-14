<!-- select 使用函数-->
<template>

  <el-row :gutter="10">
    <el-col :span="12">
      <c7-select v-model="choose"
                 :fetchData="fetchData"

      ></c7-select>
    </el-col>

    <el-col :span="8">
      <span>选中的数据 :{{ choose }}</span>
    </el-col>
  </el-row>
  <el-row :gutter="10" style="margin-top: 20px">

    <el-col :span="12">
      <c7-select v-model="choose2"
                 :fetchData="() =>getSelectData()"
                 result-key="data.data"

      ></c7-select>
    </el-col>

    <el-col :span="8">
      <span>选中的数据 :{{ choose2 }}</span>
    </el-col>

  </el-row>

</template>
<script setup>
import {ref} from 'vue'
import axios from "axios";
import {getSelectData} from '../api.js'

const choose = ref("1");

const choose2 = ref();

const fetchData = () => {

  // 模拟发送请求 返回Primise
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve({
        data: [
          {
            label: "选项1",
            value: "1",
          },
          {
            label: "选项2",
            value: "2",
          },
          {
            label: "选项3",
            value: "3",
          },
        ]
      });
    }, 1000);
  })
}

const fetchData2 = () => {
  return axios.get("https://m1.apifoxmock.com/m1/4742948-4395863-default/mockapi/dict/sex");
}
</script>
