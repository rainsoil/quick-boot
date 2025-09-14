<template>
  <c7-cascader :data-list="dictList" v-model="choose"></c7-cascader>

  <p>选中的数据:{{ choose}}</p>
</template>

<script setup>
import {ref} from 'vue'
const choose = ref();

const dictList = [
  {
    value: "1",
    label: 'Asia',
    children: [
      {
        value: "2",
        label: 'China',
        children: [
          {value: "3", label: 'Beijing'},
          {value: "4", label: 'Shanghai'},
          {value: "5", label: 'Hangzhou'},
        ],
      },
      {
        value: "6",
        label: 'Japan',
        children: [
          {value: "7", label: 'Tokyo'},
          {value: "8", label: 'Osaka'},
          {value: "9", label: 'Kyoto'},
        ],
      },
      {
        value: "10",
        label: 'Korea',
        children: [
          {value: "11", label: 'Seoul'},
          {value: "12", label: 'Busan'},
          {value: "13", label: 'Taegu'},
        ],
      },
    ],
  }]
</script>

<style scoped>

</style>
