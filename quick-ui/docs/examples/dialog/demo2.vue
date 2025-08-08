<template>
    <div>
      <c7-title label="抽屉模式示例" label-size="h3"></c7-title>
      
      <div style="margin-bottom: 20px;">
        <el-button type="primary" @click="openDrawer1">右侧抽屉</el-button>
        <el-button type="success" @click="openDrawer2">左侧抽屉</el-button>
        <el-button type="warning" @click="openDrawer3">顶部抽屉</el-button>
        <el-button type="info" @click="openDrawer4">底部抽屉</el-button>
      </div>
      
      <!-- 右侧抽屉 -->
      <c7-dialog 
        v-model:visible="drawer1Visible" 
        mode="drawer"
        :modalProps="{ title: '右侧抽屉', direction: 'rtl', size: '30%' }"
        @close="handleDrawerClose1"
      >
        <div>
          <p>这是一个右侧抽屉，从右向左滑出。</p>
          <p>可以通过 direction 属性控制抽屉的方向。</p>
          <p>可以通过 size 属性控制抽屉的大小。</p>
        </div>
      </c7-dialog>
      
      <!-- 左侧抽屉 -->
      <c7-dialog 
        v-model:visible="drawer2Visible" 
        mode="drawer"
        :modalProps="{ title: '左侧抽屉', direction: 'ltr', size: '400px' }"
        @close="handleDrawerClose2"
      >
        <div>
          <p>这是一个左侧抽屉，从左向右滑出。</p>
          <p>size 可以设置为百分比或具体像素值。</p>
        </div>
      </c7-dialog>
      
      <!-- 顶部抽屉 -->
      <c7-dialog 
        v-model:visible="drawer3Visible" 
        mode="drawer"
        :modalProps="{ title: '顶部抽屉', direction: 'ttb', size: '50%' }"
        @close="handleDrawerClose3"
      >
        <div>
          <p>这是一个顶部抽屉，从上向下滑出。</p>
          <p>适合显示表单或设置面板。</p>
        </div>
      </c7-dialog>
      
      <!-- 底部抽屉 -->
      <c7-dialog 
        v-model:visible="drawer4Visible" 
        mode="drawer"
        :modalProps="{ title: '底部抽屉', direction: 'btt', size: '60%' }"
        @close="handleDrawerClose4"
      >
        <div>
          <p>这是一个底部抽屉，从下向上滑出。</p>
          <p>适合显示操作面板或确认信息。</p>
        </div>
      </c7-dialog>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue'
  import { c7Dialog, c7Title } from "c7-plus"
  
  const drawer1Visible = ref(false)
  const drawer2Visible = ref(false)
  const drawer3Visible = ref(false)
  const drawer4Visible = ref(false)
  
  const openDrawer1 = () => {
    drawer1Visible.value = true
  }
  
  const openDrawer2 = () => {
    drawer2Visible.value = true
  }
  
  const openDrawer3 = () => {
    drawer3Visible.value = true
  }
  
  const openDrawer4 = () => {
    drawer4Visible.value = true
  }
  
  const handleDrawerClose1 = () => {
    console.log('右侧抽屉关闭')
  }
  
  const handleDrawerClose2 = () => {
    console.log('左侧抽屉关闭')
  }
  
  const handleDrawerClose3 = () => {
    console.log('顶部抽屉关闭')
  }
  
  const handleDrawerClose4 = () => {
    console.log('底部抽屉关闭')
  }
  </script>