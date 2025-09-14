<template>
  <div>
    <h2>视频预览功能示例</h2>
    
    <div class="demo-section">
      <h3>基础视频预览</h3>
      <c7-preview 
        :urls="videoUrls" 
        display-type="video"
        width="200px" 
        height="150px" 
      />
    </div>

    <div class="demo-section">
      <h3>自定义视频缩略图</h3>
      <c7-preview 
        :urls="videoUrls" 
        display-type="video"
        default-video-image="https://via.placeholder.com/200x150/FF6B6B/FFFFFF?text=视频缩略图"
        width="200px" 
        height="150px" 
      />
    </div>

    <div class="demo-section">
      <h3>按钮触发视频预览</h3>
      <c7-preview 
        :urls="videoUrls" 
        display-type="video"
        cover-type="button"
        button-text="播放视频"
        width="150px" 
        height="100px" 
      />
    </div>

    <div class="demo-section">
      <h3>文件封面视频预览</h3>
      <c7-preview 
        :urls="videoUrls" 
        display-type="video"
        cover-type="file"
        width="180px" 
        height="120px" 
      />
    </div>

    <div class="demo-section">
      <h3>多个视频预览</h3>
      <c7-preview 
        :urls="multipleVideoUrls" 
        display-type="video"
        cover-type="None"
        width="120px" 
        height="90px" 
      />
    </div>

    <div class="demo-section">
      <h3>单视频预览</h3>
      <c7-preview 
        :urls="singleVideoUrl" 
        display-type="video"
        width="250px" 
        height="180px" 
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
// 单个视频URL
const singleVideoUrl = ref('https://www.w3schools.com/html/mov_bbb.mp4')

// 多个视频URL
const videoUrls = ref([
  'https://www.w3schools.com/html/mov_bbb.mp4',
  'https://www.w3schools.com/html/mov_bbb.mp4'
].join(','))

// 更多视频URL
const multipleVideoUrls = ref([
  'https://www.w3schools.com/html/mov_bbb.mp4',
  'https://www.w3schools.com/html/mov_bbb.mp4',
  'https://www.w3schools.com/html/mov_bbb.mp4',
  'https://www.w3schools.com/html/mov_bbb.mp4'
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
</style> 