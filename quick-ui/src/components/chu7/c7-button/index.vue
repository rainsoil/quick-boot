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
import {ElMessage, ElMessageBox} from 'element-plus'
import BaseService from "@/service/baseService.js";
import c7Interface from "@/components/chu7/c7-interface.js"
// 成功之后的回调函数
const emit = defineEmits(['successCallback'])

const props = defineProps({
  clickEvent: {
    type: Boolean,
    default: false,
  },
  datas: {
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

const buttonClick = () => {
  // 如果需要提示框的话
  if (props.confirm) {
    confirm();
  } else {
    sendRequest()
  }
}

const sendRequest = () => {
  if (!props.href) {
    BaseService.post(props.url, props.datas).then(res => {
      if (props.isSuccessCallback) {
        // 调用父组件的方法
        const msgs = res
        emit("successCallback", msgs);
      } else {
        ElMessage.success(props.successMessage)
      }
    });
  } else {
    c7Interface.openNewTab(props.href, props.label);
  }

}

const confirm = () => {
  ElMessageBox.confirm(props.confirmMessage, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    sendRequest()
  }).catch(() => {
    // ElMessage({
    //   type: 'info',
    //   message: '已取消删除'
    // });
  });
}


const click = (event) => {
  console.log("click", event)
}

defineExpose({
  click
})
</script>

