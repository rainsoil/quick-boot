<template>

</template>
<script setup>
//参数
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
  // 日期类型 year / month / date / week / datetime
  type: {
    type: String,
    default: 'date'
  },
  // 是否触发 on-change事件
  isChange: {
    type: Boolean,
    default: false
  },
  format: {
    type: String,
    default: 'yyyy-MM-dd'
  },
  // 日期选择器特有的参数
  pickerOptions: {
    type: Object,
    default: () => {
      return {}
    }
  },
  valueFormat: {
    type: String,
    default: 'yyyy-MM-dd'
  },
})
</script>
<style lang="scss" scoped>
</style>
