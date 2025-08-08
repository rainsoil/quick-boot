<template>
    <div>
      <c7-title label="默认值设置示例" label-size="h3"></c7-title>
      
      <div style="margin-bottom: 20px;">
        <h4>1. 单个日期默认值</h4>
        <c7-date-picker 
          v-model="value1" 
          type="date" 
          placeholder="请选择日期"
          style="width: 200px;"
        />
        <p>默认值: 2024-01-01</p>
        <p>选中的值: {{ value1 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>2. 日期范围默认值（字符串格式）</h4>
        <c7-date-picker 
          v-model="value2" 
          type="daterange" 
          placeholder="请选择日期范围"
          style="width: 300px;"
        />
        <p>默认值: "2024-01-01,2024-12-31"</p>
        <p>选中的值: {{ value2 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>3. 日期范围默认值（数组格式）</h4>
        <c7-date-picker 
          v-model="value3" 
          type="daterange" 
          :rangeMerge="false"
          placeholder="请选择日期范围"
          style="width: 300px;"
        />
        <p>默认值: ["2024-01-01", "2024-12-31"]</p>
        <p>选中的值: {{ value3 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>4. 日期时间默认值</h4>
        <c7-date-picker 
          v-model="value4" 
          type="datetime" 
          placeholder="请选择日期时间"
          style="width: 200px;"
        />
        <p>默认值: 2024-01-01 12:00:00</p>
        <p>选中的值: {{ value4 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>5. 年份默认值</h4>
        <c7-date-picker 
          v-model="value5" 
          type="year" 
          placeholder="请选择年份"
          style="width: 200px;"
        />
        <p>默认值: 2024</p>
        <p>选中的值: {{ value5 }}</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { c7DatePicker, c7Title } from "c7-plus"
  
  const value1 = ref('2024-01-01')
  const value2 = ref('2024-01-01,2024-12-31')
  const value3 = ref(['2024-01-01', '2024-12-31'])
  const value4 = ref('2024-01-01 12:00:00')
  const value5 = ref('2024')
  </script>