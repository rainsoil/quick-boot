<template>
  <div>
    <h2>高级单选功能示例</h2>
    
    <div class="demo-section">
      <h3>表单验证</h3>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item label="性别" prop="gender">
          <c7-radio :data-list="genderOptions" v-model="form.gender" @change="handleGenderChange" />
        </el-form-item>
        <el-form-item label="学历" prop="education">
          <c7-radio :data-list="educationOptions" v-model="form.education" @change="handleEducationChange" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="demo-section">
      <h3>条件渲染</h3>
      <c7-radio :data-list="conditionOptions" v-model="conditionValue" @change="handleConditionChange" />
      <div v-if="conditionValue === 'show'" class="conditional-content">
        <p>这是条件显示的内容</p>
        <c7-radio :data-list="subOptions" v-model="subValue" @change="handleSubChange" />
      </div>
    </div>

    <div class="demo-section">
      <h3>异步数据加载</h3>
      <el-button @click="loadAsyncData" :loading="loading">加载数据</el-button>
      <c7-radio :data-list="asyncOptions" v-model="asyncValue" @change="handleAsyncChange" />
      <div class="result">选中的值：{{ asyncValue }}</div>
    </div>

    <div class="demo-section">
      <h3>数据过滤</h3>
      <el-input v-model="filterText" placeholder="输入过滤文本" style="width: 200px; margin-bottom: 10px;" />
      <c7-radio :data-list="filteredOptions" v-model="filterValue" @change="handleFilterChange" />
      <div class="result">选中的值：{{ filterValue }}</div>
    </div>

    <div class="demo-section">
      <h3>自定义样式</h3>
      <div class="custom-radio-container">
        <c7-radio :data-list="customOptions" v-model="customValue" @change="handleCustomChange" />
      </div>
      <div class="result">选中的值：{{ customValue }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
// 表单验证
const formRef = ref(null)
const form = ref({
  gender: '',
  education: ''
})

const genderOptions = ref([
  { label: '男', value: 'male' },
  { label: '女', value: 'female' }
])

const educationOptions = ref([
  { label: '高中', value: 'high_school' },
  { label: '大专', value: 'college' },
  { label: '本科', value: 'bachelor' },
  { label: '研究生', value: 'master' }
])

const rules = {
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  education: [
    { required: true, message: '请选择学历', trigger: 'change' }
  ]
}

const handleGenderChange = (value) => {
  console.log('性别变化：', value)
}

const handleEducationChange = (value) => {
  console.log('学历变化：', value)
}

const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      alert('表单验证通过！')
      console.log('表单数据：', form.value)
    } else {
      alert('表单验证失败！')
    }
  })
}

const resetForm = () => {
  formRef.value.resetFields()
}

// 条件渲染
const conditionOptions = ref([
  { label: '隐藏', value: 'hide' },
  { label: '显示', value: 'show' }
])

const conditionValue = ref('hide')
const handleConditionChange = (value) => {
  console.log('条件变化：', value)
}

const subOptions = ref([
  { label: '子选项1', value: 'sub1' },
  { label: '子选项2', value: 'sub2' }
])

const subValue = ref('')
const handleSubChange = (value) => {
  console.log('子选项变化：', value)
}

// 异步数据加载
const loading = ref(false)
const asyncOptions = ref([])
const asyncValue = ref('')

const loadAsyncData = () => {
  loading.value = true
  // 模拟异步请求
  setTimeout(() => {
    asyncOptions.value = [
      { label: '异步选项1', value: 'async1' },
      { label: '异步选项2', value: 'async2' },
      { label: '异步选项3', value: 'async3' }
    ]
    loading.value = false
  }, 1000)
}

const handleAsyncChange = (value) => {
  console.log('异步选项变化：', value)
}

// 数据过滤
const filterText = ref('')
const allOptions = ref([
  { label: '苹果', value: 'apple' },
  { label: '香蕉', value: 'banana' },
  { label: '橙子', value: 'orange' },
  { label: '葡萄', value: 'grape' },
  { label: '草莓', value: 'strawberry' }
])

const filteredOptions = computed(() => {
  if (!filterText.value) {
    return allOptions.value
  }
  return allOptions.value.filter(option => 
    option.label.toLowerCase().includes(filterText.value.toLowerCase())
  )
})

const filterValue = ref('')
const handleFilterChange = (value) => {
  console.log('过滤选项变化：', value)
}

// 自定义样式
const customOptions = ref([
  { label: '自定义选项1', value: 'custom1' },
  { label: '自定义选项2', value: 'custom2' },
  { label: '自定义选项3', value: 'custom3' }
])

const customValue = ref('')
const handleCustomChange = (value) => {
  console.log('自定义选项变化：', value)
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

.conditional-content {
  margin-top: 10px;
  padding: 10px;
  background-color: #e1f3d8;
  border-radius: 4px;
}

.custom-radio-container {
  padding: 15px;
  background-color: #f0f9ff;
  border-radius: 4px;
}

.el-button {
  margin-right: 10px;
  margin-bottom: 10px;
}
</style> 