<template>

</template>

<script setup>

// 参数
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
  // 上传的头部
  headers: {
    type: Object,
    default: () => {
      return {}
    }
  },
  // 上传的接口
  api: {
    type: String,
    default: '',
    required: true
  },
  // 上传的参数
  params: {
    type: Object,
    default: () => {
      return {}
    }
  },
  // 删除的接口
  deleteApi: {
    type: String,
    default: ''
  },
  // 最大允许上传的文件
  limit: {
    type: Number,
    default: 1
  },
  // 设置最大允许上传的文件大小(kb)
  size: {
    type: Number,
    default: 500
  },
  // 支持的文件类型(默认为图片)
  type: {
    type: String,
    default: 'image/jpeg,image/png,image/gif,image/bmp'
  }
})
</script>

<style scoped>


</style>
