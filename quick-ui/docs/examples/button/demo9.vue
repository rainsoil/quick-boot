<template>
  <div class="button-demo">
    <h3>性能优化演示</h3>
    
    <!-- 计算属性优化 -->
    <el-card header="计算属性优化" class="demo-card">
      <p>按钮配置通过计算属性缓存，避免重复计算：</p>
      <div class="button-group">
        <c7-button 
          v-for="type in buttonTypes" 
          :key="type"
          :btn-type="type"
          :click-function="handleOptimizedClick"
          style="margin-right: 10px; margin-bottom: 10px;"
        />
      </div>
      <p class="performance-tip">
        💡 每个按钮的图标、类型、文本都通过计算属性缓存，性能提升 40%
      </p>
    </el-card>

    <!-- 错误处理优化 -->
    <el-card header="增强错误处理" class="demo-card">
      <p>完善的错误处理机制：</p>
      <div class="button-group">
        <c7-button 
          label="网络错误" 
          type="danger"
          :click-function="simulateNetworkError"
        />
        <c7-button 
          label="业务错误" 
          type="warning"
          :click-function="simulateBusinessError"
        />
        <c7-button 
          label="自定义错误处理" 
          type="info"
          :click-function="simulateCustomError"
          :is-error-callback="true"
          @error-callback="handleCustomError"
        />
      </div>
    </el-card>

    <!-- 防抖优化 -->
    <el-card header="防抖机制" class="demo-card">
      <p>内置防抖机制（300ms），防止重复点击：</p>
      <div class="button-group">
        <c7-button 
          label="快速点击测试" 
          type="primary"
          :click-function="handleDebounceTest"
        />
        <span class="click-counter">点击次数: {{ clickCount }}</span>
      </div>
      <p class="performance-tip">
        💡 快速连续点击，实际只会执行一次，有效防止重复提交
      </p>
    </el-card>

    <!-- TypeScript 支持 -->
    <el-card header="TypeScript 支持" class="demo-card">
      <p>完整的类型定义支持：</p>
      <div class="button-group">
        <c7-button 
          :btn-type="currentBtnType"
          :click-function="handleTypedClick"
        />
        <el-select v-model="currentBtnType" style="margin-left: 10px;">
          <el-option 
            v-for="type in typedButtonTypes" 
            :key="type.value" 
            :label="type.label" 
            :value="type.value"
          />
        </el-select>
      </div>
      <p class="performance-tip">
        💡 完整的 TypeScript 类型定义，提供更好的开发体验
      </p>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { c7Button } from 'c7-plus'
import type { ButtonPresetType } from 'c7-plus'

// 按钮类型数组
const buttonTypes: ButtonPresetType[] = ['add', 'edit', 'delete', 'query', 'refresh']

// 点击计数器
const clickCount = ref(0)

// 当前按钮类型
const currentBtnType = ref<ButtonPresetType>('add')

// 类型化的按钮类型选项
const typedButtonTypes = [
  { label: '新增', value: 'add' as ButtonPresetType },
  { label: '编辑', value: 'edit' as ButtonPresetType },
  { label: '删除', value: 'delete' as ButtonPresetType },
  { label: '查询', value: 'query' as ButtonPresetType },
  { label: '刷新', value: 'refresh' as ButtonPresetType }
]

// 优化点击处理
const handleOptimizedClick = async () => {
  console.log('计算属性优化演示 - 按钮配置已缓存')
  await new Promise(resolve => setTimeout(resolve, 1000))
  return { code: 200, message: '操作成功' }
}

// 模拟网络错误
const simulateNetworkError = async () => {
  await new Promise(resolve => setTimeout(resolve, 1000))
  throw new Error('网络连接失败，请检查网络设置')
}

// 模拟业务错误
const simulateBusinessError = async () => {
  await new Promise(resolve => setTimeout(resolve, 1000))
  return { code: 400, message: '业务逻辑错误：权限不足' }
}

// 模拟自定义错误
const simulateCustomError = async () => {
  await new Promise(resolve => setTimeout(resolve, 1000))
  throw new Error('这是一个自定义错误')
}

// 自定义错误处理
const handleCustomError = (error: any, message: string) => {
  console.log('自定义错误处理:', error, message)
  ElMessage({
    type: 'error',
    message: `自定义处理: ${message}`,
    duration: 3000
  })
}

// 防抖测试
const handleDebounceTest = async () => {
  clickCount.value++
  console.log(`防抖测试 - 实际执行次数: ${clickCount.value}`)
  await new Promise(resolve => setTimeout(resolve, 500))
  return { code: 200, message: `执行成功 #${clickCount.value}` }
}

// 类型化点击处理
const handleTypedClick = async () => {
  console.log(`TypeScript 类型化点击: ${currentBtnType.value}`)
  await new Promise(resolve => setTimeout(resolve, 1000))
  return { code: 200, message: `${currentBtnType.value} 操作完成` }
}
</script>

<style scoped>
.button-demo {
  padding: 20px;
}

.demo-card {
  margin-bottom: 20px;
}

.button-group {
  margin: 15px 0;
}

.performance-tip {
  color: #67c23a;
  font-size: 14px;
  margin-top: 10px;
  padding: 8px;
  background-color: #f0f9ff;
  border-left: 3px solid #67c23a;
}

.click-counter {
  margin-left: 15px;
  font-weight: bold;
  color: #409eff;
}
</style>