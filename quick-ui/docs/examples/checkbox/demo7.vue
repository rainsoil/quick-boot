<template>
    <div>
      <c7-title label="异步数据获取示例" label-size="h3"></c7-title>
      
      <c7-checkbox 
        v-model="selectedValues" 
        :fetchData="fetchAsyncData"
        style="margin-top: 10px;"
      ></c7-checkbox>
      
      <p style="margin-top: 10px;">选中的数据: {{ selectedValues }}</p>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  const selectedValues = ref('')
  
  // 模拟异步数据获取
  const fetchAsyncData = () => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve({
          data: [
            { label: 'JavaScript', value: 'js' },
            { label: 'TypeScript', value: 'ts' },
            { label: 'Vue.js', value: 'vue' },
            { label: 'React', value: 'react' },
            { label: 'Angular', value: 'angular' },
            { label: 'Node.js', value: 'node' }
          ]
        })
      }, 500) // 模拟网络延迟
    })
  }
  </script>