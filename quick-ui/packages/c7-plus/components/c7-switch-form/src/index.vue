<template>
  <div class="c7-switch-form">
    <template v-if="currentConfig">
      <!-- 带头部的视图 -->
      <el-page-header 
        v-if="currentConfig.header"
        @back="handleBack(currentConfig.closeIndex)"
      >
        <template #content>
          <span class="switch-form-title">{{ currentConfig.title }}</span>
        </template>
        <div class="switch-form-content">
          <slot :name="currentConfig.name" :config="currentConfig" />
        </div>
      </el-page-header>

      <!-- 无头部的视图 -->
      <div v-else class="switch-form-content">
        <slot :name="currentConfig.name" :config="currentConfig" />
      </div>
    </template>

    <!-- 默认内容（当没有匹配的配置时） -->
    <div v-else class="switch-form-empty">
      <slot name="empty">
        <p>没有找到对应的视图配置</p>
      </slot>
    </div>
  </div>
</template>
<script setup lang="ts">
import { computed, defineOptions } from 'vue'

defineOptions({
  name: 'c7SwitchForm',
})

// 类型定义
interface SwitchConfig {
  name: string
  title?: string
  header?: boolean
  closeIndex?: string | number
  [key: string]: any
}

const emit = defineEmits<{
  'update:modelValue': [value: string | number]
  'back': [closeIndex: string | number, config: SwitchConfig]
  'change': [currentValue: string | number, config: SwitchConfig]
}>()

const props = defineProps<{
  showIndexs: SwitchConfig[]
  modelValue: string | number
  defaultView?: string
}>()

// 计算当前配置 - 使用计算属性优化性能
const currentConfig = computed((): SwitchConfig | null => {
  const config = props.showIndexs.find(item => item.name === props.modelValue)
  return config || null
})

// 处理返回操作
const handleBack = (closeIndex: string | number = '') => {
  const targetIndex = closeIndex || props.defaultView || ''
  emit('update:modelValue', targetIndex)
  
  if (currentConfig.value) {
    emit('back', closeIndex, currentConfig.value)
  }
}

// 切换视图
const switchTo = (viewName: string | number) => {
  if (props.modelValue !== viewName) {
    emit('update:modelValue', viewName)
    
    const config = props.showIndexs.find(item => item.name === viewName)
    if (config) {
      emit('change', viewName, config)
    }
  }
}

// 暴露方法供父组件调用
defineExpose({
  switchTo,
  currentConfig: currentConfig.value
})
</script>

<style scoped>
.c7-switch-form {
  width: 100%;
  height: 100%;
}

.switch-form-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--el-text-color-primary);
}

.switch-form-content {
  margin-top: 16px;
}

.switch-form-empty {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 200px;
  color: var(--el-text-color-placeholder);
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .switch-form-title {
    font-size: 16px;
  }
  
  .switch-form-content {
    margin-top: 12px;
  }
}
</style>
