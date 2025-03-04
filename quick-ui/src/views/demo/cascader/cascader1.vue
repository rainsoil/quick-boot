<template>
  <c7-title label="返回值为数组" label-size="h3"></c7-title>
  <c7-cascader :data-list="dictList" v-model="choose"></c7-cascader>
  <span>展示的数据:{{ choose }}</span>


  <c7-title label="返回值为逗号分割" label-size="h3"></c7-title>
  <c7-cascader :data-list="dictList" v-model="choose2" result-type="2"></c7-cascader>
  <span>展示的数据:{{ choose2 }}</span>


  <c7-title label="返回值为逗号,初始化数据" label-size="h3"></c7-title>
  <c7-cascader :data-list="dictList" v-model="choose3" result-type="2"></c7-cascader>
  <span>展示的数据:{{ choose3 }}</span>


  <c7-title label="返回值为数组,初始化数据" label-size="h3"></c7-title>
  <c7-cascader :data-list="dictList" v-model="choose4" result-type="1"></c7-cascader>
  <span>展示的数据:{{ choose4 }}</span>

</template>
<script setup>
import {ref} from 'vue'

const choose = ref();

const choose2 = ref()

const choose3 = ref("1,6,8")

const choose4 = ref(["1", "6", "8"])
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
