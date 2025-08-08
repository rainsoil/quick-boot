<template>
  <div class="demo-container">
    <h3>测试 c7-select 重复值问题</h3>
    
    <div class="test-section">
      <h4>1. 使用 el-select（正常显示重复值）</h4>
      <el-select v-model="elSelectValue" placeholder="请选择">
        <el-option
          v-for="item in duplicateDataList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <p>选中值: {{ elSelectValue }}</p>
    </div>

    <div class="test-section">
      <h4>2. 使用 c7-select（测试重复值显示）</h4>
      <c7-select
        v-model="c7SelectValue"
        :data-list="duplicateDataList"
        placeholder="请选择"
      />
      <p>选中值: {{ c7SelectValue }}</p>
    </div>

    <div class="test-section">
      <h4>3. 使用 c7-json-form 中的 c7-select</h4>
      <c7-json-form
        v-model="formData"
        :columns="formColumns"
      />
      <p>表单数据: {{ formData }}</p>
    </div>

    <div class="test-section">
      <h4>4. 数据列表（包含重复值）</h4>
      <pre>{{ JSON.stringify(duplicateDataList, null, 2) }}</pre>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { c7Select, c7JsonForm } from 'c7-plus'

// 包含重复值的数据列表
const duplicateDataList = ref([
  { label: '选项1', value: 'option1' },
  { label: '选项2', value: 'option2' },
  { label: '选项3', value: 'option1' }, // 重复的 value
  { label: '选项4', value: 'option2' }, // 重复的 value
  { label: '选项5', value: 'option3' },
  { label: '选项6', value: 'option1' }, // 重复的 value
])

// 测试值
const elSelectValue = ref('')
const c7SelectValue = ref('')
const formData = ref({})

// 表单配置
const formColumns = ref([
  {
    label: '选择测试',
    prop: 'selectTest',
    type: 'select',
    dataList: duplicateDataList.value,
    span: 12
  }
])
</script>

<style scoped>
.demo-container {
  padding: 20px;
}

.test-section {
  margin-bottom: 30px;
  padding: 20px;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
}

.test-section h4 {
  margin-top: 0;
  color: #409eff;
}

pre {
  background: #f5f7fa;
  padding: 10px;
  border-radius: 4px;
  overflow-x: auto;
}
</style> 