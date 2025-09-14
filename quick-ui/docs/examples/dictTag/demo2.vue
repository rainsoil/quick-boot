<template>
    <div>
      <h4>数组值展示</h4>
      <c7-dict-tag
          :options="dictList" 
          v-model="arrayValue"
      />
    </div>
  </template>
  
  <script setup>
  import {ref} from 'vue'
  
  const arrayValue = ref(['1', '3', '5'])
  
  const dictList = ref([
    {
      label: 'default',
      value: '1',
      elTagType: 'default'
    },
    {
      label: 'primary',
      value: '2',
      elTagType: 'primary'
    },
    {
      label: 'success',
      value: '3',
      elTagType: 'success'
    },
    {
      label: 'info',
      value: '4',
      elTagType: 'info'
    },
    {
      label: 'warning',
      value: '5',
      elTagType: 'warning'
    },
    {
      label: 'danger',
      value: '6',
      elTagType: 'danger'
    }
  ])
  </script>
  
  <style scoped>
  </style>