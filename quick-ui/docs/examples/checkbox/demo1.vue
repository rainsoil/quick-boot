<template>
  <c7Checkbox
      v-model="selectedValues"
      :dataList="[
    { label: '苹果', value: 'apple' },
    { label: '香蕉', value: 'banana' },
    { label: '橙子', value: 'orange' }
  ]"
  />

  <span>选中的数据:{{ selectedValues }}</span>
</template>

<script setup>
import {ref} from 'vue'
import {c7Checkbox} from "c7-plus";
// 组件内 setup
const selectedValues = ref('')

</script>

<style scoped>

</style>
