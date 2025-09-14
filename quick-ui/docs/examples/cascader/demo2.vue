<template>
  <c7-cascader :fetchData="()=>getCascaderData()" v-model="choose" resultKey="data.data"></c7-cascader>

  <p>选中的数据:{{ choose }}</p>
</template>

<script setup>
import {ref} from 'vue'
import {getCascaderData} from '../api.js'

const choose = ref();

</script>

<style scoped>

</style>
