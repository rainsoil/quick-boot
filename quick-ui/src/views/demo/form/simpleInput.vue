<template>
  <el-form :model="form" label-width="120px">

    <el-row>
      <c7-input label="名称" v-model="form.name" :span="12" label-width="80px"
                placeholder="请输入名称" @change="inputChange"></c7-input>

    </el-row>

    <span>{{ form.name }}</span>
  </el-form>
</template>

<script setup>
import {ref} from 'vue'

// do not use same name with ref
const form = ref({
  name: '',

})

const inputChange = (val) => {
  console.log(val)
  alert(val)
}
</script>
