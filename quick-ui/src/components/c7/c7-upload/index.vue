<template>
  <el-upload
      v-bind="$attrs"
      :action="uploadUrl"
      :headers="headers"
      :file-list="fileList"
      :limit="limit"
      :accept="acceptTypes"
      :before-upload="beforeUpload"
      :on-success="onSuccess"
      :on-error="onError"
      :on-remove="onRemove"
      :on-exceed="onExceed"
      :list-type="listType"
      :show-file-list="showFileList"
  >
    <slot>
      <el-button type="primary">点击上传</el-button>
    </slot>
    
    <template #tip>
      <div class="el-upload__tip" v-if="tip">
        {{ tip }}
      </div>
    </template>
  </el-upload>
</template>

<script setup>
import { ref, computed, watch, defineOptions, defineProps } from 'vue'
import { ElMessage } from 'element-plus'

defineOptions({ name: 'C7Upload' })

const props = defineProps({
  modelValue: { type: String, default: '' },
  uploadUrl: { type: String, default: '' },
  deleteUrl: { type: String, default: '' },
  fileSize: { type: Number, default: 5 },
  fileType: { type: String, default: 'jpg,png' },
  limit: { type: Number, default: 1 },
  headers: { type: Object, default: () => ({}) },
  listType: { type: String, default: 'picture-card' },
  showFileList: { type: Boolean, default: true }
})

const emit = defineEmits(['update:modelValue', 'change'])

// 文件列表
const fileList = ref([])

// 计算属性：接受的文件类型
const acceptTypes = computed(() => {
  const types = props.fileType.split(',').map(type => `.${type.trim()}`)
  return types.join(',')
})

// 计算属性：提示信息
const tip = computed(() => {
  const sizeText = `文件大小不超过 ${props.fileSize}MB`
  const typeText = `支持 ${props.fileType} 格式`
  return `${sizeText}，${typeText}`
})

// 监听 modelValue 变化，更新文件列表
watch(() => props.modelValue, (newVal) => {
  if (newVal) {
    const urls = newVal.split(',').filter(url => url.trim())
    fileList.value = urls.map((url, index) => ({
      name: `file-${index + 1}`,
      url: url.trim(),
      status: 'success'
    }))
  } else {
    fileList.value = []
  }
}, { immediate: true })

// 上传前验证
const beforeUpload = (file) => {
  // 文件大小验证
  const isValidSize = file.size / 1024 / 1024 < props.fileSize
  if (!isValidSize) {
    ElMessage.error(`文件大小不能超过 ${props.fileSize}MB!`)
    return false
  }

  // 文件类型验证
  const fileExtension = file.name.split('.').pop().toLowerCase()
  const allowedTypes = props.fileType.split(',').map(type => type.trim().toLowerCase())
  const isValidType = allowedTypes.includes(fileExtension)
  if (!isValidType) {
    ElMessage.error(`只支持 ${props.fileType} 格式的文件!`)
    return false
  }

  return true
}

// 上传成功
const onSuccess = (response, file) => {
  // 假设后端返回格式为 { code: 200, data: { url: 'xxx' } }
  let fileUrl = ''
  if (response && response.data && response.data.url) {
    fileUrl = response.data.url
  } else if (typeof response === 'string') {
    fileUrl = response
  }

  if (fileUrl) {
    updateModelValue(fileUrl)
    ElMessage.success('上传成功!')
  } else {
    ElMessage.error('上传失败，未获取到文件地址!')
  }
}

// 上传失败
const onError = (error) => {
  console.error('上传失败:', error)
  ElMessage.error('上传失败!')
}

// 删除文件
const onRemove = (file) => {
  const currentUrls = props.modelValue ? props.modelValue.split(',') : []
  const newUrls = currentUrls.filter(url => url.trim() !== file.url)
  updateModelValue(newUrls.join(','))
}

// 超出限制
const onExceed = () => {
  ElMessage.warning(`最多只能上传 ${props.limit} 个文件!`)
}

// 更新 modelValue
const updateModelValue = (value) => {
  emit('update:modelValue', value)
  emit('change', value)
}
</script>

<style scoped>
.el-upload__tip {
  color: #606266;
  font-size: 12px;
  margin-top: 7px;
}
</style>
