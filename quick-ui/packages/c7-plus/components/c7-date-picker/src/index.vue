<template>
  <el-date-picker
      v-bind="$attrs"
      v-model="internalValue"
      :type="props.type"
      :format="computedFormat"
      :value-format="computedValueFormat"
      @update:modelValue="handleValueChange"
  >
    <slot></slot>
  </el-date-picker>
</template>

<script setup lang="ts">
import { ref, computed, watch, defineOptions } from 'vue'

defineOptions({ name: 'c7DatePicker' })

// 默认格式映射
const defaultFormatMap = {
  date: 'YYYY-MM-DD',
  daterange: 'YYYY-MM-DD',
  year: 'YYYY',
  month: 'YYYY-MM',
  datetime: 'YYYY-MM-DD HH:mm:ss',
  datetimerange: 'YYYY-MM-DD HH:mm:ss'
} as const

// Props
const props = defineProps({
  modelValue: [String, Array, Date],
  type: {
    type: String,
    default: 'date'
  },
  format: {
    type: String,
    default: ''
  },
  valueFormat: {
    type: String,
    default: ''
  },
  rangeMerge: {
    type: Boolean,
    default: true
  }
})

const emit = defineEmits(['update:modelValue'])

// 默认 format & valueFormat 根据 type 推断
const computedFormat = computed(() => props.format || defaultFormatMap[props.type] || 'YYYY-MM-DD')
const computedValueFormat = computed(() => props.valueFormat || defaultFormatMap[props.type] || 'YYYY-MM-DD')

// 支持 v-model 双向绑定
const internalValue = ref<any>(props.modelValue)

// 监听外部 modelValue 变更并处理 range 拆分
watch(
    () => props.modelValue,
    (val) => {
      if (['daterange', 'datetimerange'].includes(props.type) && typeof val === 'string' && props.rangeMerge) {
        const range = val.split(',')
        internalValue.value = range.length === 2 ? range : []
      } else {
        internalValue.value = val
      }
    },
    { immediate: true }
)

// 值变更触发
const handleValueChange = (val: any) => {
  if (['daterange', 'datetimerange'].includes(props.type) && Array.isArray(val) && props.rangeMerge) {
    emit('update:modelValue', val.join(','))
  } else {
    emit('update:modelValue', val)
  }
}
</script>
