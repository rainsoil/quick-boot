<template>
    <div>
      <c7-title label="对话框模式示例" label-size="h3"></c7-title>
      
      <div style="margin-bottom: 20px;">
        <el-button type="primary" @click="openDialog1">基础对话框</el-button>
        <el-button type="success" @click="openDialog2">带标题的对话框</el-button>
        <el-button type="warning" @click="openDialog3">自定义宽度的对话框</el-button>
      </div>
      
      <!-- 基础对话框 -->
      <c7-dialog 
        v-model:visible="dialog1Visible" 
        :footer="true"
        @submit="handleSubmit1"
        @close="handleClose1"
      >
        <p>这是一个基础的对话框，包含默认的确定和取消按钮。</p>
      </c7-dialog>
      
      <!-- 带标题的对话框 -->
      <c7-dialog 
        v-model:visible="dialog2Visible" 
        :footer="true"
        :modalProps="{ title: '用户信息', width: '500px' }"
        @submit="handleSubmit2"
        @close="handleClose2"
      >
        <div>
          <p>这是一个带标题的对话框。</p>
          <p>可以通过 modalProps 传递 el-dialog 的所有属性。</p>
        </div>
      </c7-dialog>
      
      <!-- 自定义宽度的对话框 -->
      <c7-dialog 
        v-model:visible="dialog3Visible" 
        :footer="false"
        :modalProps="{ title: '自定义宽度', width: '800px', center: true }"
        @close="handleClose3"
      >
        <div>
          <p>这是一个自定义宽度的对话框，居中显示。</p>
          <p>通过设置 footer 为 false 隐藏默认按钮。</p>
        </div>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialog3Visible = false">关闭</el-button>
            <el-button type="primary" @click="handleCustomSubmit">自定义提交</el-button>
          </div>
        </template>
      </c7-dialog>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  const dialog1Visible = ref(false)
  const dialog2Visible = ref(false)
  const dialog3Visible = ref(false)
  
  const openDialog1 = () => {
    dialog1Visible.value = true
  }
  
  const openDialog2 = () => {
    dialog2Visible.value = true
  }
  
  const openDialog3 = () => {
    dialog3Visible.value = true
  }
  
  const handleSubmit1 = () => {
    alert('基础对话框提交')
    dialog1Visible.value = false
  }
  
  const handleClose1 = () => {
    alert('基础对话框关闭')
  }
  
  const handleSubmit2 = () => {
    alert('带标题对话框提交')
    dialog2Visible.value = false
  }
  
  const handleClose2 = () => {
    alert('带标题对话框关闭')
  }
  
  const handleClose3 = () => {
    alert('自定义宽度对话框关闭')
  }
  
  const handleCustomSubmit = () => {
    alert('自定义提交')
    dialog3Visible.value = false
  }
  </script>
  
  <style scoped>
  .dialog-footer {
    text-align: right;
  }
  </style>