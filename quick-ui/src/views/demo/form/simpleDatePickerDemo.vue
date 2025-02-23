<!-- 日期-->
<template>
  <c7-date-picker v-model="date" placeholder="请选择日期" span="12" label-width="80" label="日期" @change="dateChange"/>
  <span>选择的日期:{{ date }}</span> <br>
  <span>change的值为:{{dateChangeValue}}</span>
</template>

<script setup>
import {ref} from 'vue'

const date = ref('')
const  dateChangeValue = ref();
const  dateChange  =(val)=>{
  console.log(val)
  dateChangeValue.value = val
}

</script>
