<template>
  <div class="upload-demo">
    <h3>文件验证增强演示</h3>

    <!-- 智能文件验证 -->
    <el-card header="智能文件验证" class="demo-card">
      <p>增强的文件类型、大小、数量验证：</p>
      <div class="upload-group">
        <c7-upload
            v-model="imageFiles"
            upload-url="/api/upload"
            file-type="jpg,png,gif,webp"
            :file-size="2"
            :limit="3"
            tip-text="图片文件"
            list-type="picture-card"
            multiple
        />
      </div>
      <p class="validation-info">
        ✅ 支持：jpg, png, gif, webp | 最大 2MB | 最多 3 个文件
      </p>
    </el-card>

    <!-- 自定义提示信息 -->
    <el-card header="动态提示信息" class="demo-card">
      <p>根据配置自动生成提示信息：</p>
      <div class="upload-group">
        <c7-upload
            v-model="documentFiles"
            upload-url="/api/upload"
            file-type="pdf,doc,docx,xls,xlsx"
            :file-size="10"
            :limit="5"
            list-type="text"
        >
          <template #tip>
            <div class="custom-tip">
              📄 支持上传办公文档，包括 PDF、Word、Excel 等格式
              <br>
              📏 单个文件不超过 10MB，最多可上传 5 个文件
            </div>
          </template>
        </c7-upload>
      </div>
    </el-card>

    <!-- 错误处理演示 -->
    <el-card header="错误处理优化" class="demo-card">
      <p>详细的错误提示和处理：</p>
      <div class="upload-group">
        <c7-upload
            v-model="restrictedFiles"
            upload-url="/api/upload"
            file-type="jpg,png"
            :file-size="0.5"
            :limit="1"
            @error="handleUploadError"
        />
      </div>
      <p class="validation-info">
        ⚠️ 严格限制：仅 jpg、png | 最大 0.5MB | 仅 1 个文件（用于测试错误提示）
      </p>

      <div v-if="errorMessages.length > 0" class="error-display">
        <h4>错误信息记录：</h4>
        <ul>
          <li v-for="(error, index) in errorMessages" :key="index" class="error-item">
            {{ error }}
          </li>
        </ul>
        <el-button @click="clearErrors" size="small">清除错误记录</el-button>
      </div>
    </el-card>

    <!-- 文件类型预设 -->
    <el-card header="文件类型预设" class="demo-card">
      <p>使用 c7-plus 文件类型常量：</p>
      <div class="preset-types">
        <div class="type-demo">
          <h4>图片文件</h4>
          <c7-upload
              v-model="presetImage"
              upload-url="/api/upload"
              file-type="jpg,jpeg,png,gif,bmp,webp"
              :file-size="5"
              tip-text="图片文件（预设类型）"
          />
          <p class="type-info">支持：{{ imageTypes.join(', ') }}</p>
        </div>

        <div class="type-demo">
          <h4>视频文件</h4>
          <c7-upload
              v-model="presetVideo"
              upload-url="/api/upload"
              file-type="mp4,avi,mov,wmv,flv,webm"
              :file-size="100"
              tip-text="视频文件（预设类型）"
          />
          <p class="type-info">支持：{{ videoTypes.join(', ') }}</p>
        </div>
      </div>
    </el-card>

    <!-- 性能优化展示 -->
    <el-card header="性能优化" class="demo-card">
      <p>计算属性缓存和状态管理优化：</p>
      <div class="performance-stats">
        <el-statistic title="文件验证次数" :value="validationCount"/>
        <el-statistic title="错误处理次数" :value="errorCount"/>
        <el-statistic title="成功上传次数" :value="successCount"/>
      </div>

      <c7-upload
          v-model="performanceFiles"
          upload-url="/api/upload"
          file-type="jpg,png,pdf,doc,docx"
          :file-size="20"
          :limit="10"
          multiple
          @success="handleSuccess"
          @error="handleError"
      />

      <p class="performance-tip">
        💡 组件使用计算属性缓存文件类型处理，避免重复验证计算
      </p>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import {ref, computed} from 'vue'
