<template>
  <div>
    <h2>高级上传功能示例</h2>
    
    <div class="demo-section">
      <h3>带删除功能的上传</h3>
      <c7-upload 
        upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
        v-model="filesWithDelete"
        :limit="3"
        file-type="jpg,png"
        :file-size="5"
        :headers="headers"
        delete-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/delete"
      />
      <div class="result">上传的文件：{{ filesWithDelete }}</div>
    </div>

    <div class="demo-section">
      <h3>自定义请求头上传</h3>
      <c7-upload 
        upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
        v-model="filesWithHeaders"
        :limit="2"
        file-type="jpg,png,gif"
        :file-size="8"
        :headers="customHeaders"
      />
      <div class="result">上传的文件：{{ filesWithHeaders }}</div>
    </div>

    <div class="demo-section">
      <h3>严格文件类型限制</h3>
      <c7-upload 
        upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
        v-model="strictTypeFiles"
        :limit="1"
        file-type="pdf"
        :file-size="15"
        :headers="headers"
        list-type="text"
      />
      <div class="result">上传的文件：{{ strictTypeFiles }}</div>
      <div class="tip">只允许上传 PDF 文件，大小不超过 15MB</div>
    </div>

    <div class="demo-section">
      <h3>大文件上传</h3>
      <c7-upload 
        upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
        v-model="largeFiles"
        :limit="1"
        file-type="zip,rar,7z"
        :file-size="500"
        :headers="headers"
        list-type="text"
      />
      <div class="result">上传的文件：{{ largeFiles }}</div>
      <div class="tip">支持压缩文件上传，大小不超过 500MB</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { c7Upload } from 'c7-plus'

const headers = ref({
  'Authorization': 'Bearer xxxx'
})

const customHeaders = ref({
  'Authorization': 'Bearer custom-token',
  'X-Custom-Header': 'custom-value',
  'Content-Type': 'multipart/form-data'
})

// 带删除功能的上传
const filesWithDelete = ref('')

// 自定义请求头上传
const filesWithHeaders = ref('')

// 严格文件类型限制
const strictTypeFiles = ref('')

// 大文件上传
const largeFiles = ref('')
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

.tip {
  margin-top: 8px;
  padding: 6px 10px;
  background-color: #e1f3d8;
  border-radius: 4px;
  color: #67c23a;
  font-size: 12px;
}
</style> 