<template>
  <el-button 
    v-bind="$attrs" 
    :icon="computedIcon" 
    :type="computedType" 
    :plain="computedPlain"
    :loading="loading"
    :size="computedSize"
    @click="handleClick"
  >
    <slot>{{ computedLabel }}</slot>
  </el-button>
</template>

<script setup>
import { ref, computed, defineOptions } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

defineOptions({
  name: 'C7Button'
})

// 按钮类型配置映射
const buttonConfigs = {
  add: { icon: 'Plus', label: '新增', type: 'primary', plain: true },
  edit: { icon: 'Edit', label: '修改', type: 'success', plain: true },
  delete: { icon: 'Delete', label: '删除', type: 'danger', plain: true },
  query: { icon: 'Search', label: '查询', type: 'primary', plain: false },
  refresh: { icon: 'Refresh', label: '重置', type: 'default', plain: false },
  upload: { icon: 'Upload', label: '上传', type: 'info', plain: true },
  download: { icon: 'Download', label: '下载', type: 'warning', plain: true },
  submit: { icon: '', label: '确定', type: 'primary', plain: true },
  cancel: { icon: '', label: '取消', type: 'info', plain: true }
}

const emit = defineEmits(["successCallback", "errorCallback"])

const props = defineProps({
  type: {
    type: String,
    default: 'primary'
  },
  btnType: {
    type: String,
    default: ''
  },
  plain: {
    type: Boolean,
    default: true
  },
  label: {
    type: String,
    default: ''
  },
  clickFunction: {
    type: Function,
    default: null
  },
  confirm: {
    type: Boolean,
    default: false
  },
  confirmMessage: {
    type: String,
    default: ''
  },
  isSuccessCallback: {
    type: Boolean,
    default: false
  },
  successMessage: {
    type: String,
    default: ''
  },
  isErrorCallback: {
    type: Boolean,
    default: false
  },
  checkSuccess: {
    type: Function,
    default: (res) => res.code === 200
  },
  errorMessageType: {
    type: String,
    default: 'res'
  },
  errorMessage: {
    type: String,
    default: 'msg'
  },
  validate: {
    type: Boolean,
    default: false
  },
  validateRef: {
    type: Object,
    default: null
  },
  size: {
    type: String,
    default: 'small',
    validator: (value) => ['large', 'default', 'small'].includes(value)
  }
})

// 加载状态
const loading = ref(false)

// 计算属性：按钮图标
const computedIcon = computed(() => {
  if (props.btnType && buttonConfigs[props.btnType]) {
    return buttonConfigs[props.btnType].icon
  }
  return ''
})

// 计算属性：按钮类型
const computedType = computed(() => {
  if (props.btnType && buttonConfigs[props.btnType]) {
    return buttonConfigs[props.btnType].type
  }
  return props.type
})

// 计算属性：按钮样式
const computedPlain = computed(() => {
  if (props.btnType && buttonConfigs[props.btnType]) {
    return buttonConfigs[props.btnType].plain
  }
  return props.plain
})

// 计算属性：按钮标签
const computedLabel = computed(() => {
  if (props.label) {
    return props.label
  }
  if (props.btnType && buttonConfigs[props.btnType]) {
    return buttonConfigs[props.btnType].label
  }
  return ''
})

// 计算属性：按钮大小
const computedSize = computed(() => {
  return props.size
})

// 防抖处理
const debounce = (func, wait) => {
  let timeout
  return function executedFunction(...args) {
    const later = () => {
      clearTimeout(timeout)
      func(...args)
    }
    clearTimeout(timeout)
    timeout = setTimeout(later, wait)
  }
}

// 点击处理
const handleClick = debounce(async () => {
  if (!props.clickFunction) return

  // 表单验证
  if (props.validate && props.validateRef) {
    try {
      await props.validateRef.validate()
    } catch (error) {
      return
    }
  }

  // 确认框
  if (props.confirm) {
    try {
      await ElMessageBox.confirm(
        props.confirmMessage || '确定要执行此操作吗？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      )
    } catch {
      return
    }
  }

  loading.value = true
  try {
    const result = await props.clickFunction()
    
    // 检查是否成功
    if (props.checkSuccess(result)) {
      if (props.isSuccessCallback) {
        emit('successCallback', result)
      }
      if (props.successMessage) {
        ElMessage.success(props.successMessage)
      }
    } else {
      throw new Error('操作失败')
    }
  } catch (error) {
    console.error('操作失败:', error)
    
    if (props.isErrorCallback) {
      emit('errorCallback', error)
    }
    
    let errorMsg = '操作失败'
    if (props.errorMessageType === 'res' && error.response) {
      errorMsg = error.response.data?.msg || errorMsg
    } else if (props.errorMessage) {
      errorMsg = props.errorMessage
    }
    
    ElMessage.error(errorMsg)
  } finally {
    loading.value = false
  }
}, 300)
</script>

<style scoped>
/* 可在此处添加组件样式 */
</style>
