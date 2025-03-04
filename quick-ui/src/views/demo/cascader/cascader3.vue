<!-- 懒加载-->

<template>
    <c7-title label="返回值为数组" label-size="h3"></c7-title>
    <c7-cascader api="/mockapi/cascader/lazy" v-model="choose" lazy></c7-cascader>
    <span>展示的数据:{{ choose }}</span>


  <c7-title label="返回值为数组(初始化数据)" label-size="h3"></c7-title>
  <c7-cascader api="/mockapi/cascader/lazy" parent-url="/mockapi/cascader/parentCode" v-model="choose2" result-type="3"
               lazy></c7-cascader>
  <span>展示的数据:{{ choose2 }}</span>

  <c7-title label="返回值为数组(初始化数据)" label-size="h3"></c7-title>
  <c7-cascader api="/mockapi/cascader/lazy" v-model="choose3" result-type="1"
               lazy></c7-cascader>
  <span>展示的数据:{{ choose3 }}</span>


  <c7-title label="返回值为数组(初始化数据)" label-size="h3"></c7-title>
  <c7-cascader api="/mockapi/cascader/lazy" v-model="choose4" result-type="2"
               lazy></c7-cascader>
  <span>展示的数据:{{ choose4 }}</span>
</template>
<script setup>
import {ref} from "vue";

const choose = ref();

const choose2 = ref("5");


const choose3 = ref(["1", "2", "5"])


const choose4 = ref("1,2,5")
</script>
