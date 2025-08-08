<template>
    <div>
      <c7-title label="配置选项示例" label-size="h3"></c7-title>
      
      <div style="margin-bottom: 20px;">
        <el-button type="primary" @click="openDialog1">无底部按钮</el-button>
        <el-button type="success" @click="openDialog2">自定义底部按钮</el-button>
        <el-button type="warning" @click="openDialog3">不可点击遮罩关闭</el-button>
        <el-button type="info" @click="openDialog4">全屏对话框</el-button>
      </div>
      
      <!-- 无底部按钮 -->
      <c7-dialog 
        v-model:visible="dialog1Visible" 
        :footer="false"
        :modalProps="{ title: '无底部按钮', width: '400px' }"
        @close="handleClose1"
      >
        <div>
          <p>这个对话框没有底部按钮区域。</p>
          <p>通过设置 footer 为 false 来隐藏底部按钮。</p>
        </div>
      </c7-dialog>
      
      <!-- 自定义底部按钮 -->
      <c7-dialog 
        v-model:visible="dialog2Visible" 
        :footer="true"
        :modalProps="{ title: '自定义底部按钮', width: '500px' }"
        @submit="handleSubmit2"
        @close="handleClose2"
      >
        <div>
          <p>这个对话框使用自定义的底部按钮。</p>
          <p>通过 footer 插槽来自定义底部按钮内容。</p>
        </div>
        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialog2Visible = false">取消</el-button>
            <el-button type="danger" @click="handleDelete">删除</el-button>
            <el-button type="primary" @click="handleSubmit2">确定</el-button>
          </div>
        </template>
      </c7-dialog>
      
      <!-- 不可点击遮罩关闭 -->
      <c7-dialog 
        v-model:visible="dialog3Visible" 
        :footer="true"
        :modalProps="{ 
          title: '不可点击遮罩关闭', 
          width: '450px',
          'close-on-click-modal': false 
        }"
        @submit="handleSubmit3"
        @close="handleClose3"
      >
        <div>
          <p>这个对话框不能通过点击遮罩来关闭。</p>
          <p>只能通过点击关闭按钮或确定按钮来关闭。</p>
        </div>
      </c7-dialog>
      
      <!-- 全屏对话框 -->
      <c7-dialog 
        v-model:visible="dialog4Visible" 
        :footer="true"
        :modalProps="{ 
          title: '全屏对话框', 
          width: '90%',
          top: '5vh',
          center: true
        }"
        @submit="handleSubmit4"
        @close="handleClose4"
      >
        <div>
          <p>这是一个全屏对话框，占据屏幕的90%宽度。</p>
          <p>通过设置 width 为百分比和 top 来控制对话框的大小和位置。</p>
          <div style="height: 400px; background: #f5f5f5; padding: 20px; margin: 20px 0;">
            <p>这里可以放置大量的内容...</p>
            <p>适合显示复杂的数据表格或表单。</p>
          </div>
        </div>
      </c7-dialog>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { c7Dialog, c7Title } from "c7-plus"
  
  const dialog1Visible = ref(false)
  const dialog2Visible = ref(false)
  const dialog3Visible = ref(false)
  const dialog4Visible = ref(false)
  
  const openDialog1 = () => {
    dialog1Visible.value = true
  }
  
  const openDialog2 = () => {
    dialog2Visible.value = true
  }
  
  const openDialog3 = () => {
    dialog3Visible.value = true
  }
  
  const openDialog4 = () => {
    dialog4Visible.value = true
  }
  
  const handleClose1 = () => {
    console.log('无底部按钮对话框关闭')
  }
  
  const handleSubmit2 = () => {
    alert('自定义底部按钮对话框提交')
    dialog2Visible.value = false
  }
  
  const handleClose2 = () => {
    console.log('自定义底部按钮对话框关闭')
  }
  
  const handleDelete = () => {
    alert('删除操作')
    dialog2Visible.value = false
  }
  
  const handleSubmit3 = () => {
    alert('不可点击遮罩关闭对话框提交')
    dialog3Visible.value = false
  }
  
  const handleClose3 = () => {
    console.log('不可点击遮罩关闭对话框关闭')
  }
  
  const handleSubmit4 = () => {
    alert('全屏对话框提交')
    dialog4Visible.value = false
  }
  
  const handleClose4 = () => {
    console.log('全屏对话框关闭')
  }
  </script>
  
  <style scoped>
  .dialog-footer {
    text-align: right;
  }
  </style>