<template>
  <div class="json-form-demo">
    <h3>基础表单示例</h3>

    <!-- 表单容器 -->
    <el-card class="form-container">
      <el-form ref="form" :model="formData">
        <!-- 表单组件 -->
        <c7-json-form
            :columns="columns"
            v-model="formData"
            @form-change="handleFormChange"
        >

          <template #slotTest>
            <el-input v-model="formData.slot" placeholder="请输入自定义插槽内容"/>
          </template>
        </c7-json-form>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>


      </el-form>

    </el-card>

    <!-- 数据展示区域 -->
    <el-card class="result-container" style="margin-top: 20px;">
      <h4>表单数据预览</h4>
      <pre>{{ JSON.stringify(formData, null, 2) }}</pre>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from 'vue'
import {c7JsonForm} from "c7-plus";


// 表单数据模型
const formData = ref({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  age: '',
  birthdate: '',
  gender: '',
  country: '',
  address: ''
})

// 表单列配置
const columns = [
  {
    label: '用户名',
    prop: 'username',
    type: 'input',
    span: 12,
    required: true,
    rules: [
      {required: true, message: '用户名必填', trigger: 'blur'},
      {min: 3, max: 15, message: '长度在3到15个字符', trigger: 'blur'}
    ],
    placeholder: '请输入用户名',
    change: ({prop, value}) => {
      console.log(`字段 ${prop} 变更为:`, value)
    }
  },
  {
    label: '邮箱',
    prop: 'email',
    type: 'input',
    span: 12,
    rules: [
      {required: true, message: '请输入邮箱地址', trigger: 'blur'},
      {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
    ],
    placeholder: '请输入邮箱'
  },
  {
    label: '密码',
    prop: 'password',
    type: 'input',
    span: 12,
    required: true,
    rules: [
      {required: true, message: '密码必填', trigger: 'blur'},
      {min: 6, max: 20, message: '长度在6到20个字符', trigger: 'blur'}
    ],
    placeholder: '请输入密码'
  },
  {
    label: '确认密码',
    prop: 'confirmPassword',
    type: 'input',
    span: 12,
    required: true,
    rules: [
      {required: true, message: '请确认密码', trigger: 'blur'},
      {
        validator: (rule, value, callback) => {
          if (value !== formData.value.password) {
            callback(new Error('两次输入密码不一致'))
          } else {
            callback()
          }
        },
        trigger: 'blur'
      }
    ],
    placeholder: '请再次输入密码'
  },
  {
    label: '年龄',
    prop: 'age',
    type: 'input-number',
    min: 0,
    max: 150,
    precision: 0,
    span: 12,
    rules: [
      {
        pattern: /^\d+$/,
        message: '请输入数字',
        trigger: 'blur'
      },
      {
        validator: (rule, value, callback) => {
          const age = Number(value)
          if (age < 0 || age > 150) {
            callback(new Error('年龄范围应在0-150之间'))
          } else {
            callback()
          }
        },
        trigger: 'blur'
      }
    ],
    placeholder: '请输入年龄'
  },
  {
    label: '出生日期',
    prop: 'birthdate',
    type: 'date',
    span: 12,
    rules: [
      {required: true, message: '请选择出生日期', trigger: 'change'}
    ],
    placeholder: '请选择出生日期'
  },
  {
    label: '性别',
    prop: 'gender',
    type: 'radio',
    span: 12,
    rules: [
      {required: true, message: '请选择性别', trigger: 'change'}
    ],
    placeholder: '请选择性别',
    dataList: [
      {label: '男', value: 'male'},
      {label: '女', value: 'female'},
      {label: '其他', value: 'other'}
    ]
  },
  {
    label: '出生地',
    prop: 'birthplace',
    type: 'select',
    span: 12,
    rules: [
      {required: true, message: '请选择出生地', trigger: 'change'}
    ],
    placeholder: '请选择出生地',
    dataList: [
      {label: '北京', value: 'beijing'},
      {label: '天津', value: 'tianjin'},
      {label: '河北', value: 'hebei'}
    ]
  },
  {
    label: '国家',
    prop: 'country',
    type: 'cascader',
    resultType: 3,
    span: 12,
    rules: [
      {required: true, message: '请选择国家', trigger: 'change'}
    ],
    placeholder: '请选择国家',
    dataList: [
      {
        label: '中国', value: 'china', children: [
          {value: "3", label: 'Beijing'},
          {value: "4", label: 'Shanghai'},
          {value: "5", label: 'Hangzhou'},
        ],
      },
      {label: '美国', value: 'usa'},
      {label: '日本', value: 'japan'}
    ]
  },
  {
    label: '地址',
    prop: 'address',
    type: 'input',
    span: 24,
    rules: [
      {max: 100, message: '地址长度不超过100个字符', trigger: 'blur'}
    ],
    placeholder: '请输入详细地址'
  },
  {
    label: '头像',
    prop: 'icon',
    type: 'upload',
    uploadUrl: 'http://badu.com',
    span: 24,

    placeholder: '请上传头像'
  },
  {
    label: '爱去的国家',
    prop: 'love',
    type: 'checkbox',
    span: 24,
    dataList: [
      {
        label: '中国', value: 'china',
      },
      {label: '美国', value: 'usa'},
      {label: '日本', value: 'japan'}
    ],
    placeholder: '请选择爱去的国家'
  },
  {
    label: '卡槽',
    prop: 'slot',
    type: 'slot',
    span: 24,
    slotName: 'slotTest'
  }
]

// 表单变化事件处理
const handleFormChange = (data: Record<string, any>) => {
  console.log('表单数据变化:', data)
}

// 表单提交
const submitForm = () => {
  console.log('提交表单数据:', formData.value)
  // 这里可以添加实际的提交逻辑
}

// 表单重置
const resetForm = () => {
  formData.value = {
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    age: '',
    birthdate: '',
    gender: '',
    country: '',
    address: ''
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
