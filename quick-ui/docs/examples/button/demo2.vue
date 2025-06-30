<template>

  <c7Button :clickFunction="buttonClickHandler">调用本地事件</c7Button>


  <c7Button :clickFunction="() =>apiClick(Math.random() < 0.5?'200':'500')" successMessage="调用成功"
            :checkSuccess="(res) => res.data.code ===200" errorMessage="data.msg">调用接口
  </c7Button>


  <c7Button :clickFunction="() =>apiClick('200')" :checkSuccess="(res) => res.data.code ===200" successMessage="调用成功">调用接口(成功)</c7Button>

  <c7Button :clickFunction="() =>apiClick('500')" :checkSuccess="(res) => res.data.code ===200" errorMessage="data.msg">调用接口(接口返回失败)</c7Button>

  <c7Button :clickFunction="() =>apiClick2('500')"  :checkSuccess="(res) => res.data.code ===200" errorMessage="调用失败">调用接口(接口失败)</c7Button>


</template>

<script setup>
import {ref} from 'vue'
import {c7Button} from "c7-plus";
import {apiClick, apiClick2} from './api.js'

const buttonClickHandler = () => {
  alert('点击了按钮')
}
</script>

<style scoped>

</style>
