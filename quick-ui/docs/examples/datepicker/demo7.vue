<template>
    <div>
      <c7-title label="范围合并功能对比" label-size="h3"></c7-title>
      
      <div style="margin-bottom: 20px;">
        <h4>1. 合并为字符串 (rangeMerge=true)</h4>
        <c7-date-picker 
          v-model="value1" 
          type="daterange" 
          :rangeMerge="true"
          placeholder="请选择日期范围"
          style="width: 300px;"
        />
        <p>选中的值: {{ value1 }}</p>
        <p>类型: {{ typeof value1 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>2. 保持数组格式 (rangeMerge=false)</h4>
        <c7-date-picker 
          v-model="value2" 
          type="daterange" 
          :rangeMerge="false"
          placeholder="请选择日期范围"
          style="width: 300px;"
        />
        <p>选中的值: {{ value2 }}</p>
        <p>类型: {{ typeof value2 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>3. 日期时间范围合并</h4>
        <c7-date-picker 
          v-model="value3" 
          type="datetimerange" 
          :rangeMerge="true"
          placeholder="请选择日期时间范围"
          style="width: 350px;"
        />
        <p>选中的值: {{ value3 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>4. 日期时间范围数组</h4>
        <c7-date-picker 
          v-model="value4" 
          type="datetimerange" 
          :rangeMerge="false"
          placeholder="请选择日期时间范围"
          style="width: 350px;"
        />
        <p>选中的值: {{ value4 }}</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  const value1 = ref('')
  const value2 = ref([])
  const value3 = ref('')
  const value4 = ref([])
  </script>