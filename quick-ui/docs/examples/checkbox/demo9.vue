<template>
    <div>
      <c7-title label="不同绑定值类型对比" label-size="h3"></c7-title>
      
      <div style="margin-bottom: 20px;">
        <h4>1. 字符串绑定 (separator=true)</h4>
        <c7-checkbox 
          v-model="value1" 
          :dataList="checkboxData"
          :separator="true"
          style="margin-top: 10px;"
        ></c7-checkbox>
        <p>选中的数据: {{ value1 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>2. 数组绑定 (separator=false)</h4>
        <c7-checkbox 
          v-model="value2" 
          :dataList="checkboxData"
          :separator="false"
          style="margin-top: 10px;"
        ></c7-checkbox>
        <p>选中的数据: {{ value2 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>3. 带全选功能的字符串绑定</h4>
        <c7-checkbox 
          v-model="value3" 
          :dataList="checkboxData"
          :separator="true"
          :indeterminate="true"
          style="margin-top: 10px;"
        ></c7-checkbox>
        <p>选中的数据: {{ value3 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>4. 按钮样式的数组绑定</h4>
        <c7-checkbox 
          v-model="value4" 
          :dataList="checkboxData"
          :separator="false"
          :button="true"
          style="margin-top: 10px;"
        ></c7-checkbox>
        <p>选中的数据: {{ value4 }}</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { c7Checkbox, c7Title } from "c7-plus"
  
  const value1 = ref('')
  const value2 = ref([])
  const value3 = ref('')
  const value4 = ref([])
  
  const checkboxData = [
    { label: '选项A', value: 'a' },
    { label: '选项B', value: 'b' },
    { label: '选项C', value: 'c' },
    { label: '选项D', value: 'd' }
  ]
  </script>