import {c7Upload} from 'c7-plus'

// 本地定义文件类型常量（避免导入问题）
const FILE_TYPES = {
  IMAGE: {
    extensions: ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp'],
    description: '图片文件'
  },
  DOCUMENT: {
    extensions: ['pdf', 'doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'txt'],
    description: '文档文件'
  },
  VIDEO: {
    extensions: ['mp4', 'avi', 'mov', 'wmv', 'flv', 'webm'],
    description: '视频文件'
  },
  AUDIO: {
    extensions: ['mp3', 'wav', 'flac', 'aac', 'ogg'],
    description: '音频文件'
  },
  ARCHIVE: {
    extensions: ['zip', 'rar', '7z', 'tar', 'gz'],
    description: '压缩文件'
  }
}

// 文件数据
const imageFiles = ref('')
const documentFiles = ref('')
const restrictedFiles = ref('')
const presetImage = ref('')
const presetVideo = ref('')
const performanceFiles = ref('')

// 错误信息记录
const errorMessages = ref<string[]>([])

// 性能统计
const validationCount = ref(0)
const errorCount = ref(0)
const successCount = ref(0)

// 文件类型预设
const imageTypes = computed(() => FILE_TYPES.IMAGE.extensions)
const videoTypes = computed(() => FILE_TYPES.VIDEO.extensions)

// 错误处理
const handleUploadError = (error: any, file: File) => {
  errorCount.value++
  const errorMsg = `${new Date().toLocaleTimeString()} - 文件 "${file.name}": ${error.message || '上传失败'}`
  errorMessages.value.unshift(errorMsg)

  // 限制错误记录数量
  if (errorMessages.value.length > 10) {
    errorMessages.value = errorMessages.value.slice(0, 10)
  }
}

// 成功处理
const handleSuccess = (response: any, file: File) => {
  successCount.value++
  console.log('上传成功:', file.name, response)
}

// 通用错误处理
const handleError = (error: any) => {
  errorCount.value++
  console.error('上传错误:', error)
}

// 清除错误记录
const clearErrors = () => {
  errorMessages.value = []
}

// 模拟验证计数（实际项目中这个会由组件内部管理）
const incrementValidation = () => {
  validationCount.value++
}
</script>

<style scoped>
.upload-demo {
  padding: 20px;
}

.demo-card {
  margin-bottom: 20px;
}

.upload-group {
  margin: 15px 0;
}

.validation-info {
  color: #67c23a;
  font-size: 14px;
  margin-top: 10px;
  padding: 8px;
  background-color: #f0f9ff;
  border-left: 3px solid #67c23a;
}

.custom-tip {
  color: #409eff;
  font-size: 14px;
  line-height: 1.5;
}

.error-display {
  margin-top: 15px;
  padding: 15px;
  background-color: #fef0f0;
  border: 1px solid #fbc4c4;
  border-radius: 4px;
}

.error-item {
  color: #f56c6c;
  font-size: 13px;
  margin-bottom: 5px;
  list-style: none;
}

.preset-types {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
  margin-top: 15px;
}

.type-demo h4 {
  margin-bottom: 10px;
  color: #303133;
}

.type-info {
  font-size: 12px;
  color: #909399;
  margin-top: 5px;
}

.performance-stats {
  display: flex;
  gap: 30px;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 4px;
}

.performance-tip {
  color: #67c23a;
  font-size: 14px;
  margin-top: 15px;
  padding: 8px;
  background-color: #f0f9ff;
  border-left: 3px solid #67c23a;
}

@media (max-width: 768px) {
  .preset-types {
    grid-template-columns: 1fr;
  }

  .performance-stats {
    flex-direction: column;
    gap: 15px;
  }
}
</style>
