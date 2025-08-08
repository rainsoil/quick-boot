<template>
  <div>
    <h2>基础上传功能示例</h2>
    
    <div class="demo-section">
      <h3>单文件上传</h3>
      <c7-upload 
        upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
        v-model="singleFile"
        :limit="1"
        file-type="jpg,png"
        :file-size="5"
        :headers="headers"
      />
      <div class="result">上传的文件：{{ singleFile }}</div>
    </div>

    <div class="demo-section">
      <h3>多文件上传</h3>
      <c7-upload 
        upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
        v-model="multipleFiles"
        :limit="5"
        file-type="jpg,png,gif"
        :file-size="10"
        :headers="headers"
      />
      <div class="result">上传的文件：{{ multipleFiles }}</div>
    </div>

    <div class="demo-section">
      <h3>文档上传</h3>
      <c7-upload 
        upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
        v-model="documents"
        :limit="3"
        file-type="pdf,doc,docx,txt"
        :file-size="20"
        :headers="headers"
        list-type="text"
      />
      <div class="result">上传的文档：{{ documents }}</div>
    </div>

    <div class="demo-section">
      <h3>视频上传</h3>
      <c7-upload 
        upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
        v-model="videos"
        :limit="2"
        file-type="mp4,avi,mov"
        :file-size="100"
        :headers="headers"
        list-type="text"
      />
      <div class="result">上传的视频：{{ videos }}</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { c7Upload } from 'c7-plus'

const headers = ref({
  'Authorization': 'Bearer xxxx'
})

// 单文件上传
const singleFile = ref('')

// 多文件上传
const multipleFiles = ref('')

// 文档上传
const documents = ref('')

// 视频上传
const videos = ref('')
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

.result {
  margin-top: 10px;
  padding: 8px;
  background-color: #f5f7fa;
  border-radius: 4px;
  color: #606266;
  font-size: 14px;
  word-break: break-all;
}
</style> 