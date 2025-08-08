<template>
  <el-upload
      v-model:file-list="fileList"
      :action="uploadUrl"
      :list-type="listType"
      :on-preview="handlePictureCardPreview"
      :before-remove="handleDelete"
      :on-success="handleUploadSuccess"
      :before-upload="handleBeforeUpload"
      :on-error="handleError"
      :limit="limit"
      :accept="acceptTypes"
      :headers="headers"
      :class="{ hide: fileList.length >= limit }"
      :show-file-list="true"
  >
    <el-icon>
      <Plus/>
    </el-icon>
  </el-upload>
  <!-- 上传提示 -->
  <div class="el-upload__tip" v-if="showTip">
    <slot name="tip">
      请上传{{ tipContent }}的文件
    </slot>
  </div>
  <el-dialog v-model="dialogVisible">
    <el-image w-full :src="dialogImageUrl" alt="Preview Image" :preview-src-list="srcList"/>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref, watch, computed} from 'vue'
import {Plus} from '@element-plus/icons-vue'
import {ElLoading, ElMessage, UploadProps, UploadUserFile} from 'element-plus'

defineOptions({
  name: 'c7Upload'
})

// 参数
const props = defineProps({
  modelValue: [String, Array],
  // 上传url
  uploadUrl: {
    type: String,
    default: ''
  },
  // 大小限制(MB)
  fileSize: {
    type: Number,
    default: 5,
  },
  // 限制上传文件的类型（后缀），多个用逗号分隔，例如 "jpg,png,gif"
  fileType: {
    type: String,
    default: 'jpg,png'
  },
  // 图片数量限制
  limit: {
    type: Number,
    default: 1,
  },
  // 删除文件的接口
  deleteUrl: {
    type: String,
    default: ''
  },
  headers: {
    type: Object,
    default: () => ({})
  },
  listType: {
    type: String,
    default: 'picture-card'
  },
  // 是否显示提示信息
  showTip: {
    type: Boolean,
    default: true
  },
  // 提示信息内容
  tipText: {
    type: String,
    default: ''
  }
});

const emit = defineEmits(['update:modelValue'])

// 计算属性：提示内容
const tipContent = computed(() => {
  const parts = []
  if (props.tipText) {
    parts.push(props.tipText)
  }
  if (props.fileSize) {
    parts.push(`大小不超过 ${props.fileSize}MB`)
  }
  if (props.fileType) {
    parts.push(`格式为 ${props.fileType}`)
  }
  return parts.join('，')
})

// 计算属性：接受的文件类型
const acceptTypes = computed(() => {
  if (!props.fileType) return ''
  return props.fileType.split(',').map(type => `.${type.trim()}`).join(',')
})

const uploadCount = ref(0)
let loadingInstance: any
const uploadList = ref<UploadUserFile[]>([])
const srcList = ref<string[]>([])
const fileList = ref<UploadUserFile[]>([])
const dialogImageUrl = ref('')
const dialogVisible = ref(false)

// 文件验证函数
const validateFile = (file: File): boolean => {
  const errors: string[] = []
  
  // 文件类型验证
  if (props.fileType) {
    const allowedTypes = props.fileType.split(',').map(t => t.trim().toLowerCase())
    const fileExtension = file.name.split('.').pop()?.toLowerCase() || ''
    if (!allowedTypes.includes(fileExtension)) {
      errors.push(`文件类型必须是: ${allowedTypes.join(', ')}`)
    }
  }

  // 文件大小验证
  if (props.fileSize) {
    const fileSizeInMB = file.size / 1024 / 1024
    if (fileSizeInMB > props.fileSize) {
      errors.push(`文件大小不能超过 ${props.fileSize}MB`)
    }
  }

  if (errors.length > 0) {
    ElMessage.error(errors.join('；'))
    return false
  }

  return true
}

// 上传前处理
function handleBeforeUpload(file: File) {
  if (!validateFile(file)) {
    return false
  }
  
  showLoading("正在上传文件，请稍候...")
  uploadCount.value++
  return true
}

// 删除文件
function handleDelete(file: any) {
  const fileIndex = fileList.value.findIndex(f => f.name === file.name)
  if (fileIndex === -1) return true

  const deleteFile = fileList.value[fileIndex]
  
  // 从文件列表中移除
  fileList.value.splice(fileIndex, 1)
  emit("update:modelValue", formatFileList(fileList.value))

  // 调用删除接口（如果提供了删除URL）
  if (props.deleteUrl && deleteFile.url) {
    callDeleteAPI(deleteFile.url)
  }

  return false
}

// 调用删除API
const callDeleteAPI = async (fileUrl: string) => {
  try {
    const deleteParams = { fileUrl }
    // 这里需要根据实际的HTTP客户端进行调用
    // const response = await httpClient.post(props.deleteUrl, deleteParams, { headers: props.headers })
    // if (response.code === 200) {
    //   ElMessage.success("删除成功")
    // } else {
    //   ElMessage.error("删除失败")
    // }
    console.log("调用删除接口，参数:", deleteParams)
  } catch (error) {
    console.error('删除文件失败:', error)
    ElMessage.error("删除失败")
  }
}

// 预览图片
const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  if (uploadFile.url) {
    dialogImageUrl.value = uploadFile.url
    srcList.value = [uploadFile.url]
    dialogVisible.value = true
  }
}

// 监听 modelValue 变化，同步到 fileList
watch(() => props.modelValue, (val) => {
  if (val) {
    const list = Array.isArray(val) ? val : String(val).split(",")
    fileList.value = list.map(item => {
      if (typeof item === "string") {
        return { name: item, url: item }
      }
      return item
    }).filter(item => item.url) // 过滤掉无效的文件
  } else {
    fileList.value = []
  }
}, { deep: true, immediate: true })

// 上传成功回调
function handleUploadSuccess(res: any, file: any) {
  uploadCount.value--
  
  if (res.code === 200) {
    const newFile = {
      name: res.data.fileName || file.name,
      url: res.data.url,
      fileId: res.data.id
    }
    uploadList.value.push(newFile)
    checkUploadComplete()
  } else {
    closeLoading()
    ElMessage.error(res.msg || '上传失败')
    checkUploadComplete()
  }
}

// 检查所有文件是否上传完成
function checkUploadComplete() {
  if (uploadCount.value === 0) {
    // 合并已有文件和新上传的文件
    fileList.value = [...fileList.value.filter(f => f.url && !f.url.startsWith('blob:')), ...uploadList.value]
    uploadList.value = []
    emit("update:modelValue", formatFileList(fileList.value))
    closeLoading()
  }
}

// 格式化文件列表为字符串
function formatFileList(list: UploadUserFile[]): string {
  return list
    .filter(item => item.url && !item.url.startsWith('blob:'))
    .map(item => item.url)
    .join(',')
}

// 显示加载状态
function showLoading(content: string) {
  loadingInstance = ElLoading.service({
    lock: true,
    text: content,
    background: "rgba(0, 0, 0, 0.7)",
  })
}

// 上传错误处理
function handleError(error: any) {
  uploadCount.value--
  closeLoading()
  console.error('上传错误:', error)
  ElMessage.error('上传失败，请重试')
}

// 关闭加载状态
function closeLoading() {
  if (loadingInstance) {
    loadingInstance.close()
    loadingInstance = null
  }
}
</script>

