<template>


  <el-select
      v-bind="$attrs"
      v-model="selectedValue"
      :multiple="multiple"
      :allow-create="tag"
      :loading="loading"
      filterable
      :remote="remote"
      :remote-method="handleRemoteSearch"
      @focus="onFocusLoad"
      @clear="clearOptions"
      @visible-change="visibleChange"
      @change="onChange"
  >

    <el-option-group
        v-for="groupOptions in options"
        :key="groupOptions.label"
        :label="groupOptions.label"
        v-if="group"
    >
      <el-option
          v-for="(item, index) in groupOptions.options"
          :key="`${item[valueKey]}-${index}`"
          :label="item[labelKey]"
          :value="item[valueKey]"
          :disabled="item.disabled ? true : false"
      />
    </el-option-group>
    <el-option
        v-if="!group"
        v-for="(item, index) in options"
        :key="`${item[valueKey]}-${index}`"
        :label="item[labelKey]"
        :value="item[valueKey]"
        :disabled="item.disabled ? true : false"
    />
    <!-- 默认 label 插槽 -->
    <template #label="{ label, value }">
      <slot name="label" :label="label" :value="value"/>
    </template>
  </el-select>
</template>

<script setup>
import {ref, computed, onMounted, defineOptions, defineProps} from 'vue'
import {useFetchOptions} from '../../../hooks/c7Hook.ts'

// 定义组件名称
defineOptions({name: 'c7Select'})

// Props 定义
const props = defineProps({
  // 绑定值，支持 String/Number/Array
  modelValue: {type: [String, Number, Array], default: ''},
  // 异步获取数据的函数，统一用于初始化加载与远程搜索
  fetchData: {type: Function, default: null},
  // 数据格式化回调，接收原始 list，返回格式化后的 list
  dataFormatter: {type: Function, default: null},
  // el-option 的 label 字段名
  labelKey: {type: String, default: 'label'},
  // el-option 的 value 字段名
  valueKey: {type: String, default: 'value'},
  // 异步结果中列表数据所在的路径
  resultKey: {type: String, default: 'data'},
  // 静态数据列表，当 fetchData 为 null 时使用
  dataList: {type: Array, default: []},
  // 数据
  options: {
    type: Array,
    default: null,
  },
  // 异步获取数据时的额外参数
  fetchParams: {type: Object, default: () => ({})},
  // 是否在组件挂载后自动加载数据（仅在非远程模式）
  autoLoad: {type: Boolean, default: true},
  // 多选模式下是否使用逗号分隔符来存储
  separator: {type: Boolean, default: true},
  // 是否开启多选模式
  multiple: {type: Boolean, default: false},
  // 是否开启 tag 模式（允许用户输入不存在的选项）
  tag: {type: Boolean, default: false},
  // 是否远程搜索模式
  remote: {type: Boolean, default: false},

  // 是否开启分组
  group: {type: Boolean, default: false},
})

// emits 声明
const emit = defineEmits(['update:modelValue', 'change', 'visibleChange'])

// 本地状态：选项列表和加载状态

// 组件挂载后自动加载数据（仅在非远程且 autoLoad=true 时）
onMounted(() => {
  if (props.autoLoad && !props.remote) {
    fetchAndUpdate('')
  }
})

console.log(props)
// 工具函数：标准化值（将字符串转为数组，用于多选模式）
const normalizeValue = (value, multiple, separator) => {
  if (!multiple) return value

  if (typeof value === 'string') {
    return value === '' ? [] : value.split(',')
  }

  if (Array.isArray(value)) return value

  return value
}

// 工具函数：反标准化值（将数组转为字符串，用于输出）
const denormalizeValue = (value, multiple, separator) => {
  if (!multiple || !separator || !Array.isArray(value)) {
    return value
  }

  return value.join(',')
}

// 计算属性：双向绑定处理，内部维护数组/字符串之间的相互转换
const selectedValue = computed({
  get: () => normalizeValue(props.modelValue, props.multiple, props.separator),
  set: (value) => {
    const payload = denormalizeValue(value, props.multiple, props.separator)
    emit('update:modelValue', payload)
  }
})

// 远程搜索或初始化调用统一函数，委托给 fetchData
function handleRemoteSearch(query) {
  fetchAndUpdate(query)
}

// 聚焦时触发远程首次加载
function onFocusLoad() {
  if (props.remote && options.value.length === 0) {
    fetchAndUpdate('')
  }
}

console.log(props.options)

// 使用抽取的 useFetchOptions hook
const {options, loading, fetchAndUpdate} = useFetchOptions({
  fetchData: props.fetchData,
  fetchParams: props.fetchParams,
  resultKey: props.resultKey,
  dataFormatter: props.dataFormatter,
  dataList: props.options || props.dataList // 优先使用 options，兼容 dataList
})


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
