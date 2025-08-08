<template>
  <c7-button
      :clickFunction="handleSubmit"
      :isSuccessCallback="true"
      :isErrorCallback="true"
      @successCallback="onSuccess"
      @errorCallback="onError"
  >
    提交
  </c7-button>
</template>

<script setup>
import {reactive, ref} from 'vue'
import {c7Button} from 'c7-plus'

const handleSubmit = async () => {
  // 异步操作
  alert('操作成功')
}

const onSuccess = (result) => {
  console.log('操作成功:', result)
  // 自定义成功处理逻辑
}

const onError = (error, message) => {
  console.log('操作失败:', error, message)
  // 自定义错误处理逻辑
}
</script>
