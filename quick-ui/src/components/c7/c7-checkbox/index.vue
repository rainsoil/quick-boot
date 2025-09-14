<template>
  <!-- 全选复选框：仅在 props.indeterminate 为 true 时显示 -->
  <el-checkbox
      v-if="indeterminate"
      v-model="checkAll"
      :indeterminate="isIndeterminate"
      @change="handleCheckAllChange"
  >
    全选
  </el-checkbox>

  <!-- 多选组：绑定 checkList（数组/字符串） -->
  <el-checkbox-group v-model="checkList" @change="handleCheckedChange">
    <!-- 普通复选框样式 -->
    <el-checkbox
        v-if="!button"
        v-for="item in options"
        :key="item.value"
        :label="item.value"
    >
      {{ item.label }}
    </el-checkbox>

    <!-- 按钮样式复选框 -->
    <el-checkbox-button
        v-if="button"
        v-for="item in options"
        :key="item.value"
        :label="item.value"
    >
      {{ item.label }}
    </el-checkbox-button>
  </el-checkbox-group>
</template>

<script setup>
import {defineOptions, ref, watch, onMounted, computed} from 'vue'
import {useFetchOptions} from '../../../hooks/c7Hook.ts' // 自定义 hook，用于异步加载数据

// 定义组件名称
defineOptions({name: 'c7Checkbox'})

// 定义 emit 事件
const emit = defineEmits(['update:modelValue', 'change'])

// 定义 props 及其默认值
const props = defineProps({
  // 绑定值（支持数组或逗号分隔字符串）
  modelValue: {type: [Array, String], default: () => []},

  // 异步加载数据函数（返回 Promise）
  fetchData: {type: Function, default: null},

  // 静态数据列表（当 fetchData 为 null 时使用）
  dataList: {type: Array, default: () => []},

  // 异步请求参数
  fetchParams: {type: Object, default: () => ({})},

  // 异步返回结果中的数据路径（如 data.list）
  resultKey: {type: String, default: 'data'},

  // 数据格式化函数（可选）
  dataFormatter: {type: Function, default: null},

  // 是否显示全选控件（由 props.indeterminate 控制）
  indeterminate: {type: Boolean, default: false},

  // 是否将数组转换为逗号分隔字符串（true: 字符串绑定，false: 数组绑定）
  separator: {type: Boolean, default: true},

  // 数据项标签字段名（默认 label）
  labelKey: {type: String, default: 'label'},

  // 数据项值字段名（默认 value）
  valueKey: {type: String, default: 'value'},

  // 是否使用 el-checkbox-button 风格
  button: {type: Boolean, default: false},
})

// 使用自定义 hook 加载数据
const {options, loading, fetchAndUpdate} = useFetchOptions({
  fetchData: props.fetchData,
  fetchParams: props.fetchParams,
  resultKey: props.resultKey,
  dataFormatter: props.dataFormatter,
  dataList: props.dataList
})

// 组件挂载后自动加载数据
onMounted(() => {
  fetchAndUpdate('')
})

/**
 * 双向绑定的计算属性：处理数组与字符串之间的转换
 * - 当 separator == true 时：返回逗号分隔字符串
 * - 当 separator == false 时：返回数组
 */
const checkList = computed({
  get: () => {
    const val = props.modelValue
    if (props.separator) {
      // 字符串绑定模式：将字符串转换为数组
      if (typeof val === 'string') return val === '' ? [] : val.split(',')
      if (Array.isArray(val)) return val
      return val
    } else {
      // 数组绑定模式：强制返回数组（即使传入非数组）
      return Array.isArray(val) ? val : []
    }
  },
  set: (value) => {
    let payload = value
    if (props.separator && Array.isArray(value)) {
      // 字符串绑定模式：将数组转换为逗号分隔字符串
      payload = value.join(',')
    }
    emit('update:modelValue', payload)
  }
})

// 全选状态控制
const checkAll = ref(false)
const isIndeterminate = ref(true)

/**
 * 全选/全不选逻辑
 * @param {Boolean} val - 全选状态
 */
const handleCheckAllChange = (val) => {
  const allValues = options.value.map(item => item.value)
  checkList.value = val ? allValues : [] // 根据 val 设置全选或清空
  isIndeterminate.value = false // 取消中间状态
}

/**
 * 单个选项变化时的逻辑
 * @param {Array} value - 当前选中的值数组
 */
const handleCheckedChange = (value) => {
  const checkedCount = value.length
  checkAll.value = checkedCount === options.value.length // 判断是否全选
  isIndeterminate.value = checkedCount > 0 && checkedCount < options.value.length // 判断是否中间状态
  emit('change', value)
}

/**
 * 监听 options 数据变化，初始化全选状态
 * - 在数据加载完成后更新 checkAll 和 isIndeterminate
 */
watch(() => options.value, (newOptions) => {
  if (newOptions.length > 0) {
    const allValues = newOptions.map(item => item.value)
    const checkedCount = checkList.value.length
    checkAll.value = checkedCount === allValues.length
    isIndeterminate.value = checkedCount > 0 && checkedCount < allValues.length
  }
}, {immediate: true})
</script>