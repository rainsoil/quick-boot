<template>
    <div>
      <c7-title label="事件处理示例" label-size="h3"></c7-title>
      
      <div style="margin-bottom: 20px;">
        <el-button type="primary" @click="openDialog1">基础事件处理</el-button>
        <el-button type="success" @click="openDialog2">表单验证</el-button>
        <el-button type="warning" @click="openDialog3">异步提交</el-button>
      </div>
      
      <!-- 基础事件处理 -->
      <c7-dialog 
        v-model:visible="dialog1Visible" 
        :footer="true"
        :modalProps="{ title: '基础事件处理', width: '500px' }"
        @submit="handleSubmit1"
        @close="handleClose1"
      >
        <div>
          <p>这个对话框演示基础的事件处理。</p>
          <p>点击确定按钮会触发 submit 事件。</p>
          <p>点击取消按钮或关闭按钮会触发 close 事件。</p>
        </div>
      </c7-dialog>
      
      <!-- 表单验证 -->
      <c7-dialog 
        v-model:visible="dialog2Visible" 
        :footer="true"
        :modalProps="{ title: '表单验证', width: '500px' }"
        @submit="handleSubmit2"
        @close="handleClose2"
      >
        <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
          </el-form-item>
        </el-form>
      </c7-dialog>
      
      <!-- 异步提交 -->
      <c7-dialog 
        v-model:visible="dialog3Visible" 
        :footer="true"
        :modalProps="{ title: '异步提交', width: '500px' }"
        @submit="handleSubmit3"
        @close="handleClose3"
      >
        <div>
          <p>这个对话框演示异步提交的处理。</p>
          <p>点击确定按钮会模拟一个异步操作。</p>
          <p>在异步操作完成前，按钮会显示加载状态。</p>
        </div>
      </c7-dialog>
    </div>
  </template>
  
  <script setup>
  import { ref, reactive } from 'vue'
  const dialog1Visible = ref(false)
  const dialog2Visible = ref(false)
  const dialog3Visible = ref(false)
  
  const formRef = ref()
  const form = reactive({
    username: '',
    email: ''
  })
  
  const rules = {
    username: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
    ],
    email: [
      { required: true, message: '请输入邮箱地址', trigger: 'blur' },
      { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
    ]
  }
  
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
    alert('基础事件处理 - 提交成功！')
    dialog1Visible.value = false
  }
  
  const handleClose1 = () => {
    console.log('基础事件处理 - 对话框关闭')
  }
  
  const handleSubmit2 = () => {
    formRef.value.validate((valid) => {
      if (valid) {
        alert('表单验证通过！\n用户名: ' + form.username + '\n邮箱: ' + form.email)
        dialog2Visible.value = false
        // 重置表单
        form.username = ''
        form.email = ''
      } else {
        alert('表单验证失败，请检查输入！')
        return false
      }
    })
  }
  
  const handleClose2 = () => {
    console.log('表单验证 - 对话框关闭')
    // 重置表单
    form.username = ''
    form.email = ''
  }
  
  const handleSubmit3 = () => {
    // 模拟异步操作
    const loading = ElLoading.service({
      lock: true,
      text: '正在提交...',
      background: 'rgba(0, 0, 0, 0.7)',
    })
    
    setTimeout(() => {
      loading.close()
      alert('异步提交成功！')
      dialog3Visible.value = false
    }, 2000)
  }
  
  const handleClose3 = () => {
    console.log('异步提交 - 对话框关闭')
  }
  </script>