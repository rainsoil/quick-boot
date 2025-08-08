<template>
    <div>
      <c7-title label="数据格式化示例" label-size="h3"></c7-title>
      
      <c7-checkbox 
        v-model="selectedValues" 
        :fetchData="fetchRawData"
        :dataFormatter="formatData"
        style="margin-top: 10px;"
      ></c7-checkbox>
      
      <p style="margin-top: 10px;">选中的数据: {{ selectedValues }}</p>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { c7Checkbox, c7Title } from "c7-plus"
  
  const selectedValues = ref('')
  
  // 模拟获取原始数据
  const fetchRawData = () => {
    return new Promise((resolve) => {
      setTimeout(() => {
        // 原始数据格式
        const rawData = [
          { id: 1, name: '前端开发', category: '技术' },
          { id: 2, name: '后端开发', category: '技术' },
          { id: 3, name: 'UI设计', category: '设计' },
          { id: 4, name: '产品经理', category: '管理' },
          { id: 5, name: '测试工程师', category: '技术' },
          { id: 6, name: '运维工程师', category: '技术' }
        ]
        
        resolve({
          data: rawData
        })
      }, 300)
    })
  }
  
  // 数据格式化函数
  const formatData = (rawList) => {
    return rawList.map(item => ({
      label: `${item.name} (${item.category})`,
      value: item.id
    }))
  }
  </script>