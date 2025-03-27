<template>


  <c7-checkbox v-model="choose" :data-list="dictList"></c7-checkbox>

  <span>选中的数据:{{ choose}}</span>

</template>

<script setup>


import {ref} from "vue";
import {c7Checkbox} from "c7-plus";
const choose = ref();
const dictList = ref([{
  label: "中学",
  value: "0"
},
  {
    label: "小学",
    value: "1"
  },


])
</script>
