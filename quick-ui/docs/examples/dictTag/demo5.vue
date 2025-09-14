<template>
    <div>
      <h4>自定义样式</h4>
      <c7-dict-tag
          :options="dictList" 
          v-model="customValue"
      />
    </div>
  </template>
  
  <script setup>
  import {ref} from 'vue'
  
  const customValue = ref(['1', '2'])
  
  const dictList = ref([
    {
      label: '自定义样式1',
      value: '1',
      elTagType: 'success',
      elTagClass: 'custom-tag-1'
    },
    {
      label: '自定义样式2',
      value: '2',
      elTagType: 'warning',
      elTagClass: 'custom-tag-2'
    },
    {
      label: '普通标签',
      value: '3',
      elTagType: 'info'
    }
  ])
  </script>
  
  <style scoped>
  .custom-tag-1 {
    background-color: #f0f9ff !important;
    border-color: #3b82f6 !important;
    color: #1e40af !important;
  }
  
  .custom-tag-2 {
    background-color: #fef3c7 !important;
    border-color: #f59e0b !important;
    color: #92400e !important;
  }
  </style>