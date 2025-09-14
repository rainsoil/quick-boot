<template>
  <div class="layer-size-demo">
    <h3>尺寸变体演示</h3>
    
    <div class="size-buttons">
      <el-button 
        v-for="size in sizes" 
        :key="size.value"
        :size="size.value"
        @click="showLayer(size.value)"
      >
        {{ size.label }}
      </el-button>
    </div>
    
    <!-- 小尺寸弹层 -->
    <c7-layer
      v-if="currentLayer === 'small'"
      title="小尺寸弹层"
      size="small"
      theme="primary"
      @close="closeLayer"
      @back="handleBack"
    >
      <div class="layer-content">
        <h4>简洁内容</h4>
        <p>小尺寸弹层适合展示简单的信息和操作。</p>
        <el-tag size="small" type="info">紧凑布局</el-tag>
      </div>
      
      <template #footer>
        <el-button size="small" @click="closeLayer">关闭</el-button>
        <el-button size="small" type="primary" @click="handleConfirm">确定</el-button>
      </template>
    </c7-layer>
    
    <!-- 中等尺寸弹层 -->
    <c7-layer
      v-if="currentLayer === 'medium'"
      title="中等尺寸弹层"
      size="medium"
      theme="success"
      @close="closeLayer"
      @back="handleBack"
    >
      <div class="layer-content">
        <h4>标准内容</h4>
        <p>中等尺寸是默认尺寸，适合大多数常规内容展示。</p>
        
        <el-form :model="form" label-width="80px" size="default">
          <el-form-item label="用户名">
            <el-input v-model="form.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="form.email" placeholder="请输入邮箱" />
          </el-form-item>
          <el-form-item label="角色">
            <el-select v-model="form.role" placeholder="请选择角色">
              <el-option label="管理员" value="admin" />
              <el-option label="用户" value="user" />
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      
      <template #footer>
        <el-button @click="closeLayer">取消</el-button>
        <el-button type="success" @click="handleConfirm">保存</el-button>
      </template>
    </c7-layer>
    
    <!-- 大尺寸弹层 -->
    <c7-layer
      v-if="currentLayer === 'large'"
      title="大尺寸弹层"
      size="large"
      theme="warning"
      @close="closeLayer"
      @back="handleBack"
    >
      <div class="layer-content">
        <h4>复杂内容</h4>
        <p>大尺寸弹层适合展示复杂的表单、表格或详细信息。</p>
        
        <el-tabs v-model="activeTab" type="border-card">
          <el-tab-pane label="基本信息" name="basic">
            <el-descriptions title="用户详情" :column="2" border>
              <el-descriptions-item label="用户名">张三</el-descriptions-item>
              <el-descriptions-item label="邮箱">zhangsan@example.com</el-descriptions-item>
              <el-descriptions-item label="手机号">138****8888</el-descriptions-item>
              <el-descriptions-item label="创建时间">2023-01-01</el-descriptions-item>
              <el-descriptions-item label="状态">
                <el-tag type="success">正常</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="最后登录">2023-12-01 10:30:00</el-descriptions-item>
            </el-descriptions>
          </el-tab-pane>
          
          <el-tab-pane label="权限设置" name="permissions">
            <el-tree
              :data="treeData"
              show-checkbox
              node-key="id"
              :default-expanded-keys="[1, 2]"
              :default-checked-keys="[3, 4]"
              :props="{ children: 'children', label: 'label' }"
            />
          </el-tab-pane>
          
          <el-tab-pane label="操作日志" name="logs">
            <el-table :data="logData" style="width: 100%">
              <el-table-column prop="time" label="时间" width="150" />
              <el-table-column prop="action" label="操作" width="120" />
              <el-table-column prop="result" label="结果" width="80">
                <template #default="{ row }">
                  <el-tag :type="row.result === '成功' ? 'success' : 'danger'">
                    {{ row.result }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="ip" label="IP地址" />
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </div>
      
      <template #footer>
        <el-button @click="closeLayer">关闭</el-button>
        <el-button type="primary" @click="handleExport">导出数据</el-button>
        <el-button type="warning" @click="handleConfirm">保存修改</el-button>
      </template>
    </c7-layer>
    
    <!-- 尺寸对比说明 -->
    <el-card class="size-comparison">
      <template #header>
        <span>尺寸对比说明</span>
      </template>
      
      <el-table :data="sizeComparisonData" style="width: 100%">
        <el-table-column prop="size" label="尺寸" width="100" />
        <el-table-column prop="headerHeight" label="头部高度" width="100" />
        <el-table-column prop="contentPadding" label="内容边距" width="100" />
        <el-table-column prop="footerPadding" label="底部边距" width="100" />
        <el-table-column prop="scenario" label="适用场景" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
// 当前显示的弹层
const currentLayer = ref('')

// 活动标签页
const activeTab = ref('basic')

// 表单数据
const form = ref({
  username: '',
  email: '',
  role: ''
})

// 尺寸配置
const sizes = [
  { label: '小尺寸 (Small)', value: 'small' },
  { label: '中尺寸 (Medium)', value: 'medium' },
  { label: '大尺寸 (Large)', value: 'large' }
]

// 尺寸对比数据
const sizeComparisonData = [
  {
    size: 'Small',
    headerHeight: '45px',
    contentPadding: '16px',
    footerPadding: '12px',
    scenario: '简单信息、快速操作、移动端友好'
  },
  {
    size: 'Medium',
    headerHeight: '55px',
    contentPadding: '20px',
    footerPadding: '16px',
    scenario: '常规表单、标准内容、默认选择'
  },
  {
    size: 'Large',
    headerHeight: '65px',
    contentPadding: '24px',
    footerPadding: '20px',
    scenario: '复杂表单、详细信息、数据展示'
  }
]

// 权限树数据
const treeData = [
  {
    id: 1,
    label: '系统管理',
    children: [
      { id: 3, label: '用户管理' },
      { id: 4, label: '角色管理' },
      { id: 5, label: '权限管理' }
    ]
  },
  {
    id: 2,
    label: '业务管理',
    children: [
      { id: 6, label: '订单管理' },
      { id: 7, label: '商品管理' },
      { id: 8, label: '库存管理' }
    ]
  }
]

// 日志数据
const logData = [
  { time: '2023-12-01 10:30', action: '登录', result: '成功', ip: '192.168.1.100' },
  { time: '2023-12-01 10:25', action: '修改密码', result: '成功', ip: '192.168.1.100' },
  { time: '2023-11-30 16:20', action: '登录', result: '失败', ip: '192.168.1.101' },
  { time: '2023-11-30 16:15', action: '登录', result: '成功', ip: '192.168.1.100' }
]

// 显示弹层
const showLayer = (size) => {
  currentLayer.value = size
}

// 关闭弹层
const closeLayer = () => {
  currentLayer.value = ''
}

// 返回处理
const handleBack = () => {
  console.log('点击了返回按钮')
  closeLayer()
}

// 确认处理
const handleConfirm = () => {
  ElMessage.success(`${currentLayer.value} 尺寸操作确认成功`)
  closeLayer()
}

// 导出处理
const handleExport = () => {
  ElMessage.success('数据导出中...')
}
</script>

<style scoped>
.layer-size-demo {
  padding: 20px;
}

.size-buttons {
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
  margin-bottom: 30px;
}

.layer-content {
  padding: 20px 0;
}

.layer-content h4 {
  margin-top: 0;
  margin-bottom: 15px;
  color: #303133;
}

.size-comparison {
  margin-top: 30px;
}

.el-form {
  margin-top: 15px;
}

.el-tabs {
  margin-top: 15px;
}

.el-descriptions {
  margin-top: 10px;
}

.el-tree {
  margin-top: 10px;
}

@media (max-width: 768px) {
  .size-buttons {
    flex-direction: column;
  }
  
  .layer-content {
    padding: 10px 0;
  }
  
  .el-descriptions {
    --el-descriptions-item-bordered-label-background: #fafafa;
  }
}
</style>