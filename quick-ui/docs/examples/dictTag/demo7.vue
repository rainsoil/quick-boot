<template>
    <div>
      <h4>条件渲染</h4>
      <div>
        <el-button @click="toggleStatus">切换状态</el-button>
        <c7-dict-tag
          v-if="showTag"
          :options="statusOptions" 
          v-model="userStatus"
        />
        <span v-else>用户已禁用，不显示状态标签</span>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed } from 'vue'
  import { c7DictTag } from 'c7-plus'
  
  const userStatus = ref('1')
  const showTag = computed(() => userStatus.value !== '0')
  
  const statusOptions = ref([
    { label: '启用', value: '1', elTagType: 'success' },
    { label: '禁用', value: '0', elTagType: 'danger' }
  ])
  
  const toggleStatus = () => {
    userStatus.value = userStatus.value === '1' ? '0' : '1'
  }
  </script>
  
  <style scoped>
  </style>