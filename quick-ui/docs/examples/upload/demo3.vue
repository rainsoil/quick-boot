<template>
  <div>
    <h2>实际应用场景示例</h2>
    
    <div class="demo-section">
      <h3>用户头像上传</h3>
      <div class="avatar-upload">
        <c7-upload 
          upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
          v-model="avatar"
          :limit="1"
          file-type="jpg,png"
          :file-size="2"
          :headers="headers"
        />
        <div class="avatar-info">
          <p>支持 JPG、PNG 格式，大小不超过 2MB</p>
          <p>建议尺寸：200x200 像素</p>
        </div>
      </div>
      <div class="result">头像文件：{{ avatar }}</div>
    </div>

    <div class="demo-section">
      <h3>产品图片上传</h3>
      <c7-upload 
        upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
        v-model="productImages"
        :limit="6"
        file-type="jpg,png,gif"
        :file-size="5"
        :headers="headers"
      />
      <div class="result">产品图片：{{ productImages }}</div>
      <div class="tip">最多上传 6 张产品图片，支持 JPG、PNG、GIF 格式</div>
    </div>

    <div class="demo-section">
      <h3>合同文档上传</h3>
      <c7-upload 
        upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
        v-model="contractDocuments"
        :limit="5"
        file-type="pdf,doc,docx"
        :file-size="20"
        :headers="headers"
        list-type="text"
        delete-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/delete"
      />
      <div class="result">合同文档：{{ contractDocuments }}</div>
      <div class="tip">支持 PDF、DOC、DOCX 格式，每个文件不超过 20MB</div>
    </div>

    <div class="demo-section">
      <h3>简历文件上传</h3>
      <c7-upload 
        upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
        v-model="resumeFile"
        :limit="1"
        file-type="pdf,doc,docx"
        :file-size="10"
        :headers="headers"
        list-type="text"
      />
      <div class="result">简历文件：{{ resumeFile }}</div>
      <div class="tip">支持 PDF、DOC、DOCX 格式，大小不超过 10MB</div>
    </div>

    <div class="demo-section">
      <h3>视频素材上传</h3>
      <c7-upload 
        upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
        v-model="videoMaterials"
        :limit="3"
        file-type="mp4,avi,mov"
        :file-size="200"
        :headers="headers"
        list-type="text"
      />
      <div class="result">视频素材：{{ videoMaterials }}</div>
      <div class="tip">支持 MP4、AVI、MOV 格式，每个文件不超过 200MB</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { c7Upload } from 'c7-plus'

const headers = ref({
  'Authorization': 'Bearer xxxx'
})

// 用户头像
const avatar = ref('')

// 产品图片
const productImages = ref('')

// 合同文档
const contractDocuments = ref('')

// 简历文件
const resumeFile = ref('')

// 视频素材
const videoMaterials = ref('')
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

.avatar-upload {
  display: flex;
  align-items: flex-start;
  gap: 20px;
}

.avatar-info {
  flex: 1;
}

.avatar-info p {
  margin: 5px 0;
  color: #909399;
  font-size: 14px;
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