<!-- 对按钮的封装-->
<template>

  <el-button v-if="clickEvent" v-bind="$attrs">
    {{ props.label }}
    <slot></slot>
  </el-button>

  <el-button v-else v-bind="$attrs" @click="buttonClick">
    <slot></slot>
    {{ props.label }}
  </el-button>
</template>

<script setup>
import {ref, computed, useSlots, defineOptions,watch} from 'vue';
import pkg from 'lodash';
const { debounce } = pkg;

import {ElMessage, ElMessageBox} from 'element-plus'
import   {injectService} from  '../../../service/injectService.ts'

defineOptions({
  name: 'c7Button'
})
// 成功之后的回调函数
const emit = defineEmits(['successCallback'])

const props = defineProps({
  clickEvent: {
    type: Boolean,
    default: false,
  },
  params: {
    type: Object,
    default: () => ({}), // 请求参数
  },
  url: {
    type: String,
    required: false,
    default: "",
  },
  // 按钮名称
  label: {
    type: String,
    default: "",
    required: true
  },
  // 是否弹出确认框
  confirm: {
    type: Boolean,
    default: false,
  },
  // 确认框提示内容
  confirmMessage: {
    type: String,
    default: "",
  },
  // 是否开启成功之后回调
  isSuccessCallback: {
    type: Boolean,
    default: false,
  },

  // 成功之后提示信息(当isSuccessCallback为false的时候,才启动)
  successMessage: {
    type: String,
    default: "",
  },
  // 跳转的地址
  href: {
    type: String,
    default: "",
  },
})

let isProcessing = ref(false); // 用于防止重复点击

const buttonClick = debounce(() => {
  if (isProcessing.value) return; // 如果已经有请求在处理，直接返回

  if (props.confirm) {
    confirm();
  } else {
    sendRequest()
  }
}, 300); // 假设防抖间隔为300毫秒

const sendRequest = () => {
  isProcessing.value = true; // 开始处理请求时设置为true
  if (!props.href) {
    injectService.postRequest(props.url, props.params).then(res => {
      // 请求完成后，重置状态
      isProcessing.value = false;
      if (props.isSuccessCallback) {
        emit("successCallback", res);
      } else {
        ElMessage.success(props.successMessage)
      }
    }).catch(err => {
      // 在请求失败的情况下也要记得重置状态
      isProcessing.value = false;
      console.error('请求失败:', err);
    });
  } else {
    injectService.openNewTab(props.href, props.label);
    // 对于打开新标签页的情况，立即重置状态
    isProcessing.value = false;
  }
}

const confirm = () => {
  ElMessageBox.confirm(props.confirmMessage, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    sendRequest();
  }).catch(() => {
    // 确认框取消时也需要重置状态
    isProcessing.value = false;
  });
}


const click = (event) => {
  console.log("click", event)
}

defineExpose({
  click
})
</script>

