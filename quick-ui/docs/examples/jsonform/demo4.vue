<template>
  <div class="json-form-demo">
    <h3>自定义插槽示例</h3>

    <el-card class="form-container">
      <el-form ref="form" :model="formData">
        <el-row>
          <c7-json-form
            :columns="columns"
            v-model="formData"
            @update:modelValue="handleFormChange"
          >
            <!-- 自定义头像上传插槽 -->
            <template #slot_avatar="{ item, modelValue }">
              <div class="avatar-upload">
                <el-upload
                  class="avatar-uploader"
                  action="https://api.example.com/upload"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload"
                >
                  <img v-if="modelValue" :src="modelValue" class="avatar" />
                  <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                </el-upload>
                <div class="avatar-tip">点击上传头像</div>
              </div>
            </template>

          </c7-json-form>

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
import { Plus } from '@element-plus/icons-vue'

// 表单数据模型
const formData = ref({
  name: '',
  avatar: '',
  rating: 0,
  color: '#409EFF',
  status: false,
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
      { required: true, message: '请输入姓名', trigger: 'blur' }
    ],
    placeholder: '请输入姓名'
  },
  {
    label: '头像',
    prop: 'avatar',
    type: 'slot',
    span: 12,
    slotName: 'slot_avatar',
    placeholder: '请上传头像'
  },
  {
    label: '评分',
    prop: 'rating',
    type: 'slot',
    span: 12,
    slotName: 'slot_rating',
    placeholder: '请选择评分'
  },
  {
    label: '主题色',
    prop: 'color',
    type: 'slot',
    span: 12,
    slotName: 'slot_color',
    placeholder: '请选择主题色'
  },
  {
    label: '状态',
    prop: 'status',
    type: 'slot',
    span: 12,
    slotName: 'slot_switch',
    placeholder: '请选择状态'
  },
  {
    label: '描述',
    prop: 'description',
    type: 'slot',
    span: 24,
    slotName: 'slot_textarea',
    placeholder: '请输入描述'
  }
]

// 头像上传成功
const handleAvatarSuccess = (response: any, file: any) => {
  formData.value.avatar = URL.createObjectURL(file.raw)
}

// 头像上传前验证
const beforeAvatarUpload = (file: any) => {
  const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJPG) {
    alert('上传头像图片只能是 JPG/PNG 格式!')
  }
  if (!isLt2M) {
    alert('上传头像图片大小不能超过 2MB!')
  }
  return isJPG && isLt2M
}

// 评分变化
const handleRatingChange = (value: number) => {
  console.log('评分变化:', value)
}

// 颜色变化
const handleColorChange = (value: string) => {
  console.log('颜色变化:', value)
}

// 开关变化
const handleSwitchChange = (value: boolean) => {
  console.log('开关变化:', value)
}

// 文本域变化
const handleTextareaChange = (value: string) => {
  console.log('文本域变化:', value)
}

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
    avatar: '',
    rating: 0,
    color: '#409EFF',
    status: false,
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

.avatar-upload {
  text-align: center;
}

.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 100px;
  height: 100px;
  margin: 0 auto;
}

.avatar-uploader:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}

.avatar {
  width: 100px;
  height: 100px;
  display: block;
}

.avatar-tip {
  font-size: 12px;
  color: #666;
  margin-top: 8px;
}
</style>
