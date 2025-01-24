<template>

</template>

<script setup>

// 设置参数
const props = defineProps({
  // 栅栏占据的列数
  span: {
    type: String,
    default: 8
  },
  // 表单域标签的款第
  labelWidth: {
    type: String,
    default: '140'
  },
  // 标签文本
  label: {
    type: String,
    default: ''
  },
  // 输入框占位文本
  placeholder: {
    type: String,
    default: '请选择'
  },
  // 属性名称
  prop: {
    type: String,
    default: ''
  },
  // 绑定值
  modelValue: {
    type: Object,
    default: ''
  },
  // 接口
  api: {
    type: String,
    default: ''
  },
  // 参数
  params: {
    type: Object,
    default: {}
  }
})
</script>

<style scoped>

</style>
