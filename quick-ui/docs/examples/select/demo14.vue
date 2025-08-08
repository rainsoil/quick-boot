<template>
  <div class="select-performance-demo">
    <h3>性能优化演示</h3>

    <!-- 数据处理优化 -->
    <el-card header="数据处理优化" class="demo-card">
      <p>使用工具函数处理多种数据格式：</p>

      <div class="demo-section">
        <h4>标准化数据处理</h4>
        <div class="select-group">
          <div class="select-item">
            <label>API 数据格式化：</label>
            <c7-select
              v-model="apiValue"
              :fetch-data="fetchApiData"
              :data-formatter="formatApiData"
              label-key="name"
              value-key="id"
              placeholder="从API获取数据"
            />
          </div>

          <div class="select-item">
            <label>多选分隔符处理：</label>
            <c7-select
              v-model="multiValue"
              :data-list="standardOptions"
              multiple
              :separator="true"
              placeholder="多选值用逗号分隔"
            />
          </div>

          <div class="select-item">
            <label>数组格式多选：</label>
            <c7-select
              v-model="arrayValue"
              :data-list="standardOptions"
              multiple
              :separator="false"
              placeholder="多选值为数组格式"
            />
          </div>
        </div>
      </div>

      <div class="value-display">
        <h4>当前值展示：</h4>
        <p>API 值: {{ apiValue }}</p>
        <p>多选值（字符串）: {{ multiValue }}</p>
        <p>多选值（数组）: {{ arrayValue }}</p>
      </div>
    </el-card>

    <!-- 计算属性缓存演示 -->
    <el-card header="计算属性缓存" class="demo-card">
      <p>计算属性避免重复计算，提升性能：</p>

      <div class="performance-stats">
        <el-statistic title="渲染次数" :value="renderCount" />
        <el-statistic title="计算次数" :value="computeCount" />
        <el-statistic title="缓存命中率" :value="cacheHitRate + '%'" />
      </div>

      <div class="demo-controls">
        <c7-select
          v-model="performanceValue"
          :data-list="largeDataSet"
          :data-formatter="countingFormatter"
          placeholder="大数据集测试"
          filterable
        />
        <el-button @click="triggerRerender">触发重新渲染</el-button>
        <el-button @click="resetStats">重置统计</el-button>
      </div>

      <p class="performance-tip">
        💡 使用计算属性缓存后，重复访问相同数据时直接返回缓存结果
      </p>
    </el-card>

    <!-- 工具函数集成 -->
    <el-card header="工具函数集成" class="demo-card">
      <p>集成 c7-plus 数据处理工具：</p>

      <div class="tool-demo">
        <h4>树形数据扁平化</h4>
        <c7-select
          v-model="treeValue"
          :data-list="flattenedTreeData"
          placeholder="扁平化的树形数据"
        />

        <h4>数据去重处理</h4>
        <c7-select
          v-model="uniqueValue"
          :data-list="uniqueData"
          placeholder="自动去重的数据"
        />

        <h4>数据分组展示</h4>
        <c7-select
          v-model="groupValue"
          :data-list="groupedData"
          group
          placeholder="按类型分组的数据"
        />
      </div>
    </el-card>

    <!-- 类型安全演示 -->
    <el-card header="TypeScript 类型安全" class="demo-card">
      <p>完整的类型定义支持：</p>

      <div class="type-demo">
        <h4>类型化选项</h4>
        <c7-select
          v-model="typedValue"
          :data-list="typedOptions"
          placeholder="类型安全的选项"
        />

        <div class="code-example">
          <h4>类型定义示例：</h4>
          <pre><code>interface Option {
  label: string
  value: string | number
  disabled?: boolean
  children?: Option[]
}

const options: Option[] = [
  { label: '选项1', value: 1 },
  { label: '选项2', value: 2, disabled: true }
]</code></pre>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { c7Select } from 'c7-plus'

// 定义 Option 类型（本地）
interface Option {
  label: string
  value: string | number
  disabled?: boolean
  children?: Option[]
}

// 基础数据
const apiValue = ref('')
const multiValue = ref('')
const arrayValue = ref([])
const performanceValue = ref('')
const treeValue = ref('')
const uniqueValue = ref('')
const groupValue = ref('')
const typedValue = ref<string | number>('')

// 性能统计
const renderCount = ref(0)
const computeCount = ref(0)
const cacheHitRate = computed(() => {
  if (computeCount.value === 0) return 100
  return Math.round((1 - computeCount.value / renderCount.value) * 100)
})

// 标准选项数据
const standardOptions: Option[] = [
  { label: '选项1', value: '1' },
  { label: '选项2', value: '2' },
  { label: '选项3', value: '3' },
  { label: '选项4', value: '4' },
  { label: '选项5', value: '5' }
]

// 类型化选项
const typedOptions: Option[] = [
  { label: '字符串值', value: 'string' },
  { label: '数字值', value: 123 },
  { label: '布尔值', value: 'boolean' },
  { label: '禁用选项', value: 'disabled', disabled: true }
]

// 大数据集（用于性能测试）
const largeDataSet = ref<Option[]>([])

