<template>
  <c7Checkbox
      v-model="selectedValues"
      :dataList="[
    { label: '苹果', value: 'apple' },
    { label: '香蕉', value: 'banana' },
    { label: '橙子', value: 'orange' }
  ]"
  />

  <span>选中的数据(结果用,隔开):{{ selectedValues }}</span>


  <c7Checkbox
      v-model="selectedValues2"
      :separator="false"
      :dataList="[
    { label: '苹果', value: 'apple' },
    { label: '香蕉', value: 'banana' },
    { label: '橙子', value: 'orange' }
  ]"
  />

  <span>选中的数据（结果为数组）:{{ selectedValues2 }}</span>
</template>

<script setup>
import {ref} from 'vue'
import {c7Checkbox} from "c7-plus";
// 组件内 setup
const selectedValues = ref('')

const selectedValues2 = ref('')

</script>

<style scoped>

</style>
