<template>
  <div class="file-preview">
    <!-- 根据封面类型选择展示形式 -->
    <template v-if="coverType === 'None'">
      <!-- 直接展示所有预览项 -->
      <template v-for="(url, index) in fileUrls" :key="index">
        <template v-if="displayType === 'image'">
          <el-image
              :src="url"
              :style="previewStyle"
              :preview-src-list="fileUrls"
              v-bind="$attrs"
          ></el-image>
        </template>
        <template v-else-if="displayType === 'video'">
          <el-image
              :src="defaultVideoImage"
              :style="previewStyle"
              @click="openVideoDialog(url)"
              v-bind="$attrs"
          ></el-image>
        </template>
        <template v-else-if="displayType === 'file'">
          <el-link
              type="primary"
              @click="openFile(url)"
              style="display: block; margin-bottom: 8px;"
          >
            {{ extractFileName(url) }}
          </el-link>
        </template>
      </template>
    </template>

    <template v-else-if="coverType === 'button'">
      <!-- 封面为按钮：显示按钮，点击弹出预览对话框 -->
      <el-button type="primary" @click="coverDialogVisible = true">
        {{ buttonText }}
      </el-button>
    </template>

    <template v-else-if="coverType === 'file'">
      <!-- 封面为文件：根据 displayType 选择封面图片 -->
      <template v-if="displayType === 'image'">
        <!-- 图片类型取第一个文件 -->
        <el-image
            preview-teleported
            close-on-press-escape
            :src="fileUrls[0]"
            :style="previewStyle"
            @click="coverDialogVisible = true"
            v-bind="$attrs"
        ></el-image>
      </template>
      <template v-else-if="displayType === 'video'">
        <el-image
            preview-teleported
            close-on-press-escape
            :src="defaultVideoImage"
            :style="previewStyle"
            @click="coverDialogVisible = true"
            v-bind="$attrs"
        ></el-image>
      </template>
      <template v-else-if="displayType === 'file'">
        <el-image
            preview-teleported
            close-on-press-escape
            :src="defaultFileImage"
            :style="previewStyle"
            @click="coverDialogVisible = true"
            v-bind="$attrs"
        ></el-image>
      </template>
    </template>

    <!-- 预览对话框：用于 button 和 file 封面类型 -->
    <el-dialog
        v-model="coverDialogVisible"
        width="80%"
        title="文件预览"
        :before-close="handleCoverDialogClose"
    >
      <div class="dialog-content">
        <template v-for="(url, index) in fileUrls" :key="index">
          <template v-if="displayType === 'image'">
            <el-image
                preview-teleported
                close-on-press-escape
                :src="url"
                :style="previewStyle"
                :preview-src-list="fileUrls"
                v-bind="$attrs"
            ></el-image>
          </template>
          <template v-else-if="displayType === 'video'">
            <el-image
                :src="defaultVideoImage"
                preview-teleported
                close-on-press-escape
                :style="previewStyle"
                @click="openVideoDialog(url)"
                v-bind="$attrs"
            ></el-image>
          </template>
          <template v-else-if="displayType === 'file'">
            <el-link
                type="primary"
                @click="openFile(url)"
                style="display: block; margin-bottom: 8px;"
            >
              {{ extractFileName(url) }}
            </el-link>
          </template>
        </template>
      </div>
      <template #footer>
        <el-button @click="coverDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>

    <!-- 视频播放对话框 -->
    <el-dialog
        append-to-body
        v-model="videoDialogVisible"
        width="80%"
        title="视频预览"
        :before-close="handleVideoDialogClose"
    >
      <video
          v-if="currentVideoUrl"
          :src="currentVideoUrl"
          controls
          autoplay
          style="width: 100%; height: 100%;"
      ></video>
      <template #footer>
        <el-button @click="videoDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, computed} from 'vue'

defineOptions({
  name: 'c7Preview'
})
const props = defineProps({
  // 以逗号分隔的文件 URL 列表
  urls: {
    type: String,
    required: true
  },
  // 预览区域宽度，可传数字或带单位的字符串
  width: {
    type: [String, Number],
    default: '200px'
  },
  // 预览区域高度
  height: {
    type: [String, Number],
    default: '200px'
  },
  // 文件显示类型：'image' | 'video' | 'file'
  displayType: {
    type: String,
    default: 'image'
  },
  // 封面类型：'None'（直接展示所有预览）、'button'（按钮封面）、'file'（文件封面）
  coverType: {
    type: String,
    default: 'None'
  },
  // 当封面类型为 button 时，按钮显示的文本
  buttonText: {
    type: String,
    default: '预览文件'
  },
  // 当 displayType 为 video 时，默认的视频缩略图
  defaultVideoImage: {
    type: String,
    default: 'https://via.placeholder.com/200?text=Video'
  },
  // 当 displayType 为 file 且封面类型为 file 时，默认的文件封面图
  defaultFileImage: {
    type: String,
    default: 'https://via.placeholder.com/200?text=File'
  }
})

// 将 urls 字符串按逗号分割为数组
const fileUrls = computed(() => {
      console.log(props.urls)
      return props.urls ? props.urls.split(',').map(url => url.trim()).filter(url => url) : []
    }
)

// 生成预览组件的样式对象
const previewStyle = computed(() => ({
  width: typeof props.width === 'number' ? props.width + 'px' : props.width,
  height: typeof props.height === 'number' ? props.height + 'px' : props.height,
  cursor: 'pointer'
}))

// 对话框状态：cover 对话框和视频对话框
const coverDialogVisible = ref(false)
const videoDialogVisible = ref(false)
const currentVideoUrl = ref('')

// 打开视频预览对话框
const openVideoDialog = (url) => {
  currentVideoUrl.value = url
  videoDialogVisible.value = true
}

// 视频对话框关闭前回调
const handleVideoDialogClose = (done) => {
  done()
}

// Cover 对话框关闭前回调
const handleCoverDialogClose = (done) => {
  done()
}

// 点击 file 类型链接时，在新窗口打开文件 URL
const openFile = (url) => {
  window.open(url, '_blank')
}

// 从 URL 中提取文件名
const extractFileName = (url) => {
  try {
    const segments = url.split('/')
    return segments[segments.length - 1] || url
  } catch (error) {
    return url
  }
}
</script>

<style scoped>
.file-preview {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}

.dialog-content {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}
</style>
