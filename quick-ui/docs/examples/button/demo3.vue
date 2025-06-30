<template>

  <c7Button :clickFunction="buttonClickHandler" :confirm="true" confirmMessage="是否确认点击">调用本地事件</c7Button>


  <c7Button :clickFunction="() =>apiClick(Math.random() < 0.5?'200':'500')" successMessage="调用成功"
            :checkSuccess="(res) => res.data.code ===200" errorMessage="data.msg" :confirm="true" confirmMessage="是否确认点击">调用接口
  </c7Button>


</template>

<script setup>
import {ref} from 'vue'
import {c7Button} from "c7-plus";
import {apiClick} from './api.js'

const buttonClickHandler = () => {
  alert('点击了按钮')
}
</script>

<style scoped>

</style>
