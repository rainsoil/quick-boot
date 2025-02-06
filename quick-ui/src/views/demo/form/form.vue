<template>

  <div class="app-container">

    <h1>form表单</h1>

    <el-form>
      <el-row>
        <c7-input label="名称" :value="formRef.name" prop="name" placeholder="请输入名称"></c7-input>
      </el-row>
    </el-form>

  </div>

</template>

<script setup>

const formRef = reactive({
  name: ''
})

</script>


<style scoped>

</style>
