<template>
  <c7-button label="点击触发后端事件" type="primary"></c7-button>

  <c7-button label="点击触发前端事件" type="primary" @click="clickFun" click-event></c7-button>
  <c7-button label="点击触发路由跳转" type="primary"></c7-button>
</template>
<script setup>

const clickFun = () => {
  alert("触发前端事件")
}



</script>
