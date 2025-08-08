<template>
  <div>
    <h2>基础单选功能示例</h2>
    
    <div class="demo-section">
      <h3>基础单选</h3>
      <c7-radio :data-list="basicOptions" v-model="basicValue" @change="handleBasicChange" />
      <div class="result">选中的值：{{ basicValue }}</div>
    </div>

    <div class="demo-section">
      <h3>数字类型单选</h3>
      <c7-radio :data-list="numberOptions" v-model="numberValue" @change="handleNumberChange" />
      <div class="result">选中的值：{{ numberValue }}</div>
    </div>

    <div class="demo-section">
      <h3>默认选中</h3>
      <c7-radio :data-list="defaultOptions" v-model="defaultValue" @change="handleDefaultChange" />
      <div class="result">选中的值：{{ defaultValue }}</div>
    </div>

    <div class="demo-section">
      <h3>动态选项</h3>
      <el-button @click="addOption">添加选项</el-button>
      <el-button @click="removeOption">删除选项</el-button>
      <c7-radio :data-list="dynamicOptions" v-model="dynamicValue" @change="handleDynamicChange" />
      <div class="result">选中的值：{{ dynamicValue }}</div>
    </div>

    <div class="demo-section">
      <h3>禁用状态</h3>
      <c7-radio :data-list="disabledOptions" v-model="disabledValue" @change="handleDisabledChange" />
      <div class="result">选中的值：{{ disabledValue }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { c7Radio } from 'c7-plus'

// 基础选项
const basicOptions = ref([
  { label: '选项1', value: 'option1' },
  { label: '选项2', value: 'option2' },
  { label: '选项3', value: 'option3' }
])

const basicValue = ref('')
const handleBasicChange = (value) => {
  console.log('基础单选变化：', value)
}

// 数字类型选项
const numberOptions = ref([
  { label: '数字1', value: 1 },
  { label: '数字2', value: 2 },
  { label: '数字3', value: 3 }
])

const numberValue = ref(1)
const handleNumberChange = (value) => {
  console.log('数字单选变化：', value)
}

// 默认选中选项
const defaultOptions = ref([
  { label: '默认选项1', value: 'default1' },
  { label: '默认选项2', value: 'default2' },
  { label: '默认选项3', value: 'default3' }
])

const defaultValue = ref('default2')
const handleDefaultChange = (value) => {
  console.log('默认选中变化：', value)
}

// 动态选项
const dynamicOptions = ref([
  { label: '动态选项1', value: 'dynamic1' },
  { label: '动态选项2', value: 'dynamic2' }
])

const dynamicValue = ref('')
const handleDynamicChange = (value) => {
  console.log('动态选项变化：', value)
}

const addOption = () => {
  const newIndex = dynamicOptions.value.length + 1
  dynamicOptions.value.push({
    label: `动态选项${newIndex}`,
    value: `dynamic${newIndex}`
  })
}

const removeOption = () => {
  if (dynamicOptions.value.length > 1) {
    dynamicOptions.value.pop()
  }
}

// 禁用状态选项
const disabledOptions = ref([
  { label: '可用选项', value: 'enabled' },
  { label: '禁用选项', value: 'disabled' }
])

const disabledValue = ref('enabled')
const handleDisabledChange = (value) => {
  console.log('禁用状态变化：', value)
}
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

.el-button {
  margin-right: 10px;
  margin-bottom: 10px;
}
</style> 