// 树形数据
const treeData = [
  {
    label: '前端技术',
    value: 'frontend',
    children: [
      { label: 'Vue.js', value: 'vue' },
      { label: 'React', value: 'react' },
      { label: 'Angular', value: 'angular' }
    ]
  },
  {
    label: '后端技术',
    value: 'backend',
    children: [
      { label: 'Node.js', value: 'nodejs' },
      { label: 'Python', value: 'python' },
      { label: 'Java', value: 'java' }
    ]
  }
]

// 本地工具函数定义
const flattenTreeData = (tree: any[]): Option[] => {
  const result: Option[] = []
  const traverse = (nodes: any[], prefix = '') => {
    nodes.forEach(node => {
      result.push({
        label: prefix + node.label,
        value: node.value,
        disabled: node.disabled
      })
      if (node.children && node.children.length > 0) {
        traverse(node.children, prefix + node.label + ' > ')
      }
    })
  }
  traverse(tree)
  return result
}

const uniqueArray = (arr: any[], key: string) => {
  const seen = new Set()
  return arr.filter(item => {
    const value = item[key]
    if (seen.has(value)) {
      return false
    }
    seen.add(value)
    return true
  })
}

const groupBy = (arr: any[], key: string) => {
  return arr.reduce((groups, item) => {
    const group = item[key]
    if (!groups[group]) {
      groups[group] = []
    }
    groups[group].push(item)
    return groups
  }, {})
}

// 扁平化的树形数据
const flattenedTreeData = computed(() => {
  return flattenTreeData(treeData)
})

// 重复数据
const duplicateData = [
  { label: '选项A', value: 'a' },
  { label: '选项B', value: 'b' },
  { label: '选项A', value: 'a' }, // 重复
  { label: '选项C', value: 'c' },
  { label: '选项B', value: 'b' }  // 重复
]

// 去重后的数据
const uniqueData = computed(() => {
  return uniqueArray(duplicateData, 'value')
})

// 分组数据
const rawGroupData = [
  { label: 'Vue组件', value: 'vue-comp', type: '前端' },
  { label: 'React组件', value: 'react-comp', type: '前端' },
  { label: 'Node模块', value: 'node-mod', type: '后端' },
  { label: 'Python包', value: 'py-pkg', type: '后端' }
]

const groupedData = computed(() => {
  const grouped = groupBy(rawGroupData, 'type')
  return Object.entries(grouped).map(([label, options]) => ({
    label,
    options
  }))
})

// 模拟API数据获取
const fetchApiData = async () => {
  await new Promise(resolve => setTimeout(resolve, 1000))
  return {
    code: 200,
    data: [
      { id: 1, name: '北京', code: 'BJ' },
      { id: 2, name: '上海', code: 'SH' },
      { id: 3, name: '深圳', code: 'SZ' }
    ]
  }
}

// API数据格式化
const formatApiData = (data: any[]) => {
  return data.map(item => ({
    label: `${item.name} (${item.code})`,
    value: item.id
  }))
}

// 计数格式化器（用于性能测试）
const countingFormatter = (data: any[]) => {
  computeCount.value++
  return data
}

// 触发重新渲染
const triggerRerender = () => {
  renderCount.value++
  // 强制触发响应式更新
  performanceValue.value = performanceValue.value
}

// 重置统计
const resetStats = () => {
  renderCount.value = 0
  computeCount.value = 0
}

// 初始化大数据集
onMounted(() => {
  largeDataSet.value = Array.from({ length: 1000 }, (_, i) => ({
    label: `选项 ${i + 1}`,
    value: i + 1
  }))
})
</script>

<style scoped>
.select-performance-demo {
  padding: 20px;
}

.demo-card {
  margin-bottom: 20px;
}

.demo-section {
  margin: 15px 0;
}

.select-group {
  display: grid;
  gap: 15px;
}

.select-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.select-item label {
  min-width: 140px;
  font-weight: 500;
}

.value-display {
  margin-top: 20px;
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 4px;
}

.value-display h4 {
  margin-top: 0;
  margin-bottom: 10px;
}

.value-display p {
  margin: 5px 0;
  font-family: 'Courier New', monospace;
  color: #409eff;
}

.performance-stats {
  display: flex;
  gap: 30px;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f0f9ff;
  border-radius: 4px;
}

.demo-controls {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 15px;
}

.performance-tip {
  color: #67c23a;
  font-size: 14px;
  margin-top: 10px;
  padding: 8px;
  background-color: #f0f9ff;
  border-left: 3px solid #67c23a;
}

.tool-demo h4 {
  margin-top: 20px;
  margin-bottom: 10px;
  color: #303133;
}

.type-demo {
  margin-top: 15px;
}

.code-example {
  margin-top: 20px;
}

.code-example pre {
  background-color: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
  overflow-x: auto;
}

.code-example code {
  color: #e6a23c;
  font-size: 13px;
  line-height: 1.5;
}

@media (max-width: 768px) {
  .performance-stats {
    flex-direction: column;
    gap: 15px;
  }

  .demo-controls {
    flex-direction: column;
    align-items: stretch;
  }

  .select-item {
    flex-direction: column;
    align-items: stretch;
  }

  .select-item label {
    min-width: auto;
  }
}
</style>
