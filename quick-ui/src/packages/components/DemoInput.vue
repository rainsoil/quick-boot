<template>
  <el-button @click="post">
    <slot></slot>
  </el-button>
</template>
<script setup name="DemoButton">


const post = () => {

  alert("111")
}
</script>
