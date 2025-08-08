<template>
  <div class="layer-theme-demo">
    <h3>主题变体演示</h3>
    
    <div class="theme-buttons">
      <el-button 
        v-for="theme in themes" 
        :key="theme.value"
        :type="theme.type"
        @click="showLayer(theme.value)"
      >
        {{ theme.label }}
      </el-button>
    </div>
    
    <!-- 不同主题的弹层 -->
    <c7-layer
      v-if="currentLayer === 'default'"
      title="默认主题弹层"
      theme="default"
      @close="closeLayer"
      @back="handleBack"
    >
      <div class="layer-content">
        <h4>默认主题</h4>
        <p>这是默认主题的弹层，适用于大多数通用场景。</p>
        <ul>
          <li>简洁的设计风格</li>
          <li>中性的颜色搭配</li>
          <li>良好的可读性</li>
        </ul>
      </div>
      
      <template #footer>
        <el-button @click="closeLayer">关闭</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </template>
    </c7-layer>
    
    <c7-layer
      v-if="currentLayer === 'primary'"
      title="主要主题弹层"
      theme="primary"
      @close="closeLayer"
      @back="handleBack"
    >
      <div class="layer-content">
        <h4>主要主题</h4>
        <p>这是主要主题的弹层，用于重要的操作和信息展示。</p>
        <el-alert
          title="重要提示"
          type="info"
          description="这是一个重要的操作，请仔细阅读相关说明。"
          show-icon
          :closable="false"
        />
      </div>
      
      <template #footer>
        <el-button @click="closeLayer">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确认操作</el-button>
      </template>
    </c7-layer>
    
    <c7-layer
      v-if="currentLayer === 'success'"
      title="成功主题弹层"
      theme="success"
      @close="closeLayer"
      @back="handleBack"
    >
      <div class="layer-content">
        <h4>操作成功</h4>
        <div class="success-content">
          <el-icon size="48" color="#67c23a">
            <SuccessFilled />
          </el-icon>
          <p>恭喜！您的操作已经成功完成。</p>
          <div class="success-details">
            <p>✅ 数据已保存</p>
            <p>✅ 通知已发送</p>
            <p>✅ 日志已记录</p>
          </div>
        </div>
      </div>
      
      <template #footer>
        <el-button type="success" @click="closeLayer">太好了</el-button>
      </template>
    </c7-layer>
    
    <c7-layer
      v-if="currentLayer === 'warning'"
      title="警告主题弹层"
      theme="warning"
      @close="closeLayer"
      @back="handleBack"
    >
      <div class="layer-content">
        <h4>注意事项</h4>
        <el-alert
          title="警告"
          type="warning"
          description="此操作可能会影响系统性能，建议在业务低峰期执行。"
          show-icon
          :closable="false"
        />
        <div class="warning-list">
          <p><strong>请注意：</strong></p>
          <ul>
            <li>操作不可逆转</li>
            <li>可能需要较长时间</li>
            <li>建议提前备份数据</li>
          </ul>
        </div>
      </div>
      
      <template #footer>
        <el-button @click="closeLayer">取消</el-button>
        <el-button type="warning" @click="handleConfirm">我已了解，继续</el-button>
      </template>
    </c7-layer>
    
    <c7-layer
      v-if="currentLayer === 'danger'"
      title="危险主题弹层"
      theme="danger"
      @close="closeLayer"
      @back="handleBack"
    >
      <div class="layer-content">
        <h4>危险操作确认</h4>
        <el-alert
          title="危险操作"
          type="error"
          description="此操作将永久删除数据，无法恢复，请谨慎操作！"
          show-icon
          :closable="false"
        />
        <div class="danger-content">
          <p>您即将执行以下危险操作：</p>
          <ul>
            <li>🗑️ 删除所有用户数据</li>
            <li>🗑️ 清空系统日志</li>
            <li>🗑️ 重置系统配置</li>
          </ul>
          <el-input
            v-model="confirmText"
            placeholder="请输入 'DELETE' 确认删除"
            style="margin-top: 15px;"
          />
        </div>
      </div>
      
      <template #footer>
        <el-button @click="closeLayer">取消</el-button>
        <el-button 
          type="danger" 
          :disabled="confirmText !== 'DELETE'"
          @click="handleDangerousConfirm"
        >
          确认删除
        </el-button>
      </template>
    </c7-layer>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { c7Layer } from 'c7-plus'
import { SuccessFilled } from '@element-plus/icons-vue'

// 当前显示的弹层
const currentLayer = ref('')

// 确认文本
const confirmText = ref('')

// 主题配置
const themes = [
  { label: '默认主题', value: 'default', type: 'default' },
  { label: '主要主题', value: 'primary', type: 'primary' },
  { label: '成功主题', value: 'success', type: 'success' },
  { label: '警告主题', value: 'warning', type: 'warning' },
  { label: '危险主题', value: 'danger', type: 'danger' }
]

// 显示弹层
const showLayer = (theme) => {
  currentLayer.value = theme
  confirmText.value = ''
}

// 关闭弹层
const closeLayer = () => {
  currentLayer.value = ''
  confirmText.value = ''
}

// 返回处理
const handleBack = () => {
  console.log('点击了返回按钮')
  closeLayer()
}

// 确认处理
const handleConfirm = () => {
  ElMessage.success('操作确认成功')
  closeLayer()
}

// 危险操作确认
const handleDangerousConfirm = () => {
  ElMessage.error('危险操作已执行（演示）')
  closeLayer()
}
</script>

<style scoped>
.layer-theme-demo {
  padding: 20px;
}

.theme-buttons {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  margin-bottom: 20px;
}

.layer-content {
  padding: 20px 0;
}

.layer-content h4 {
  margin-top: 0;
  margin-bottom: 15px;
  color: #303133;
}

.success-content {
  text-align: center;
  padding: 20px;
}

.success-content .el-icon {
  margin-bottom: 15px;
}

.success-details {
  margin-top: 20px;
  text-align: left;
}

.success-details p {
  margin: 8px 0;
  color: #67c23a;
}

.warning-list {
  margin-top: 15px;
}

.warning-list ul {
  margin-left: 20px;
}

.danger-content {
  margin-top: 15px;
}

.danger-content ul {
  margin: 15px 0;
  padding-left: 20px;
}

.danger-content li {
  margin: 8px 0;
  color: #f56c6c;
}

@media (max-width: 768px) {
  .theme-buttons {
    flex-direction: column;
  }
  
  .layer-content {
    padding: 10px 0;
  }
}
</style>