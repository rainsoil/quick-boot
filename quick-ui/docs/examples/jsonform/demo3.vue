<template>
  <div class="json-form-demo">
    <h3>表单验证和布局示例</h3>

    <el-card class="form-container">
      <el-form ref="formRef" :model="formData" :rules="formRules">
        <el-row>
          <c7-json-form
            :columns="columns"
            v-model="formData"
            @update:modelValue="handleFormChange"
          />
          
          <el-form-item>
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
            <el-button @click="validateForm">验证</el-button>
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
  username: '',
  password: '',
  confirmPassword: '',
  email: '',
  phone: '',
  age: null,
  salary: null,
  startDate: '',
  endDate: '',
  department: '',
  role: '',
  permissions: [],
  status: '',
  description: ''
})

// 表单引用
const formRef = ref()

// 表单验证规则
const formRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在3到20个字符', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9_]+$/, message: '用户名只能包含字母、数字和下划线', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在6到20个字符', trigger: 'blur' },
    { pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/, message: '密码必须包含大小写字母和数字', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule: any, value: string, callback: any) => {
        if (value !== formData.value.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  age: [
    { required: true, message: '请输入年龄', trigger: 'blur' },
    { type: 'number', min: 18, max: 65, message: '年龄必须在18到65之间', trigger: 'blur' }
  ],
  salary: [
    { required: true, message: '请输入薪资', trigger: 'blur' },
    { type: 'number', min: 3000, message: '薪资不能低于3000', trigger: 'blur' }
  ],
  startDate: [
    { required: true, message: '请选择开始日期', trigger: 'change' }
  ],
  endDate: [
    { required: true, message: '请选择结束日期', trigger: 'change' },
    {
      validator: (rule: any, value: string, callback: any) => {
        if (value && formData.value.startDate && value <= formData.value.startDate) {
          callback(new Error('结束日期必须大于开始日期'))
        } else {
          callback()
        }
      },
      trigger: 'change'
    }
  ],
  department: [
    { required: true, message: '请选择部门', trigger: 'change' }
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ],
  permissions: [
    { required: true, message: '请选择权限', trigger: 'change' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
}

// 表单列配置
const columns = [
  {
    label: '用户名',
    prop: 'username',
    type: 'input',
    span: 12,
    required: true,
    rules: formRules.username,
    placeholder: '请输入用户名',
    change: ({ prop, value, formData }) => {
      console.log(`用户名变更为: ${value}`)
    }
  },
  {
    label: '密码',
    prop: 'password',
    type: 'input',
    span: 12,
    required: true,
    rules: formRules.password,
    placeholder: '请输入密码'
  },
  {
    label: '确认密码',
    prop: 'confirmPassword',
    type: 'input',
    span: 12,
    required: true,
    rules: formRules.confirmPassword,
    placeholder: '请再次输入密码'
  },
  {
    label: '邮箱',
    prop: 'email',
    type: 'input',
    span: 12,
    required: true,
    rules: formRules.email,
    placeholder: '请输入邮箱'
  },
  {
    label: '手机号',
    prop: 'phone',
    type: 'input',
    span: 12,
    required: true,
    rules: formRules.phone,
    placeholder: '请输入手机号'
  },
  {
    label: '年龄',
    prop: 'age',
    type: 'input-number',
    span: 12,
    min: 18,
    max: 65,
    precision: 0,
    required: true,
    rules: formRules.age,
    placeholder: '请输入年龄'
  },
  {
    label: '薪资',
    prop: 'salary',
    type: 'input-number',
    span: 12,
    min: 3000,
    max: 100000,
    precision: 2,
    required: true,
    rules: formRules.salary,
    placeholder: '请输入薪资'
  },
  {
    label: '开始日期',
    prop: 'startDate',
    type: 'date',
    span: 12,
    required: true,
    rules: formRules.startDate,
    placeholder: '请选择开始日期',
    format: 'YYYY-MM-DD',
    valueFormat: 'YYYY-MM-DD'
  },
  {
    label: '结束日期',
    prop: 'endDate',
    type: 'date',
    span: 12,
    required: true,
    rules: formRules.endDate,
    placeholder: '请选择结束日期',
    format: 'YYYY-MM-DD',
    valueFormat: 'YYYY-MM-DD'
  },
  {
    label: '部门',
    prop: 'department',
    type: 'select',
    span: 12,
    required: true,
    rules: formRules.department,
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
    label: '角色',
    prop: 'role',
    type: 'cascader',
    span: 12,
    required: true,
    rules: formRules.role,
    placeholder: '请选择角色',
    dataList: [
      {
        label: '管理员',
        value: 'admin',
        children: [
          { label: '超级管理员', value: 'super_admin' },
          { label: '普通管理员', value: 'normal_admin' }
        ]
      },
      {
        label: '用户',
        value: 'user',
        children: [
          { label: 'VIP用户', value: 'vip_user' },
          { label: '普通用户', value: 'normal_user' }
        ]
      }
    ]
  },
  {
    label: '权限',
    prop: 'permissions',
    type: 'checkbox',
    span: 24,
    required: true,
    rules: formRules.permissions,
    placeholder: '请选择权限',
    dataList: [
      { label: '用户管理', value: 'user_manage' },
      { label: '角色管理', value: 'role_manage' },
      { label: '权限管理', value: 'permission_manage' },
      { label: '系统设置', value: 'system_setting' },
      { label: '数据查看', value: 'data_view' },
      { label: '数据编辑', value: 'data_edit' }
    ]
  },
  {
    label: '状态',
    prop: 'status',
    type: 'radio',
    span: 24,
    required: true,
    rules: formRules.status,
    placeholder: '请选择状态',
    dataList: [
      { label: '启用', value: 'active' },
      { label: '禁用', value: 'inactive' },
      { label: '待审核', value: 'pending' }
    ]
  },
  {
    label: '描述',
    prop: 'description',
    type: 'input',
    span: 24,
    rules: [
      { max: 500, message: '描述不能超过500个字符', trigger: 'blur' }
    ],
    placeholder: '请输入描述信息'
  }
]

// 表单变化事件处理
const handleFormChange = (data: Record<string, any>) => {
  console.log('表单数据变化:', data)
}

// 表单提交
const submitForm = async () => {
  try {
    await formRef.value.validate()
    console.log('表单验证通过，提交数据:', formData.value)
    alert('表单提交成功！')
  } catch (error) {
    console.log('表单验证失败:', error)
    alert('表单验证失败，请检查输入！')
  }
}

// 表单验证
const validateForm = async () => {
  try {
    await formRef.value.validate()
    alert('表单验证通过！')
  } catch (error) {
    alert('表单验证失败，请检查输入！')
  }
}

// 表单重置
const resetForm = () => {
  formRef.value.resetFields()
  formData.value = {
    username: '',
    password: '',
    confirmPassword: '',
    email: '',
    phone: '',
    age: null,
    salary: null,
    startDate: '',
    endDate: '',
    department: '',
    role: '',
    permissions: [],
    status: '',
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