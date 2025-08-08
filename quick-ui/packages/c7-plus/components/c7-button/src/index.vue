<!-- 对按钮的封装-->
<template>
  <el-button 
    v-bind="$attrs" 
    :icon="computedIcon" 
    :type="computedType" 
    :plain="computedPlain"
    :loading="loading"
    @click="handleClick"
  >
    <slot>{{ computedLabel }}</slot>
  </el-button>
</template>

<script setup>
import {ref, computed, defineOptions} from 'vue';
import pkg from 'lodash';
import {ElMessage, ElMessageBox} from 'element-plus'
import {jsonGet} from "../../../utils/utils.ts";

const {debounce} = pkg;

defineOptions({
  name: 'c7Button'
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
  // 类型 (新增/修改/删除/查询/刷新/普通)
  btnType: {
    type: String,
    default: ''
  },
  // 是否为朴素按钮
  plain: {
    type: Boolean,
    default: true
  },
  // 按钮内容
  label: {
    type: String,
    default: ''
  },
  // 图标
  icon: {
    type: String,
    default: ''
  },
  // promise
  clickFunction: {type: Function, default: null},
  // 是否弹出确认框
  confirm: {
    type: Boolean,
    default: false,
  },
  // 确认框提示内容
  confirmMessage: {
    type: String,
    default: "",
  },
  // 是否开启成功之后回调
  isSuccessCallback: {
    type: Boolean,
    default: false,
  },
  // 成功之后提示信息(当isSuccessCallback为false的时候,才启动)
  successMessage: {
    type: String,
    default: "",
  },
  //是否开启失败回调
  isErrorCallback: {
    type: Boolean,
    default: false,
  },
  //检查接口调用是否成功 checkSuccess
  checkSuccess: {
    type: Function,
    default: (res) => res.code === 200,
  },
  // 错误提示类型(msg:直接错误提示,res:从结果中获取)
  errorMessageType: {
    type: String,
    default: "res",
  },
  // 错误提示信息(当isErrorCallback为false的时候,才启动)
  errorMessage: {
    type: String,
    default: "msg",
  },
  // 是否进行参数校验
  validate: {
    type: Boolean,
    default: false,
  },
  // 参数校验的ref
  validateRef: {
    type: Object,
    default: null,
  }
})

// 计算属性优化性能
const buttonConfig = computed(() => buttonConfigs[props.btnType] || {})

const computedIcon = computed(() => {
  return props.icon || buttonConfig.value.icon || ''
})

const computedType = computed(() => {
  return buttonConfig.value.type || props.type
})

const computedLabel = computed(() => {
  return buttonConfig.value.label || props.label
})

const computedPlain = computed(() => {
  return buttonConfig.value.plain !== undefined ? buttonConfig.value.plain : props.plain
})

const loading = ref(false)
let isProcessing = ref(false) // 用于防止重复点击

// 统一的点击处理函数
const handleClick = debounce(() => {
  if (isProcessing.value || !props.clickFunction) return

  if (props.confirm) {
    showConfirmDialog()
  } else {
    executeClickFunction()
  }
}, 300)

// 确认对话框
const showConfirmDialog = () => {
  ElMessageBox.confirm(props.confirmMessage, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    executeClickFunction();
  }).catch(() => {
    // 确认框取消时重置状态
    isProcessing.value = false;
  });
}

// 执行点击函数
async function executeClickFunction() {
  if (isProcessing.value) return
  
  isProcessing.value = true
  loading.value = true
  
  try {
    // 表单验证
    if (props.validate && props.validateRef) {
      const isValid = await validateForm()
      if (!isValid) return
    }

    // 执行业务逻辑
    const result = await props.clickFunction()
    
    if (result && props.checkSuccess(result)) {
      handleSuccess(result)
    } else if (result) {
      handleError(result)
    }

  } catch (err) {
    console.error('Button click error:', err)
    const errorMsg = err?.message || err || '操作失败'
    
    if (props.isErrorCallback) {
      emit('errorCallback', err, errorMsg);
    } else {
      ElMessage.error(errorMsg);
    }
  } finally {
    loading.value = false
    isProcessing.value = false
  }
}

// 表单验证
const validateForm = () => {
  return new Promise((resolve) => {
    if (!props.validateRef) {
      resolve(false)
      return
    }

    props.validateRef.validate((valid, fields) => {
      if (valid) {
        resolve(true)
      } else {
        emit('errorCallback', fields);
        resolve(false)
      }
    })
  })
}

// 处理成功响应
const handleSuccess = (result) => {
  if (props.isSuccessCallback) {
    emit('successCallback', result);
  } else if (props.successMessage) {
    ElMessage.success(props.successMessage);
  }
}

// 处理错误响应
const handleError = (result) => {
  let msg = '';
  if (props.errorMessageType === 'res') {
    msg = jsonGet(result, props.errorMessage) || '操作失败';
  } else if (props.errorMessageType === 'msg') {
    msg = props.errorMessage || '操作失败';
  }
  
  if (props.isErrorCallback) {
    emit('errorCallback', result, msg);
  } else {
    ElMessage.error(msg);
  }
}

</script>

