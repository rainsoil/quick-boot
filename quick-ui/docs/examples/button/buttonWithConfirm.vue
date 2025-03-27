<template>
  <c7-button label="点击触发确认框" type="primary" confirm confirm-message="确认点击吗？" is-success-callback

             url="/mockapi/crud/click" success-message="调用成功"

  ></c7-button>


  <c7-button label="点击触发自定义回调" type="primary" confirm confirm-message="确认点击吗？" is-success-callback
             @success-callback="successCallback"
             url="/mockapi/crud/click"

  ></c7-button>
</template>

<script setup lang="ts">
import {c7Button} from "c7-plus";
const successCallback = (res) => {
  alert("回调:" + JSON.stringify(res))
}
</script>
