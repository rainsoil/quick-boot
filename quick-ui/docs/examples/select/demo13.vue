<template>
  <div>
    <h2>实际应用场景示例</h2>
    
    <div class="demo-section">
      <h3>用户选择器</h3>
      <c7-select 
        v-model="selectedUser" 
        :fetchData="getUsers"
        placeholder="请选择用户"
        style="width: 300px;"
        @change="handleUserChange"
      />
      <div class="result">选中的用户：{{ selectedUser }}</div>
    </div>

    <div class="demo-section">
      <h3>部门选择器（分组）</h3>
      <c7-select 
        v-model="selectedDepartment" 
        :dataList="departmentData"
        :group="true"
        placeholder="请选择部门"
        style="width: 300px;"
        @change="handleDepartmentChange"
      />
      <div class="result">选中的部门：{{ selectedDepartment }}</div>
    </div>

    <div class="demo-section">
      <h3>技能标签选择器（多选）</h3>
      <c7-select 
        v-model="selectedSkills" 
        :dataList="skillsData"
        :multiple="true"
        :separator="true"
        placeholder="请选择技能标签"
        style="width: 300px;"
        @change="handleSkillsChange"
      />
      <div class="result">选中的技能：{{ selectedSkills }}</div>
    </div>

    <div class="demo-section">
      <h3>城市搜索器（远程搜索）</h3>
      <c7-select 
        v-model="selectedCity" 
        :fetchData="searchCities"
        :remote="true"
        placeholder="请输入城市名称搜索"
        style="width: 300px;"
        @change="handleCityChange"
      />
      <div class="result">选中的城市：{{ selectedCity }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { c7Select } from 'c7-plus'

// 用户选择器
const selectedUser = ref('')
const handleUserChange = (value) => {
  console.log('用户选择变化：', value)
}

const getUsers = () => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        code: 200,
        data: [
          { label: '张三', value: 'user1' },
          { label: '李四', value: 'user2' },
          { label: '王五', value: 'user3' },
          { label: '赵六', value: 'user4' }
        ]
      })
    }, 800)
  })
}

// 部门选择器
const selectedDepartment = ref('')
const handleDepartmentChange = (value) => {
  console.log('部门选择变化：', value)
}

const departmentData = ref([
  {
    label: '技术部',
    options: [
      { label: '前端组', value: 'frontend' },
      { label: '后端组', value: 'backend' },
      { label: '测试组', value: 'qa' }
    ]
  },
  {
    label: '产品部',
    options: [
      { label: '产品经理', value: 'pm' },
      { label: '产品助理', value: 'pa' }
    ]
  }
])

// 技能标签选择器
const selectedSkills = ref('')
const handleSkillsChange = (value) => {
  console.log('技能选择变化：', value)
}

const skillsData = ref([
  { label: 'JavaScript', value: 'js' },
  { label: 'TypeScript', value: 'ts' },
  { label: 'Vue.js', value: 'vue' },
  { label: 'React', value: 'react' },
  { label: 'Node.js', value: 'node' }
])

// 城市搜索器
const selectedCity = ref('')
const handleCityChange = (value) => {
  console.log('城市选择变化：', value)
}

const searchCities = (params) => {
  const query = params.query || ''
  return new Promise((resolve) => {
    setTimeout(() => {
      const allCities = [
        { label: '北京', value: 'beijing' },
        { label: '上海', value: 'shanghai' },
        { label: '广州', value: 'guangzhou' },
        { label: '深圳', value: 'shenzhen' },
        { label: '杭州', value: 'hangzhou' }
      ]
      
      const filteredCities = allCities.filter(city => 
        city.label.toLowerCase().includes(query.toLowerCase())
      )
      
      resolve({
        code: 200,
        data: filteredCities
      })
    }, 300)
  })
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
</style> 