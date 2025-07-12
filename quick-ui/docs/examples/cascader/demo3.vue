<template>
  <c7-title label="基础用法" labelSize="h3"></c7-title>
  <c7-cascader :data-list="dictList" v-model="choose" style="margin-top: 10px"></c7-cascader>

  <p>选中的数据:{{ choose }}</p>


  <c7-title label="返回值用,隔开" labelSize="h3"></c7-title>
  <c7-cascader :data-list="dictList" v-model="choose2" :resultType='2' style="margin-top: 10px"></c7-cascader>

  <p>选中的数据:{{ choose2 }}</p>


  <c7-title label="只返回最后一层" labelSize="h3"></c7-title>
  <c7-cascader :data-list="dictList" v-model="choose3" :resultType=3 style="margin-top: 10px"></c7-cascader>

  <p>选中的数据:{{ choose3 }}</p>


</template>

<script setup>
import {ref} from 'vue'
import {c7Cascader, c7Title} from "c7-plus";

const choose = ref();


const choose2 = ref();


const choose3 = ref();

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
