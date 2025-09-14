<template>
  <div>
    <h2>复杂选择功能示例</h2>
    
    <div class="demo-section">
      <h3>分组选择</h3>
      <c7-select 
        v-model="groupValue" 
        :dataList="groupDataList"
        :group="true"
        placeholder="请选择（分组展示）"
        style="width: 300px;"
        @change="handleGroupChange"
      />
      <div class="result">选中的值：{{ groupValue }}</div>
    </div>

    <div class="demo-section">
      <h3>Tag模式（允许创建新选项）</h3>
      <c7-select 
        v-model="tagValue" 
        :dataList="tagDataList"
        :tag="true"
        placeholder="请选择或输入新选项"
        style="width: 300px;"
        @change="handleTagChange"
      />
      <div class="result">选中的值：{{ tagValue }}</div>
    </div>

    <div class="demo-section">
      <h3>禁用自动加载</h3>
      <el-button @click="loadData" :loading="loading">手动加载数据</el-button>
      <c7-select 
        v-model="manualValue" 
        :fetchData="getManualData"
        :autoLoad="false"
        placeholder="请先点击按钮加载数据"
        style="width: 300px; margin-top: 10px;"
        @change="handleManualChange"
      />
      <div class="result">选中的值：{{ manualValue }}</div>
    </div>

    <div class="demo-section">
      <h3>自定义标签模板</h3>
      <c7-select 
        v-model="customLabelValue" 
        :dataList="customLabelDataList"
        placeholder="请选择（自定义标签）"
        style="width: 300px;"
        @change="handleCustomLabelChange"
      >
        <template #label="{ label, value }">
          <div class="custom-label">
            <span class="label-text">{{ label }}</span>
            <span class="label-value">({{ value }})</span>
          </div>
        </template>
      </c7-select>
      <div class="result">选中的值：{{ customLabelValue }}</div>
    </div>

    <div class="demo-section">
      <h3>表单验证</h3>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item label="必选项" prop="required">
          <c7-select 
            v-model="form.required" 
            :dataList="formOptions"
            placeholder="请选择（必填）"
            style="width: 300px;"
            @change="handleFormChange"
          />
        </el-form-item>
        <el-form-item label="多选项" prop="multiple">
          <c7-select 
            v-model="form.multiple" 
            :dataList="formOptions"
            :multiple="true"
            placeholder="请选择多个选项（必填）"
            style="width: 300px;"
            @change="handleFormMultipleChange"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="demo-section">
      <h3>条件渲染</h3>
      <c7-select 
        v-model="conditionValue" 
        :dataList="conditionOptions"
        placeholder="请选择显示条件"
        style="width: 300px;"
        @change="handleConditionChange"
      />
      <div v-if="conditionValue === 'show'" class="conditional-content">
        <p>这是条件显示的内容</p>
        <c7-select 
          v-model="subValue" 
          :dataList="subOptions"
          placeholder="请选择子选项"
          style="width: 300px;"
          @change="handleSubChange"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
// 分组选择
const groupValue = ref('')
const handleGroupChange = (value) => {
  console.log('分组选择变化：', value)
}

const groupDataList = ref([
  {
    label: '水果',
    options: [
      { label: '苹果', value: 'apple' },
      { label: '香蕉', value: 'banana' },
      { label: '橙子', value: 'orange' }
    ]
  },
  {
    label: '蔬菜',
    options: [
      { label: '胡萝卜', value: 'carrot' },
      { label: '白菜', value: 'cabbage' },
      { label: '土豆', value: 'potato' }
    ]
  }
])

// Tag模式
const tagValue = ref('')
const handleTagChange = (value) => {
  console.log('Tag模式变化：', value)
}

const tagDataList = ref([
  { label: '预设选项1', value: 'preset1' },
  { label: '预设选项2', value: 'preset2' },
  { label: '预设选项3', value: 'preset3' }
])

// 手动加载数据
const manualValue = ref('')
const loading = ref(false)
const handleManualChange = (value) => {
  console.log('手动加载变化：', value)
}

const getManualData = () => {
  return new Promise((resolve) => {
    loading.value = true
    setTimeout(() => {
      resolve({
        code: 200,
        data: [
          { label: '手动加载选项1', value: 'manual1' },
          { label: '手动加载选项2', value: 'manual2' },
          { label: '手动加载选项3', value: 'manual3' }
        ]
      })
      loading.value = false
    }, 1500)
  })
}

const loadData = () => {
  // 触发数据加载
  getManualData()
}

// 自定义标签模板
const customLabelValue = ref('')
const handleCustomLabelChange = (value) => {
  console.log('自定义标签变化：', value)
}

const customLabelDataList = ref([
  { label: '选项A', value: 'option_a' },
  { label: '选项B', value: 'option_b' },
  { label: '选项C', value: 'option_c' }
])

// 表单验证
const formRef = ref(null)
const form = ref({
  required: '',
  multiple: ''
})

const formOptions = ref([
  { label: '选项1', value: 'option1' },
  { label: '选项2', value: 'option2' },
  { label: '选项3', value: 'option3' },
  { label: '选项4', value: 'option4' }
])

const rules = {
  required: [
    { required: true, message: '请选择必选项', trigger: 'change' }
  ],
  multiple: [
    { required: true, message: '请选择多选项', trigger: 'change' }
  ]
}

const handleFormChange = (value) => {
  console.log('表单选择变化：', value)
}

const handleFormMultipleChange = (value) => {
  console.log('表单多选变化：', value)
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
const conditionValue = ref('')
const handleConditionChange = (value) => {
  console.log('条件变化：', value)
}

const conditionOptions = ref([
  { label: '隐藏', value: 'hide' },
  { label: '显示', value: 'show' }
])

const subValue = ref('')
const handleSubChange = (value) => {
  console.log('子选项变化：', value)
}

const subOptions = ref([
  { label: '子选项1', value: 'sub1' },
  { label: '子选项2', value: 'sub2' }
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

.conditional-content {
  margin-top: 10px;
  padding: 10px;
  background-color: #e1f3d8;
  border-radius: 4px;
}

.custom-label {
  display: flex;
  align-items: center;
  gap: 5px;
}

.label-text {
  font-weight: bold;
}

.label-value {
  color: #909399;
  font-size: 12px;
}

.el-button {
  margin-right: 10px;
  margin-bottom: 10px;
}
</style> 