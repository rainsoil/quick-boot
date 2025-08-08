<template>
    <div>
      <c7-title label="数据格式化示例" label-size="h3"></c7-title>
      
      <c7-cascader 
        :fetchData="fetchRawData" 
        :dataFormatter="formatData"
        v-model="selectedValue" 
        style="margin-top: 10px; width: 300px;"
      ></c7-cascader>
      
      <p style="margin-top: 10px;">选中的数据: {{ selectedValue }}</p>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { c7Cascader, c7Title } from "c7-plus"
  
  const selectedValue = ref([])
  
  // 模拟获取原始数据
  const fetchRawData = () => {
    return new Promise((resolve) => {
      setTimeout(() => {
        // 原始数据格式
        const rawData = [
          {
            id: 1,
            name: '电子产品',
            subCategories: [
              {
                id: 11,
                name: '手机',
                subItems: [
                  { id: 111, name: 'iPhone' },
                  { id: 112, name: 'Samsung' },
                  { id: 113, name: 'Huawei' }
                ]
              },
              {
                id: 12,
                name: '电脑',
                subItems: [
                  { id: 121, name: 'MacBook' },
                  { id: 122, name: 'ThinkPad' },
                  { id: 123, name: 'Dell' }
                ]
              }
            ]
          },
          {
            id: 2,
            name: '服装',
            subCategories: [
              {
                id: 21,
                name: '男装',
                subItems: [
                  { id: 211, name: 'T恤' },
                  { id: 212, name: '牛仔裤' },
                  { id: 213, name: '衬衫' }
                ]
              },
              {
                id: 22,
                name: '女装',
                subItems: [
                  { id: 221, name: '连衣裙' },
                  { id: 222, name: '外套' },
                  { id: 223, name: '裙子' }
                ]
              }
            ]
          }
        ]
        
        resolve({
          data: {
            data: rawData
          }
        })
      }, 300)
    })
  }
  
  // 数据格式化函数
  const formatData = (rawList) => {
    const formatNode = (node) => {
      return {
        value: node.id,
        label: node.name,
        children: node.subCategories ? node.subCategories.map(formatNode) : 
                  node.subItems ? node.subItems.map(item => ({
                    value: item.id,
                    label: item.name
                  })) : undefined
      }
    }
    
    return rawList.map(formatNode)
  }
  </script>