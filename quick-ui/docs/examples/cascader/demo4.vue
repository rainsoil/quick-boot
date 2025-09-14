<template>
  <c7-cascader :fetchData="(parentId)=>getCascaderDataLazy(parentId)" v-model="choose" resultKey="data.data"
               lazy></c7-cascader>

  <p>选中的数据:{{ choose }}</p>
</template>

<script setup>
import {ref} from 'vue'
import {getCascaderDataLazy} from '../api.js'

const choose = ref();

</script>

<style scoped>

</style>
