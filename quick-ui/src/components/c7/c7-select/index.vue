<template>
  <el-select
      v-bind="$attrs"
      v-model="selectedValue"
      :multiple="multiple"
      :loading="loading"
      filterable
      :remote="remote"
      :remote-method="handleRemoteSearch"
      @focus="onFocusLoad"
      @clear="clearOptions"
      @visible-change="visibleChange"
      @change="onChange"
  >
    <el-option
        v-for="(item, index) in options"
        :key="`${item[valueKey]}-${index}`"
        :label="item[labelKey]"
        :value="item[valueKey]"
        :disabled="item.disabled"
    />
    
    <!-- 默认 label 插槽 -->
    <template #label="{ label, value }">
      <slot name="label" :label="label" :value="value"/>
    </template>
  </el-select>
</template>

<script setup>
import { ref, computed, onMounted, defineOptions, defineProps } from 'vue'

defineOptions({ name: 'C7Select' })

// Props 定义
const props = defineProps({
  modelValue: { type: [String, Number, Array], default: '' },
  fetchData: { type: Function, default: null },
  dataFormatter: { type: Function, default: null },
  labelKey: { type: String, default: 'label' },
  valueKey: { type: String, default: 'value' },
  resultKey: { type: String, default: 'data' },
  options: { type: Array, default: null },
  dataList: { type: Array, default: [] },
  fetchParams: { type: Object, default: () => ({}) },
  autoLoad: { type: Boolean, default: true },
  separator: { type: Boolean, default: true },
  multiple: { type: Boolean, default: false },
  remote: { type: Boolean, default: false },
})

const emit = defineEmits(['update:modelValue', 'change', 'visibleChange'])

// 本地状态
const options = ref([])
const loading = ref(false)

// 组件挂载后自动加载数据
onMounted(() => {
  if (props.autoLoad && !props.remote) {
    fetchAndUpdate('')
  }
})

// 工具函数：标准化值
const normalizeValue = (value, multiple, separator) => {
  if (!multiple) return value
  if (typeof value === 'string') {
    return value === '' ? [] : value.split(',')
  }
  if (Array.isArray(value)) return value
  return value
}

// 工具函数：反标准化值
const denormalizeValue = (value, multiple, separator) => {
  if (!multiple || !separator || !Array.isArray(value)) {
    return value
  }
  return value.join(',')
}

// 计算属性：双向绑定处理
const selectedValue = computed({
  get: () => normalizeValue(props.modelValue, props.multiple, props.separator),
  set: (value) => {
    const payload = denormalizeValue(value, props.multiple, props.separator)
    emit('update:modelValue', payload)
  }
})

// 获取数据
const fetchAndUpdate = async (query = '') => {
  if (!props.fetchData) {
    // 使用静态数据
    const data = props.options || props.dataList
    options.value = Array.isArray(data) ? data : []
    return
  }

  loading.value = true
  try {
    const params = { ...props.fetchParams, query }
    const response = await props.fetchData(params)
    
    let data = response
    if (props.resultKey && response[props.resultKey]) {
      data = response[props.resultKey]
    }
    
    if (props.dataFormatter && typeof props.dataFormatter === 'function') {
      data = props.dataFormatter(data)
    }
    
    options.value = Array.isArray(data) ? data : []
  } catch (error) {
    console.error('获取数据失败:', error)
    options.value = []
  } finally {
    loading.value = false
  }
}

// 远程搜索
function handleRemoteSearch(query) {
  fetchAndUpdate(query)
}

// 聚焦时触发远程首次加载
function onFocusLoad() {
  if (props.remote && options.value.length === 0) {
    fetchAndUpdate('')
  }
}

// 清空选项列表
function clearOptions() {
  options.value = []
}

// 下拉面板显示/隐藏事件
function visibleChange(open) {
  emit('visibleChange', open)
}

// 选中值改变时的回调
function onChange(val) {
  emit('change', val)
}
</script>

<style scoped>
/* 可在此处添加组件样式 */
</style>
