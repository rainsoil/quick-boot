<template>
  <div>
    <h2>高级预览功能示例</h2>
    
    <div class="demo-section">
      <h3>混合文件类型预览</h3>
      <p>同时预览图片、视频和文件</p>
      <c7-preview 
        :urls="mixedUrls" 
        display-type="image"
        cover-type="button"
        button-text="预览所有文件"
        width="200px" 
        height="150px" 
      />
    </div>

    <div class="demo-section">
      <h3>大尺寸图片预览</h3>
      <c7-preview 
        :urls="largeImageUrls" 
        width="300px" 
        height="200px" 
      />
    </div>

    <div class="demo-section">
      <h3>小尺寸缩略图预览</h3>
      <c7-preview 
        :urls="thumbnailUrls" 
        width="60px" 
        height="60px" 
      />
    </div>

    <div class="demo-section">
      <h3>自定义视频缩略图</h3>
      <c7-preview 
        :urls="videoUrls" 
        display-type="video"
        default-video-image="https://via.placeholder.com/200x150/FF5722/FFFFFF?text=自定义视频封面"
        cover-type="file"
        width="200px" 
        height="150px" 
      />
    </div>

    <div class="demo-section">
      <h3>自定义文件封面图</h3>
      <c7-preview 
        :urls="documentUrls" 
        display-type="file"
        default-file-image="https://via.placeholder.com/200x150/2196F3/FFFFFF?text=PDF文档"
        cover-type="file"
        width="200px" 
        height="150px" 
      />
    </div>

    <div class="demo-section">
      <h3>多文件网格预览</h3>
      <c7-preview 
        :urls="gridUrls" 
        cover-type="None"
        width="80px" 
        height="80px" 
      />
    </div>

    <div class="demo-section">
      <h3>响应式预览</h3>
      <div class="responsive-container">
        <c7-preview 
          :urls="responsiveUrls" 
          width="100%" 
          height="200px" 
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
// 混合文件类型URL
const mixedUrls = ref([
  'https://via.placeholder.com/400x300/409EFF/FFFFFF?text=图片',
  'https://www.w3schools.com/html/mov_bbb.mp4',
  'https://www.w3.org/TR/PNG/iso_8859-1.txt'
].join(','))

// 大尺寸图片URL
const largeImageUrls = ref([
  'https://via.placeholder.com/800x600/409EFF/FFFFFF?text=大图片1',
  'https://via.placeholder.com/800x600/67C23A/FFFFFF?text=大图片2'
].join(','))

// 缩略图URL
const thumbnailUrls = ref([
  'https://via.placeholder.com/100x100/409EFF/FFFFFF?text=缩略图1',
  'https://via.placeholder.com/100x100/67C23A/FFFFFF?text=缩略图2',
  'https://via.placeholder.com/100x100/E6A23C/FFFFFF?text=缩略图3',
  'https://via.placeholder.com/100x100/F56C6C/FFFFFF?text=缩略图4'
].join(','))

// 视频URL
const videoUrls = ref([
  'https://www.w3schools.com/html/mov_bbb.mp4',
  'https://www.w3schools.com/html/mov_bbb.mp4'
].join(','))

// 文档URL
const documentUrls = ref([
  'https://www.w3.org/TR/PNG/iso_8859-1.txt',
  'https://www.w3.org/TR/PNG/iso_8859-1.txt'
].join(','))

// 网格预览URL
const gridUrls = ref([
  'https://via.placeholder.com/100x100/409EFF/FFFFFF?text=1',
  'https://via.placeholder.com/100x100/67C23A/FFFFFF?text=2',
  'https://via.placeholder.com/100x100/E6A23C/FFFFFF?text=3',
  'https://via.placeholder.com/100x100/F56C6C/FFFFFF?text=4',
  'https://via.placeholder.com/100x100/9C27B0/FFFFFF?text=5',
  'https://via.placeholder.com/100x100/FF9800/FFFFFF?text=6'
].join(','))

// 响应式预览URL
const responsiveUrls = ref([
  'https://via.placeholder.com/1200x800/409EFF/FFFFFF?text=响应式图片1',
  'https://via.placeholder.com/1200x800/67C23A/FFFFFF?text=响应式图片2'
].join(','))
</script>

<style scoped>
h2 {
  margin-bottom: 20px;
  color: #333;
}

.demo-section {
  margin-bottom: 30px;
  padding: 20px;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
}

.demo-section h3 {
  margin-bottom: 15px;
  color: #666;
  font-size: 16px;
}

.demo-section p {
  margin-bottom: 15px;
  color: #999;
  font-size: 14px;
}

.responsive-container {
  max-width: 600px;
  margin: 0 auto;
}
</style> 