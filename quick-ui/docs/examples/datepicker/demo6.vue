<template>
    <div>
      <c7-title label="自定义格式示例" label-size="h3"></c7-title>
      
      <div style="margin-bottom: 20px;">
        <h4>1. 自定义显示格式</h4>
        <c7-date-picker 
          v-model="value1" 
          type="date" 
          format="YYYY年MM月DD日"
          placeholder="请选择日期"
          style="width: 200px;"
        />
        <p>显示格式: YYYY年MM月DD日</p>
        <p>选中的值: {{ value1 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>2. 自定义绑定值格式</h4>
        <c7-date-picker 
          v-model="value2" 
          type="date" 
          value-format="YYYY/MM/DD"
          placeholder="请选择日期"
          style="width: 200px;"
        />
        <p>绑定值格式: YYYY/MM/DD</p>
        <p>选中的值: {{ value2 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>3. 自定义显示和绑定格式</h4>
        <c7-date-picker 
          v-model="value3" 
          type="datetime" 
          format="YYYY年MM月DD日 HH时mm分ss秒"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择日期时间"
          style="width: 250px;"
        />
        <p>显示格式: YYYY年MM月DD日 HH时mm分ss秒</p>
        <p>绑定值格式: YYYY-MM-DD HH:mm:ss</p>
        <p>选中的值: {{ value3 }}</p>
      </div>
      
      <div style="margin-bottom: 20px;">
        <h4>4. 自定义范围选择格式</h4>
        <c7-date-picker 
          v-model="value4" 
          type="daterange" 
          format="MM/DD/YYYY"
          value-format="YYYY-MM-DD"
          placeholder="请选择日期范围"
          style="width: 300px;"
        />
        <p>显示格式: MM/DD/YYYY</p>
        <p>绑定值格式: YYYY-MM-DD</p>
        <p>选中的值: {{ value4 }}</p>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  const value1 = ref('')
  const value2 = ref('')
  const value3 = ref('')
  const value4 = ref('')
  </script>