<!--

默认值
-->

<template>

  <c7Title label="静态数据"></c7Title>
  <p>结果类型(resultType) 为数组 </p>
  <c7-cascader :data-list="dictList" v-model="choose"></c7-cascader>
  <p>选中的数据:{{ choose }}</p>

  <c7Title label="静态数据"></c7Title>
  <p>结果类型(resultType) 为用逗号隔开 </p>
  <c7-cascader :data-list="dictList" v-model="choose2" :resultType=2></c7-cascader>
  <p>选中的数据:{{ choose2 }}</p>

  <c7Title label="静态数据"></c7Title>
  <p>结果类型(resultType) 为最后一个元素 </p>
  <c7-cascader :data-list="dictList" v-model="choose3" :resultType=3></c7-cascader>
  <p>选中的数据:{{ choose3 }}</p>

  <c7Title label="动态数据(非懒加载)"></c7Title>
  <p>结果类型(resultType) 为最后一个元素 </p>
  <c7-cascader :fetchData="()=>getCascaderData()" v-model="choose4" :resultType=3 resultKey="data.data"></c7-cascader>

  <p>选中的数据:{{ choose4 }}</p>

  <c7Title label="动态数据(懒加载)"></c7Title>
  <p>结果类型(resultType) 为最后一个元素 </p>
  <c7-cascader :fetchData="(parentId)=>getCascaderDataLazy(parentId)" v-model="choose5" resultKey="data.data" :resultType=3
               :parentNodeFetchFunction="(value) =>parentNodeFetchFunction(value)"
               lazy></c7-cascader>

  <p>选中的数据:{{ choose5 }}</p>

  <c7Title label="动态数据(懒加载)"></c7Title>
  <p>结果类型(resultType) 用,隔开 </p>
  <c7-cascader :fetchData="(parentId)=>getCascaderDataLazy(parentId)" v-model="choose6" resultKey="data.data" :resultType=2
               :parentNodeFetchFunction="(value) =>parentNodeFetchFunction(value)"
               lazy></c7-cascader>

  <p>选中的数据:{{ choose6 }}</p>

</template>

<script setup>
import {getCascaderData, getCascaderDataLazy, parentNodeFetchFunction} from '../api.js'
import {ref, onMounted} from 'vue'

const choose = ref(["1", "2", "3"]);
const choose2 = ref("1,2,3");

const choose3 = ref("3");

const choose4 = ref("3");

const choose5 = ref("5");

const choose6 = ref("1,2,5");
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
