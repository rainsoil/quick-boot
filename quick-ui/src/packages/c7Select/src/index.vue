<!-- 下拉选-->

<template>


</template>
<script setup>
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
  //   调用后端接口触发的参数
  params: {
    type: Object,
    default: {}
  },
  // 自定义接口, 返回值为 { "label":xx,value:xx,disable:true/false}
  api: {
    type: String,
    default: ''
  },
  // 字典值type, 当指定了type时, 会从字典中获取字典值
  dictType: {
    type: String,
    default: ''
  },
  // 是否触发 on-change事件
  isChange: {
    type: Boolean,
    default: false
  },
  // 是否多选
  multiple: {
    type: Boolean,
    default: false
  },
  // 远程搜索
  remote: {
    type: Boolean,
    default: false
  },



})
</script>

<style scoped>
</style>
