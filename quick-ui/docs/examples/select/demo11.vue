<template>
  <div>
    <h2>高级选择功能示例</h2>
    
    <div class="demo-section">
      <h3>异步数据加载</h3>
      <c7-select 
        v-model="asyncValue" 
        :fetchData="getAsyncData"
        placeholder="请选择（异步加载）"
        style="width: 300px;"
        @change="handleAsyncChange"
      />
      <div class="result">选中的值：{{ asyncValue }}</div>
    </div>

    <div class="demo-section">
      <h3>远程搜索</h3>
      <c7-select 
        v-model="remoteValue" 
        :fetchData="getRemoteData"
        :remote="true"
        placeholder="请输入关键词搜索"
        style="width: 300px;"
        @change="handleRemoteChange"
      />
      <div class="result">选中的值：{{ remoteValue }}</div>
    </div>

    <div class="demo-section">
      <h3>数据格式化</h3>
      <c7-select 
        v-model="formattedValue" 
        :fetchData="getRawData"
        :dataFormatter="formatData"
        placeholder="请选择（格式化数据）"
        style="width: 300px;"
        @change="handleFormattedChange"
      />
      <div class="result">选中的值：{{ formattedValue }}</div>
    </div>

    <div class="demo-section">
      <h3>自定义字段名</h3>
      <c7-select 
        v-model="customValue" 
        :dataList="customDataList"
        labelKey="name"
        valueKey="id"
        placeholder="请选择（自定义字段）"
        style="width: 300px;"
        @change="handleCustomChange"
      />
      <div class="result">选中的值：{{ customValue }}</div>
    </div>

    <div class="demo-section">
      <h3>多选模式（数组存储）</h3>
      <c7-select 
        v-model="multipleArrayValue" 
        :dataList="multipleOptions"
        :multiple="true"
        :separator="false"
        placeholder="请选择多个选项"
        style="width: 300px;"
        @change="handleMultipleArrayChange"
      />
      <div class="result">选中的值：{{ multipleArrayValue }}</div>
    </div>

    <div class="demo-section">
      <h3>多选模式（字符串存储）</h3>
      <c7-select 
        v-model="multipleStringValue" 
        :dataList="multipleOptions"
        :multiple="true"
        :separator="true"
        placeholder="请选择多个选项"
        style="width: 300px;"
        @change="handleMultipleStringChange"
      />
      <div class="result">选中的值：{{ multipleStringValue }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { c7Select } from 'c7-plus'

// 异步数据加载
const asyncValue = ref('')
const handleAsyncChange = (value) => {
  console.log('异步选择变化：', value)
}

const getAsyncData = () => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        code: 200,
        data: [
          { label: '异步选项1', value: 'async1' },
          { label: '异步选项2', value: 'async2' },
          { label: '异步选项3', value: 'async3' },
          { label: '异步选项4', value: 'async4' }
        ]
      })
    }, 1000)
  })
}

// 远程搜索
const remoteValue = ref('')
const handleRemoteChange = (value) => {
  console.log('远程搜索变化：', value)
}

const getRemoteData = (params) => {
  console.log('远程搜索参数：', params)
  return new Promise((resolve) => {
    setTimeout(() => {
      const query = params.query || ''
      const filteredData = [
        { label: '苹果', value: 'apple' },
        { label: '香蕉', value: 'banana' },
        { label: '橙子', value: 'orange' },
        { label: '葡萄', value: 'grape' },
        { label: '草莓', value: 'strawberry' }
      ].filter(item => 
        item.label.toLowerCase().includes(query.toLowerCase())
      )
      resolve({
        code: 200,
        data: filteredData
      })
    }, 500)
  })
}

// 数据格式化
const formattedValue = ref('')
const handleFormattedChange = (value) => {
  console.log('格式化数据变化：', value)
}

const getRawData = () => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        code: 200,
        data: [
          { id: 1, name: '原始数据1', status: 'active' },
          { id: 2, name: '原始数据2', status: 'inactive' },
          { id: 3, name: '原始数据3', status: 'active' }
        ]
      })
    }, 800)
  })
}

const formatData = (list) => {
  return list.map(item => ({
    label: `${item.name} (${item.status})`,
    value: item.id
  }))
}

// 自定义字段名
const customValue = ref('')
const handleCustomChange = (value) => {
  console.log('自定义字段变化：', value)
}

const customDataList = ref([
  { name: '用户1', id: 'user1' },
  { name: '用户2', id: 'user2' },
  { name: '用户3', id: 'user3' }
])

// 多选模式（数组存储）
const multipleArrayValue = ref([])
const handleMultipleArrayChange = (value) => {
  console.log('多选数组变化：', value)
}

// 多选模式（字符串存储）
const multipleStringValue = ref('')
const handleMultipleStringChange = (value) => {
  console.log('多选字符串变化：', value)
}

const multipleOptions = ref([
  { label: '选项A', value: 'option_a' },
  { label: '选项B', value: 'option_b' },
  { label: '选项C', value: 'option_c' },
  { label: '选项D', value: 'option_d' },
  { label: '选项E', value: 'option_e' }
])
</script>

<style scoped>
h2 {
  margin-bottom: 20px;
  color: #333;
}

.demo-section {
  margin-bottom: 30px;
  padding: 20px;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
}

.demo-section h3 {
  margin-bottom: 15px;
  color: #666;
  font-size: 16px;
}

.result {
  margin-top: 10px;
  padding: 8px;
  background-color: #f5f7fa;
  border-radius: 4px;
  color: #606266;
  font-size: 14px;
}
</style> 