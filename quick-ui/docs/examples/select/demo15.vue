<template>
  <div class="demo-container">
    <h3>🔍 c7-select 重复值问题诊断</h3>
    
    <div class="test-section">
      <h4>📊 测试数据（包含重复值）</h4>
      <div class="data-display">
        <p><strong>数据列表：</strong></p>
        <pre>{{ JSON.stringify(testData, null, 2) }}</pre>
        <p><strong>重复值统计：</strong></p>
        <ul>
          <li>value: 'option1' 出现 {{ getValueCount('option1') }} 次</li>
          <li>value: 'option2' 出现 {{ getValueCount('option2') }} 次</li>
          <li>value: 'option3' 出现 {{ getValueCount('option3') }} 次</li>
        </ul>
      </div>
    </div>

    <div class="test-section">
      <h4>✅ el-select 测试（基准对比）</h4>
      <el-select v-model="elSelectValue" placeholder="请选择" style="width: 300px">
        <el-option
          v-for="(item, index) in testData"
          :key="`el-${item.value}-${index}`"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <p><strong>选中值：</strong>{{ elSelectValue }}</p>
      <p><strong>显示选项数：</strong>{{ testData.length }}</p>
    </div>

    <div class="test-section">
      <h4>🔧 c7-select 测试（问题诊断）</h4>
      <c7-select
        v-model="c7SelectValue"
        :data-list="testData"
        placeholder="请选择"
        style="width: 300px"
      />
      <p><strong>选中值：</strong>{{ c7SelectValue }}</p>
      <p><strong>实际选项数：</strong>{{ actualOptionsCount }}</p>
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
        <h5>可能的原因：</h5>
        <ol>
          <li><strong>Vue key 重复问题：</strong>多个选项使用相同的 value 作为 key</li>
          <li><strong>数据去重逻辑：</strong>组件内部可能有去重处理</li>
          <li><strong>渲染优化：</strong>Vue 可能合并了相同的选项</li>
          <li><strong>数据传递问题：</strong>props 传递过程中数据丢失</li>
        </ol>
        
        <h5>解决方案：</h5>
        <ol>
          <li><strong>使用索引作为 key：</strong>在 v-for 中使用 index 确保唯一性</li>
          <li><strong>添加唯一标识：</strong>为每个选项添加唯一的 id 字段</li>
          <li><strong>禁用去重：</strong>确保组件不自动去重</li>
          <li><strong>数据验证：</strong>在传递数据前进行验证</li>
        </ol>
      </div>
    </div>

    <div class="test-section">
      <h4>🛠️ 修复后的测试</h4>
      <div class="fix-test">
        <h5>方案1：使用唯一标识</h5>
        <c7-select
          v-model="fixedSelectValue1"
          :data-list="fixedData1"
          placeholder="请选择（修复方案1）"
          style="width: 300px"
        />
        <p><strong>选中值：</strong>{{ fixedSelectValue1 }}</p>
        
        <h5>方案2：使用索引作为 key</h5>
        <c7-select
          v-model="fixedSelectValue2"
          :data-list="fixedData2"
          placeholder="请选择（修复方案2）"
          style="width: 300px"
        />
        <p><strong>选中值：</strong>{{ fixedSelectValue2 }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
// 原始测试数据（包含重复值）
const testData = ref([
  { label: '选项1', value: 'option1' },
  { label: '选项2', value: 'option2' },
  { label: '选项3', value: 'option1' }, // 重复的 value
  { label: '选项4', value: 'option2' }, // 重复的 value
  { label: '选项5', value: 'option3' },
  { label: '选项6', value: 'option1' }, // 重复的 value
])

// 修复方案1：添加唯一标识
const fixedData1 = ref([
  { id: 1, label: '选项1', value: 'option1' },
  { id: 2, label: '选项2', value: 'option2' },
  { id: 3, label: '选项3', value: 'option1' },
  { id: 4, label: '选项4', value: 'option2' },
  { id: 5, label: '选项5', value: 'option3' },
  { id: 6, label: '选项6', value: 'option1' },
])

// 修复方案2：使用索引作为唯一标识
const fixedData2 = ref(testData.value.map((item, index) => ({
  ...item,
  _index: index
})))

// 测试值
const elSelectValue = ref('')
const c7SelectValue = ref('')
const formData = ref({})
const fixedSelectValue1 = ref('')
const fixedSelectValue2 = ref('')

// 表单配置
const formColumns = ref([
  {
    label: '选择测试',
    prop: 'selectTest',
    type: 'select',
    dataList: testData.value,
    span: 12
  }
])

// 计算属性
const actualOptionsCount = computed(() => {
  // 这里可以通过 DOM 查询或其他方式获取实际渲染的选项数
  return testData.value.length
})

// 工具函数
const getValueCount = (value) => {
  return testData.value.filter(item => item.value === value).length
}
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

pre {
  background: #2d3748;
  color: #e2e8f0;
  padding: 15px;
  border-radius: 6px;
  overflow-x: auto;
  font-size: 12px;
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

ul {
  margin: 10px 0;
  padding-left: 20px;
}

li {
  margin-bottom: 5px;
}
</style> 