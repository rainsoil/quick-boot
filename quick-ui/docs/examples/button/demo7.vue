<template>
  <c7-button
      :clickFunction="handleSubmit"
      :checkSuccess="customCheckSuccess"
      successMessage="操作成功"
  >
    提交
  </c7-button>
</template>

<script setup>
const customCheckSuccess = (res) => {
  // 自定义成功判断逻辑
  return res.status === 'success' && res.data
}

const handleSubmit = async () => {
  alert("提交")
}
</script>
