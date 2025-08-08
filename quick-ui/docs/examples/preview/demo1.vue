<template>
  <div>
    <h2>图片预览功能示例</h2>
    
    <div class="demo-section">
      <h3>基础图片预览</h3>
      <c7-preview :urls="imageUrls" />
    </div>

    <div class="demo-section">
      <h3>自定义尺寸的图片预览</h3>
      <c7-preview :urls="imageUrls" width="150px" height="150px" />
    </div>

    <div class="demo-section">
      <h3>按钮触发预览</h3>
      <c7-preview 
        :urls="imageUrls" 
        cover-type="button" 
        button-text="查看图片"
        width="100px" 
        height="100px" 
      />
    </div>

    <div class="demo-section">
      <h3>文件封面预览</h3>
      <c7-preview 
        :urls="imageUrls" 
        cover-type="file"
        width="120px" 
        height="120px" 
      />
    </div>

    <div class="demo-section">
      <h3>直接展示所有图片</h3>
      <c7-preview 
        :urls="imageUrls" 
        cover-type="None"
        width="80px" 
        height="80px" 
      />
    </div>

    <div class="demo-section">
      <h3>单张图片预览</h3>
      <c7-preview 
        :urls="singleImageUrl" 
        width="200px" 
        height="200px" 
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { c7Preview } from 'c7-plus'

// 多张图片URL
const imageUrls = ref([
  'https://via.placeholder.com/400x300/409EFF/FFFFFF?text=图片1',
  'https://via.placeholder.com/400x300/67C23A/FFFFFF?text=图片2',
  'https://via.placeholder.com/400x300/E6A23C/FFFFFF?text=图片3',
  'https://via.placeholder.com/400x300/F56C6C/FFFFFF?text=图片4'
].join(','))

// 单张图片URL
const singleImageUrl = ref('https://via.placeholder.com/600x400/409EFF/FFFFFF?text=单张图片')
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