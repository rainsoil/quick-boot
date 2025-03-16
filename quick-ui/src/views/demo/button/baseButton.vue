<template>

  <div class="app-container">
    <c7-button label="点击触发后端事件" type="primary" url="/mockapi/crud/click" success-message="调用成功"></c7-button>

    <c7-button label="点击触发前端事件" type="primary" @click="clickFun" click-event></c7-button>
    <c7-button label="点击触发路由跳转" type="primary" href="https://baidu.com"></c7-button>
  </div>
</template>
<script setup>
// 现代构建工具（Vite/webpack）会优先使用 ES 模块
import {tableView} from 'c7-plus/hooks/tableView';
const clickFun = () => {
  alert("触发前端事件")
}


</script>
