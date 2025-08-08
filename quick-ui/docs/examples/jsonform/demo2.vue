<template>
  <div class="json-form-demo">
    <h3>不同类型表单字段示例</h3>

    <el-card class="form-container">
      <el-form ref="form" :model="formData">
        <el-row>
          <c7-json-form
            :columns="columns"
            v-model="formData"
            @update:modelValue="handleFormChange"
          />
          
          <el-form-item>
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-card>

    <el-card class="result-container" style="margin-top: 20px;">
      <h4>表单数据预览</h4>
      <pre>{{ JSON.stringify(formData, null, 2) }}</pre>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { c7JsonForm } from "c7-plus"

// 表单数据模型
const formData = ref({
  name: '',
  age: null,
  email: '',
  phone: '',
  birthday: '',
  workDate: '',
  department: '',
  position: '',
  skills: [],
  avatar: '',
  description: ''
})

// 表单列配置
const columns = [
  {
    label: '姓名',
    prop: 'name',
    type: 'input',
    span: 12,
    required: true,
    rules: [
      { required: true, message: '请输入姓名', trigger: 'blur' },
      { min: 2, max: 10, message: '姓名长度在2到10个字符', trigger: 'blur' }
    ],
    placeholder: '请输入姓名'
  },
  {
    label: '年龄',
    prop: 'age',
    type: 'input-number',
    span: 12,
    min: 18,
    max: 65,
    precision: 0,
    rules: [
      { required: true, message: '请输入年龄', trigger: 'blur' }
    ],
    placeholder: '请输入年龄'
  },
  {
    label: '邮箱',
    prop: 'email',
    type: 'input',
    span: 12,
    rules: [
      { required: true, message: '请输入邮箱', trigger: 'blur' },
      { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
    ],
    placeholder: '请输入邮箱'
  },
  {
    label: '手机号',
    prop: 'phone',
    type: 'input',
    span: 12,
    rules: [
      { required: true, message: '请输入手机号', trigger: 'blur' },
      { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
    ],
    placeholder: '请输入手机号'
  },
  {
    label: '生日',
    prop: 'birthday',
    type: 'date',
    span: 12,
    rules: [
      { required: true, message: '请选择生日', trigger: 'change' }
    ],
    placeholder: '请选择生日',
    format: 'YYYY-MM-DD',
    valueFormat: 'YYYY-MM-DD'
  },
  {
    label: '入职日期',
    prop: 'workDate',
    type: 'daterange',
    span: 12,
    rules: [
      { required: true, message: '请选择入职日期范围', trigger: 'change' }
    ],
    placeholder: '请选择入职日期范围',
    startPlaceholder: '开始日期',
    endPlaceholder: '结束日期'
  },
  {
    label: '部门',
    prop: 'department',
    type: 'select',
    span: 12,
    rules: [
      { required: true, message: '请选择部门', trigger: 'change' }
    ],
    placeholder: '请选择部门',
    dataList: [
      { label: '技术部', value: 'tech' },
      { label: '产品部', value: 'product' },
      { label: '设计部', value: 'design' },
      { label: '运营部', value: 'operation' },
      { label: '市场部', value: 'marketing' }
    ]
  },
  {
    label: '职位',
    prop: 'position',
    type: 'cascader',
    span: 12,
    rules: [
      { required: true, message: '请选择职位', trigger: 'change' }
    ],
    placeholder: '请选择职位',
    dataList: [
      {
        label: '技术部',
        value: 'tech',
        children: [
          { label: '前端工程师', value: 'frontend' },
          { label: '后端工程师', value: 'backend' },
          { label: '全栈工程师', value: 'fullstack' }
        ]
      },
      {
        label: '产品部',
        value: 'product',
        children: [
          { label: '产品经理', value: 'pm' },
          { label: '产品助理', value: 'pa' }
        ]
      }
    ]
  },
  {
    label: '技能',
    prop: 'skills',
    type: 'checkbox',
    span: 24,
    rules: [
      { required: true, message: '请选择技能', trigger: 'change' }
    ],
    placeholder: '请选择技能',
    dataList: [
      { label: 'JavaScript', value: 'js' },
      { label: 'TypeScript', value: 'ts' },
      { label: 'Vue.js', value: 'vue' },
      { label: 'React', value: 'react' },
      { label: 'Node.js', value: 'node' },
      { label: 'Python', value: 'python' },
      { label: 'Java', value: 'java' },
      { label: 'Go', value: 'go' }
    ]
  },
  {
    label: '头像',
    prop: 'avatar',
    type: 'upload',
    span: 24,
    uploadUrl: 'https://api.example.com/upload',
    limit: 1,
    fileType: 'jpg,png,gif',
    fileSize: 5,
    placeholder: '请上传头像'
  },
  {
    label: '个人描述',
    prop: 'description',
    type: 'input',
    span: 24,
    rules: [
      { max: 200, message: '描述不能超过200个字符', trigger: 'blur' }
    ],
    placeholder: '请输入个人描述'
  }
]

// 表单变化事件处理
const handleFormChange = (data: Record<string, any>) => {
  console.log('表单数据变化:', data)
}

// 表单提交
const submitForm = () => {
  console.log('提交表单数据:', formData.value)
  alert('表单提交成功！')
}

// 表单重置
const resetForm = () => {
  formData.value = {
    name: '',
    age: null,
    email: '',
    phone: '',
    birthday: '',
    workDate: '',
    department: '',
    position: '',
    skills: [],
    avatar: '',
    description: ''
  }
}
</script>

<style scoped>
.json-form-demo {
  padding: 20px;
}

.form-container {
  padding: 20px;
}

.result-container {
  font-family: monospace;
  background-color: #f9f9f9;
  padding: 15px;
  border-radius: 4px;
}

pre {
  margin: 0;
  overflow-x: auto;
}
</style> 