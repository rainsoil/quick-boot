<template>
  <c7Checkbox
      v-model="selectedValues"
      :fetchData="() =>getSelectData()"
      result-key="data.data"
  />

  <span>选中的数据:{{ selectedValues }}</span>
</template>

<script setup>
import {ref} from 'vue'
import {getSelectData} from '../api.js'
// 组件内 setup
const selectedValues = ref('')

</script>

<style scoped>

</style>
