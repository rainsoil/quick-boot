<!--参数校验 -->

<template>

  <el-form ref="userRef" :model="form" :rules="rules" label-width="80px">
    <el-form-item label="用户名" prop="name">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="年龄" prop="age">
      <el-input-number v-model="form.age"></el-input-number>
    </el-form-item>

    <!--    <c7Button @click="submit">点击</c7Button>-->
    <c7Button :clickFunction="buttonClickHandler" btn-type="submit" :validate="true" :validateRef="userRef"></c7Button>
    <c7Button :clickFunction="buttonClickHandler" btn-type="cancel"></c7Button>
  </el-form>

</template>

<script setup>
import {reactive, ref} from 'vue'
const userRef = ref();

const form = ref({
  name: '',
  age: '',
})
const rules = reactive({
  name: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
  ],
  age: [
    {required: true, message: '请输入年龄', trigger: 'blur'},
  ]
})

const submit = () => {
  userRef.value.validate(valid => {
    if (valid) {

      alert("点击成功")
    }
  });
}
const buttonClickHandler = () => {
  alert('点击了按钮')
}
</script>

<style scoped>

</style>
