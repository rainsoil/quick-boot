<template>
    <div>
      <h4>完整示例</h4>
      
      <!-- 基础用法 -->
      <div style="margin-bottom: 20px;">
        <h5>基础用法：</h5>
        <c7-dict-tag
          :options="statusOptions" 
          v-model="userStatus"
        />
      </div>
      
      <!-- 数组值 -->
      <div style="margin-bottom: 20px;">
        <h5>数组值：</h5>
        <c7-dict-tag
          :options="roleOptions" 
          v-model="userRoles"
        />
      </div>
      
      <!-- 自定义样式 -->
      <div style="margin-bottom: 20px;">
        <h5>自定义样式：</h5>
        <c7-dict-tag
          :options="customOptions" 
          v-model="customValue"
        />
      </div>
      
      <!-- 不显示未匹配值 -->
      <div style="margin-bottom: 20px;">
        <h5>不显示未匹配值：</h5>
        <c7-dict-tag
          :options="limitedOptions" 
          v-model="unmatchValue"
          :showValue="false"
        />
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { c7DictTag } from 'c7-plus'
  
  // 用户状态
  const userStatus = ref('1')
  const statusOptions = ref([
    { label: '启用', value: '1', elTagType: 'success' },
    { label: '禁用', value: '0', elTagType: 'danger' }
  ])
  
  // 用户角色（数组）
  const userRoles = ref(['admin', 'user'])
  const roleOptions = ref([
    { label: '管理员', value: 'admin', elTagType: 'danger' },
    { label: '普通用户', value: 'user', elTagType: 'primary' },
    { label: '访客', value: 'guest', elTagType: 'info' }
  ])
  
  // 自定义样式
  const customValue = ref(['vip'])
  const customOptions = ref([
    { 
      label: 'VIP用户', 
      value: 'vip', 
      elTagType: 'warning',
      elTagClass: 'vip-tag'
    }
  ])
  
  // 未匹配值处理
  const unmatchValue = ref(['1', '999'])
  const limitedOptions = ref([
    { label: '选项1', value: '1', elTagType: 'success' }
  ])
  </script>
  
  <style scoped>
  .vip-tag {
    background: linear-gradient(45deg, #ff6b6b, #feca57) !important;
    color: white !important;
    border: none !important;
  }
  </style>