<template>
  <div>
    <h2>文件预览功能示例</h2>
    
    <div class="demo-section">
      <h3>基础文件预览</h3>
      <c7-preview 
        :urls="fileUrls" 
        display-type="file"
        width="200px" 
        height="150px" 
      />
    </div>

    <div class="demo-section">
      <h3>自定义文件封面图</h3>
      <c7-preview 
        :urls="fileUrls" 
        display-type="file"
        cover-type="file"
        default-file-image="https://via.placeholder.com/200x150/9C27B0/FFFFFF?text=文档"
        width="200px" 
        height="150px" 
      />
    </div>

    <div class="demo-section">
      <h3>按钮触发文件预览</h3>
      <c7-preview 
        :urls="fileUrls" 
        display-type="file"
        cover-type="button"
        button-text="查看文件"
        width="150px" 
        height="100px" 
      />
    </div>

    <div class="demo-section">
      <h3>多个文件预览</h3>
      <c7-preview 
        :urls="multipleFileUrls" 
        display-type="file"
        cover-type="None"
        width="120px" 
        height="90px" 
      />
    </div>

    <div class="demo-section">
      <h3>单文件预览</h3>
      <c7-preview 
        :urls="singleFileUrl" 
        display-type="file"
        width="250px" 
        height="180px" 
      />
    </div>

    <div class="demo-section">
      <h3>混合文件类型预览</h3>
      <c7-preview 
        :urls="mixedFileUrls" 
        display-type="file"
        cover-type="file"
        width="180px" 
        height="120px" 
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
// 单个文件URL
const singleFileUrl = ref('https://www.w3.org/TR/PNG/iso_8859-1.txt')

// 多个文件URL
const fileUrls = ref([
  'https://www.w3.org/TR/PNG/iso_8859-1.txt',
  'https://www.w3.org/TR/PNG/iso_8859-1.txt'
].join(','))

// 更多文件URL
const multipleFileUrls = ref([
  'https://www.w3.org/TR/PNG/iso_8859-1.txt',
  'https://www.w3.org/TR/PNG/iso_8859-1.txt',
  'https://www.w3.org/TR/PNG/iso_8859-1.txt',
  'https://www.w3.org/TR/PNG/iso_8859-1.txt'
].join(','))

// 混合文件类型URL
const mixedFileUrls = ref([
  'https://www.w3.org/TR/PNG/iso_8859-1.txt',
  'https://www.w3.org/TR/PNG/iso_8859-1.txt',
  'https://www.w3.org/TR/PNG/iso_8859-1.txt'
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