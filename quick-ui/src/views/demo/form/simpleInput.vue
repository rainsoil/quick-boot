<template>
  <el-form :model="form" label-width="120px">
    <el-form-item label="Activity name">
      <el-input v-model="form.name" />
    </el-form-item>

    <c7-input label="名称" v-model="form.name" :span="8" label-width="140px"
                placeholder="请输入名称"></c7-input>
  </el-form>
</template>

<script setup>
import { reactive } from 'vue'

// do not use same name with ref
const form = reactive({
  name: '',

})

</script>
