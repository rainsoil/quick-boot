<template>
    <div>
      <c7-title label="不同绑定值类型对比" label-size="h3"></c7-title>
      
      <div style="margin-bottom: 20px;">
        <h4>1. 数组类型 (resultType=1)</h4>
        <c7-cascader 
          :data-list="cascaderData" 
          v-model="value1" 
          :resultType="1"
          style="margin-top: 10px; width: 300px;"
        ></c7-cascader>
        <p>选中的数据: {{ value1 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>2. 字符串类型 (resultType=2)</h4>
        <c7-cascader 
          :data-list="cascaderData" 
          v-model="value2" 
          :resultType="2"
          style="margin-top: 10px; width: 300px;"
        ></c7-cascader>
        <p>选中的数据: {{ value2 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>3. 单值类型 (resultType=3)</h4>
        <c7-cascader 
          :data-list="cascaderData" 
          v-model="value3" 
          :resultType="3"
          style="margin-top: 10px; width: 300px;"
        ></c7-cascader>
        <p>选中的数据: {{ value3 }}</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { c7Cascader, c7Title } from "c7-plus"
  
  const value1 = ref([])
  const value2 = ref('')
  const value3 = ref('')
  
  const cascaderData = [
    {
      value: "1",
      label: '电子产品',
      children: [
        {
          value: "11",
          label: '手机',
          children: [
            {value: "111", label: 'iPhone'},
            {value: "112", label: 'Samsung'},
            {value: "113", label: 'Huawei'},
          ],
        },
        {
          value: "12",
          label: '电脑',
          children: [
            {value: "121", label: 'MacBook'},
            {value: "122", label: 'ThinkPad'},
            {value: "123", label: 'Dell'},
          ],
        },
      ],
    },
    {
      value: "2",
      label: '服装',
      children: [
        {
          value: "21",
          label: '男装',
          children: [
            {value: "211", label: 'T恤'},
            {value: "212", label: '牛仔裤'},
            {value: "213", label: '衬衫'},
          ],
        },
        {
          value: "22",
          label: '女装',
          children: [
            {value: "221", label: '连衣裙'},
            {value: "222", label: '外套'},
            {value: "223", label: '裙子'},
          ],
        },
      ],
    }
  ]
  </script>