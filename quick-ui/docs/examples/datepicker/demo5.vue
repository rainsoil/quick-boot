<template>
    <div>
      <c7-title label="不同日期类型示例" label-size="h3"></c7-title>
      
      <div style="margin-bottom: 20px;">
        <h4>1. 年份选择器</h4>
        <c7-date-picker 
          v-model="yearValue" 
          type="year" 
          placeholder="请选择年份"
          style="width: 200px;"
        />
        <p>选中的年份: {{ yearValue }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>2. 月份选择器</h4>
        <c7-date-picker 
          v-model="monthValue" 
          type="month" 
          placeholder="请选择月份"
          style="width: 200px;"
        />
        <p>选中的月份: {{ monthValue }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>3. 日期时间选择器</h4>
        <c7-date-picker 
          v-model="datetimeValue" 
          type="datetime" 
          placeholder="请选择日期时间"
          style="width: 200px;"
        />
        <p>选中的日期时间: {{ datetimeValue }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>4. 日期时间范围选择器</h4>
        <c7-date-picker 
          v-model="datetimerangeValue" 
          type="datetimerange" 
          placeholder="请选择日期时间范围"
          style="width: 300px;"
        />
        <p>选中的日期时间范围: {{ datetimerangeValue }}</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  const yearValue = ref('')
  const monthValue = ref('')
  const datetimeValue = ref('')
  const datetimerangeValue = ref('')
  </script>