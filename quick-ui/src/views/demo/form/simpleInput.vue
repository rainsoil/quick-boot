<template>
  <el-form :model="form" label-width="120px">

    <el-row>
      <c7-input label="名称" v-model="form.name" :span="12" label-width="80px"
                placeholder="请输入名称" @change="inputChange"></c7-input>

    </el-row>
    <el-button @click="inputChange2">点我</el-button>

    <span>{{ form.name }}</span>
  </el-form>
</template>

<script setup>
import {ref} from 'vue'
import injectService from "../../../packages/service/injectService.js";
// do not use same name with ref
const form = ref({
  name: '',

})

const inputChange2 = () => {
  injectService.postRequest("https://www.baidu.com/", {}).then(res => {
    console.log(res)
  })
}
const inputChange = (val) => {
  console.log(val)
  alert(val)
}
</script>
