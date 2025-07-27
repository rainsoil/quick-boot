<template>
  <div>
    <!-- 单选按钮组 -->
    <el-radio-group
        v-model="innerValue"
        @change="handleChange"
    >
      <el-radio
          v-for="item in options"
          :key="item.value"
          :label="item.value"
      >
        {{ item.label }}
      </el-radio>
    </el-radio-group>
  </div>
</template>

<script setup>
import {defineOptions, ref, computed, defineProps, defineEmits} from 'vue'

// 组件名称
defineOptions({
  name: 'c7Radio'
})

// 定义更新事件
const emit = defineEmits(['update:modelValue', 'change'])

// 接收属性
const props = defineProps({
  // 支持单选值类型，可为字符串或数字
  modelValue: {type: [String, Number], default: ''},
  // 静态数据列表
  dataList: {type: Array, default: () => []}
})

// 内部绑定值
const innerValue = computed({
  get() {
    return props.modelValue
  },
  set(val) {
    emit('update:modelValue', val)
  }
})

// 选项列表直接使用dataList
const options = computed(() => {
  return props.dataList
})

// 处理选中后触发 change 事件
function handleChange(val) {
  emit('change', val)
}
</script>
