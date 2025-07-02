<template>
  <c7Checkbox
      v-model="selectedValues"
      button
      @change="selectedValuesChange"
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

const selectedValuesChange = (value) => {
  console.log("点击的数据", value)
}
</script>

<style scoped>

</style>
