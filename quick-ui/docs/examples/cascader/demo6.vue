<template>
    <div>
      <c7-title label="懒加载级联选择器" label-size="h3"></c7-title>
      
      <c7-cascader 
        :fetchData="fetchLazyData" 
        v-model="selectedValue" 
        :lazy="true"
        :resultType="1"
        style="margin-top: 10px; width: 300px;"
      ></c7-cascader>
      
      <p style="margin-top: 10px;">选中的数据: {{ selectedValue }}</p>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { c7Cascader, c7Title } from "c7-plus"
  
  const selectedValue = ref([])
  
  // 模拟懒加载数据获取函数
  const fetchLazyData = (parentId) => {
    return new Promise((resolve) => {
      setTimeout(() => {
        let data = []
        
        if (parentId === -1) {
          // 根节点数据
          data = [
            { value: '1', label: '北京市', leaf: false },
            { value: '2', label: '上海市', leaf: false },
            { value: '3', label: '广东省', leaf: false }
          ]
        } else if (parentId === '1') {
          // 北京市下级数据
          data = [
            { value: '11', label: '朝阳区', leaf: true },
            { value: '12', label: '海淀区', leaf: true },
            { value: '13', label: '西城区', leaf: true }
          ]
        } else if (parentId === '2') {
          // 上海市下级数据
          data = [
            { value: '21', label: '浦东新区', leaf: true },
            { value: '22', label: '黄浦区', leaf: true },
            { value: '23', label: '静安区', leaf: true }
          ]
        } else if (parentId === '3') {
          // 广东省下级数据
          data = [
            { value: '31', label: '广州市', leaf: false },
            { value: '32', label: '深圳市', leaf: false },
            { value: '33', label: '珠海市', leaf: false }
          ]
        } else if (parentId === '31') {
          // 广州市下级数据
          data = [
            { value: '311', label: '天河区', leaf: true },
            { value: '312', label: '越秀区', leaf: true },
            { value: '313', label: '荔湾区', leaf: true }
          ]
        } else if (parentId === '32') {
          // 深圳市下级数据
          data = [
            { value: '321', label: '南山区', leaf: true },
            { value: '322', label: '福田区', leaf: true },
            { value: '323', label: '罗湖区', leaf: true }
          ]
        }
        
        resolve({
          data: {
            data: data
          }
        })
      }, 500) // 模拟网络延迟
    })
  }
  </script>