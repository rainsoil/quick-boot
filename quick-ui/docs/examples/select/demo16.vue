<template>
  <div class="demo-container">
    <h3>🔍 c7-select 处理 toRef 包装数据问题诊断</h3>
    
    <div class="test-section">
      <h4>📊 测试数据对比</h4>
      <div class="data-display">
        <h5>1. 普通数组数据</h5>
        <pre>{{ JSON.stringify(normalData, null, 2) }}</pre>
        
        <h5>2. toRef 包装的数据</h5>
        <pre>{{ JSON.stringify(toRefData, null, 2) }}</pre>
        
        <h5>3. ref 包装的数据</h5>
        <pre>{{ JSON.stringify(refData, null, 2) }}</pre>
      </div>
    </div>

    <div class="test-section">
      <h4>✅ el-select 测试（基准对比）</h4>
      <el-select v-model="elSelectValue" placeholder="请选择" style="width: 300px">
        <el-option
          v-for="item in normalData"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <p><strong>选中值：</strong>{{ elSelectValue }}</p>
    </div>

    <div class="test-section">
      <h4>🔧 c7-select 测试（普通数组）</h4>
      <c7-select
        v-model="c7SelectValue1"
        :data-list="normalData"
        placeholder="请选择（普通数组）"
        style="width: 300px"
      />
      <p><strong>选中值：</strong>{{ c7SelectValue1 }}</p>
    </div>

    <div class="test-section">
      <h4>🔧 c7-select 测试（toRef 包装）</h4>
      <c7-select
        v-model="c7SelectValue2"
        :data-list="toRefData"
        placeholder="请选择（toRef 包装）"
        style="width: 300px"
      />
      <p><strong>选中值：</strong>{{ c7SelectValue2 }}</p>
    </div>

    <div class="test-section">
      <h4>🔧 c7-select 测试（ref 包装）</h4>
      <c7-select
        v-model="c7SelectValue3"
        :data-list="refData"
        placeholder="请选择（ref 包装）"
        style="width: 300px"
      />
      <p><strong>选中值：</strong>{{ c7SelectValue3 }}</p>
    </div>

    <div class="test-section">
      <h4>🎯 c7-json-form 中的 c7-select 测试</h4>
      <c7-json-form
        v-model="formData"
        :columns="formColumns"
      />
      <p><strong>表单数据：</strong>{{ formData }}</p>
    </div>

    <div class="test-section">
      <h4>🔍 问题分析</h4>
      <div class="analysis">
        <h5>问题根源：</h5>
        <ol>
          <li><strong>响应式对象处理：</strong>toRef 包装的数据是响应式对象，不是普通数组</li>
          <li><strong>useFetchOptions hook：</strong>直接使用 dataList 参数，没有解包响应式对象</li>
          <li><strong>数据格式不匹配：</strong>组件期望的是数组，但收到的是响应式对象</li>
          <li><strong>渲染失败：</strong>v-for 无法正确遍历响应式对象</li>
        </ol>
        
        <h5>解决方案：</h5>
        <ol>
          <li><strong>响应式解包：</strong>在 useFetchOptions 中正确处理响应式数据</li>
          <li><strong>数据验证：</strong>确保传递给组件的是数组格式</li>
          <li><strong>兼容性处理：</strong>支持多种数据格式（普通数组、ref、toRef）</li>
          <li><strong>错误处理：</strong>添加数据格式验证和错误提示</li>
        </ol>
      </div>
    </div>

    <div class="test-section">
      <h4>🛠️ 修复后的测试</h4>
      <div class="fix-test">
        <h5>修复方案：响应式数据解包</h5>
        <p>在 useFetchOptions 中添加响应式数据解包逻辑：</p>
        <pre>
// 修复前
options.value = dataList

// 修复后
options.value = unref(dataList) || []
        </pre>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, toRef } from 'vue'
import { c7Select, c7JsonForm } from 'c7-plus'

// 普通数组数据
const normalData = [
  { label: '选项1', value: 'option1' },
  { label: '选项2', value: 'option2' },
  { label: '选项3', value: 'option3' },
]

// toRef 包装的数据
const sourceData = ref([
  { label: '选项A', value: 'optionA' },
  { label: '选项B', value: 'optionB' },
  { label: '选项C', value: 'optionC' },
])
const toRefData = toRef(sourceData, 'value')

// ref 包装的数据
const refData = ref([
  { label: '选项X', value: 'optionX' },
  { label: '选项Y', value: 'optionY' },
  { label: '选项Z', value: 'optionZ' },
])

// 测试值
const elSelectValue = ref('')
const c7SelectValue1 = ref('')
const c7SelectValue2 = ref('')
const c7SelectValue3 = ref('')
const formData = ref({})

// 表单配置
const formColumns = ref([
  {
    label: '选择测试（普通数组）',
    prop: 'selectTest1',
    type: 'select',
    dataList: normalData,
    span: 12
  },
  {
    label: '选择测试（toRef 包装）',
    prop: 'selectTest2',
    type: 'select',
    dataList: toRefData,
    span: 12
  }
])
</script>

<style scoped>
.demo-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.test-section {
  margin-bottom: 30px;
  padding: 20px;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  background: #fff;
}

.test-section h4 {
  margin-top: 0;
  color: #409eff;
  border-bottom: 2px solid #409eff;
  padding-bottom: 8px;
}

.data-display {
  background: #f8f9fa;
  padding: 15px;
  border-radius: 6px;
}

.data-display h5 {
  color: #495057;
  margin-top: 15px;
  margin-bottom: 8px;
}

pre {
  background: #2d3748;
  color: #e2e8f0;
  padding: 15px;
  border-radius: 6px;
  overflow-x: auto;
  font-size: 12px;
  margin: 8px 0;
}

.analysis {
  background: #fff3cd;
  border: 1px solid #ffeaa7;
  border-radius: 6px;
  padding: 15px;
}

.analysis h5 {
  color: #856404;
  margin-top: 0;
}

.analysis ol {
  margin: 10px 0;
  padding-left: 20px;
}

.analysis li {
  margin-bottom: 8px;
  line-height: 1.5;
}

.fix-test h5 {
  color: #28a745;
  margin-top: 20px;
  margin-bottom: 10px;
}

.fix-test pre {
  background: #f8f9fa;
  color: #495057;
  border: 1px solid #dee2e6;
}
</style> 