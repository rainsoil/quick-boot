<template>
  <div class="switch-form-performance-demo">
    <h3>性能优化与 TypeScript 支持演示</h3>
    
    <!-- 控制面板 -->
    <el-card header="控制面板" class="demo-card">
      <div class="control-panel">
        <div class="control-item">
          <label>当前视图：</label>
          <el-select v-model="currentView" placeholder="选择视图">
            <el-option 
              v-for="config in switchConfigs" 
              :key="config.name"
              :label="config.title" 
              :value="config.name"
            />
          </el-select>
        </div>
        
        <div class="control-item">
          <label>性能统计：</label>
          <div class="stats">
            <el-statistic title="配置查找次数" :value="lookupCount" />
            <el-statistic title="渲染次数" :value="renderCount" />
            <el-statistic title="缓存命中率" :value="cacheHitRate + '%'" />
          </div>
        </div>
        
        <div class="control-buttons">
          <el-button @click="resetStats">重置统计</el-button>
          <el-button @click="triggerRerender">触发重渲染</el-button>
          <el-button @click="switchToRandom">随机切换</el-button>
        </div>
      </div>
    </el-card>

    <!-- 优化后的切换组件 -->
    <el-card header="优化后的切换组件" class="demo-card">
      <c7-switch-form
        ref="switchFormRef"
        v-model="currentView"
        :show-indexs="switchConfigs"
        default-view="main"
        @back="handleBack"
        @change="handleChange"
      >
        <!-- 主页面 -->
        <template #main="{ config }">
          <div class="view-content">
            <h4>{{ config.title }}</h4>
            <p>这是主页面，展示了性能优化后的切换组件特性。</p>
            
            <div class="feature-list">
              <h5>优化特性：</h5>
              <ul>
                <li>✅ 使用计算属性缓存配置查找</li>
                <li>✅ 减少重复的数组遍历</li>
                <li>✅ 优化模板条件判断</li>
                <li>✅ TypeScript 类型安全</li>
              </ul>
            </div>
            
            <div class="action-buttons">
              <el-button type="primary" @click="switchTo('list')">
                进入列表页
              </el-button>
              <el-button type="success" @click="switchTo('form')">
                进入表单页
              </el-button>
            </div>
          </div>
        </template>

        <!-- 列表页面 -->
        <template #list="{ config }">
          <div class="view-content">
            <h4>{{ config.title }}</h4>
            <p>这是列表页面，展示数据列表功能。</p>
            
            <el-table :data="tableData" style="width: 100%">
              <el-table-column prop="id" label="ID" width="80" />
              <el-table-column prop="name" label="名称" />
              <el-table-column prop="status" label="状态">
                <template #default="{ row }">
                  <el-tag :type="row.status === '正常' ? 'success' : 'warning'">
                    {{ row.status }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作">
                <template #default="{ row }">
                  <el-button size="small" @click="viewDetail(row)">
                    查看详情
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            
            <div class="pagination">
              <el-pagination
                v-model:current-page="currentPage"
                :page-size="pageSize"
                :total="total"
                layout="total, prev, pager, next"
              />
            </div>
          </div>
        </template>

        <!-- 详情页面 -->
        <template #detail="{ config }">
          <div class="view-content">
            <h4>{{ config.title }}</h4>
            <p>这是详情页面，展示选中项的详细信息。</p>
            
            <el-descriptions title="详细信息" :column="2" border>
              <el-descriptions-item label="ID">{{ selectedItem.id }}</el-descriptions-item>
              <el-descriptions-item label="名称">{{ selectedItem.name }}</el-descriptions-item>
              <el-descriptions-item label="状态">
                <el-tag :type="selectedItem.status === '正常' ? 'success' : 'warning'">
                  {{ selectedItem.status }}
                </el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="创建时间">{{ selectedItem.createTime }}</el-descriptions-item>
              <el-descriptions-item label="描述" :span="2">
                {{ selectedItem.description }}
              </el-descriptions-item>
            </el-descriptions>
            
            <div class="detail-actions">
              <el-button @click="editItem">编辑</el-button>
              <el-button type="danger" @click="deleteItem">删除</el-button>
            </div>
          </div>
        </template>

        <!-- 表单页面 -->
        <template #form="{ config }">
          <div class="view-content">
            <h4>{{ config.title }}</h4>
            <p>这是表单页面，展示表单编辑功能。</p>
            
            <el-form :model="formData" label-width="100px">
              <el-form-item label="名称">
                <el-input v-model="formData.name" placeholder="请输入名称" />
              </el-form-item>
              <el-form-item label="状态">
                <el-select v-model="formData.status" placeholder="请选择状态">
                  <el-option label="正常" value="正常" />
                  <el-option label="异常" value="异常" />
                </el-select>
              </el-form-item>
              <el-form-item label="描述">
                <el-input 
                  v-model="formData.description" 
                  type="textarea" 
                  placeholder="请输入描述"
                  :rows="4"
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="saveForm">保存</el-button>
                <el-button @click="resetForm">重置</el-button>
              </el-form-item>
            </el-form>
          </div>
        </template>

        <!-- 设置页面 -->
        <template #settings="{ config }">
          <div class="view-content">
            <h4>{{ config.title }}</h4>
            <p>这是设置页面，展示系统配置选项。</p>
            
            <el-tabs v-model="activeTab">
              <el-tab-pane label="基本设置" name="basic">
                <el-form label-width="120px">
                  <el-form-item label="系统名称">
                    <el-input v-model="settings.systemName" />
                  </el-form-item>
                  <el-form-item label="默认语言">
                    <el-select v-model="settings.language">
                      <el-option label="中文" value="zh" />
                      <el-option label="English" value="en" />
                    </el-select>
                  </el-form-item>
                  <el-form-item label="主题">
                    <el-radio-group v-model="settings.theme">
                      <el-radio label="light">浅色</el-radio>
                      <el-radio label="dark">深色</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-form>
              </el-tab-pane>
              
              <el-tab-pane label="高级设置" name="advanced">
                <el-form label-width="120px">
                  <el-form-item label="缓存大小">
                    <el-slider v-model="settings.cacheSize" :max="100" />
                  </el-form-item>
                  <el-form-item label="自动保存">
                    <el-switch v-model="settings.autoSave" />
                  </el-form-item>
                  <el-form-item label="调试模式">
                    <el-switch v-model="settings.debug" />
                  </el-form-item>
                </el-form>
              </el-tab-pane>
            </el-tabs>
          </div>
        </template>

        <!-- 空状态 -->
        <template #empty>
          <div class="empty-state">
            <el-empty description="没有找到对应的视图配置" />
            <el-button type="primary" @click="switchTo('main')">
              返回主页
            </el-button>
          </div>
        </template>
      </c7-switch-form>
    </el-card>

    <!-- TypeScript 类型展示 -->
    <el-card header="TypeScript 类型定义" class="demo-card">
      <div class="type-display">
        <h4>SwitchConfig 接口定义：</h4>
        <pre><code>interface SwitchConfig {
  name: string                    // 页面名称（必需）
  title?: string                 // 页面标题
  header?: boolean               // 是否显示页面头部
  closeIndex?: string | number   // 返回时切换到哪个页面
  [key: string]: any            // 其他自定义属性
}</code></pre>

        <h4>当前配置：</h4>
        <pre><code>{{ JSON.stringify(switchConfigs, null, 2) }}</code></pre>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { c7SwitchForm } from 'c7-plus'

// 本地类型定义
interface SwitchConfig {
  name: string                    // 页面名称（必需）
  title?: string                 // 页面标题
  header?: boolean               // 是否显示页面头部
  closeIndex?: string | number   // 返回时切换到哪个页面
  [key: string]: any            // 其他自定义属性
}

// 组件引用
const switchFormRef = ref()

// 当前视图
const currentView = ref<string>('main')

// 性能统计
const lookupCount = ref(0)
const renderCount = ref(0)
const cacheHitRate = computed(() => {
  if (lookupCount.value === 0) return 100
  return Math.round((1 - lookupCount.value / renderCount.value) * 100)
})

// 活动标签页
const activeTab = ref('basic')

// 分页数据
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(50)

// 选中项
const selectedItem = ref({
  id: '',
  name: '',
  status: '',
  createTime: '',
  description: ''
})

// 表单数据
const formData = ref({
  name: '',
  status: '',
  description: ''
})

// 设置数据
const settings = ref({
  systemName: 'C7-Plus 系统',
  language: 'zh',
  theme: 'light',
  cacheSize: 50,
  autoSave: true,
  debug: false
})

// 表格数据
const tableData = ref([
  { id: '001', name: '项目A', status: '正常', createTime: '2023-12-01' },
  { id: '002', name: '项目B', status: '异常', createTime: '2023-12-02' },
  { id: '003', name: '项目C', status: '正常', createTime: '2023-12-03' },
  { id: '004', name: '项目D', status: '正常', createTime: '2023-12-04' }
])

// 类型安全的配置数组
const switchConfigs: SwitchConfig[] = [
  {
    name: 'main',
    title: '主页面',
    header: false
  },
  {
    name: 'list',
    title: '数据列表',
    header: true,
    closeIndex: 'main'
  },
  {
    name: 'detail',
    title: '详情页面',
    header: true,
    closeIndex: 'list'
  },
  {
    name: 'form',
    title: '表单页面',
    header: true,
    closeIndex: 'main'
  },
  {
    name: 'settings',
    title: '系统设置',
    header: true,
    closeIndex: 'main'
  }
]

// 切换到指定视图
const switchTo = (viewName: string) => {
  lookupCount.value++
  if (switchFormRef.value) {
    switchFormRef.value.switchTo(viewName)
  } else {
    currentView.value = viewName
  }
}

// 查看详情
const viewDetail = (row: any) => {
  selectedItem.value = {
    ...row,
    createTime: row.createTime,
    description: `这是${row.name}的详细描述信息，包含了项目的各种详细参数和配置信息。`
  }
  switchTo('detail')
}

// 编辑项目
const editItem = () => {
  formData.value = {
    name: selectedItem.value.name,
    status: selectedItem.value.status,
    description: selectedItem.value.description
  }
  switchTo('form')
}

// 删除项目
const deleteItem = () => {
  ElMessageBox.confirm('确定要删除这个项目吗？', '确认删除', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    ElMessage.success('删除成功')
    switchTo('list')
  })
}

// 保存表单
const saveForm = () => {
  ElMessage.success('保存成功')
  switchTo('main')
}

// 重置表单
const resetForm = () => {
  formData.value = {
    name: '',
    status: '',
    description: ''
  }
}

// 处理返回事件
const handleBack = (closeIndex: string | number, config: SwitchConfig) => {
  console.log('返回事件:', closeIndex, config)
  ElMessage.info(`返回到: ${closeIndex}`)
}

// 处理切换事件
const handleChange = (currentValue: string | number, config: SwitchConfig) => {
  console.log('切换事件:', currentValue, config)
  renderCount.value++
}

// 重置统计
const resetStats = () => {
  lookupCount.value = 0
  renderCount.value = 0
}

// 触发重渲染
const triggerRerender = () => {
  renderCount.value++
  // 强制触发更新
  currentView.value = currentView.value
}

// 随机切换
const switchToRandom = () => {
  const randomIndex = Math.floor(Math.random() * switchConfigs.length)
  const randomConfig = switchConfigs[randomIndex]
  switchTo(randomConfig.name)
}

onMounted(() => {
  console.log('Switch Form Performance Demo 已加载')
})
</script>

<style scoped>
.switch-form-performance-demo {
  padding: 20px;
}

.demo-card {
  margin-bottom: 20px;
}

.control-panel {
  display: grid;
  gap: 20px;
}

.control-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.control-item label {
  min-width: 100px;
  font-weight: 500;
}

.stats {
  display: flex;
  gap: 20px;
}

.control-buttons {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.view-content {
  padding: 20px;
  min-height: 300px;
}

.view-content h4 {
  margin-top: 0;
  margin-bottom: 15px;
  color: #303133;
}

.feature-list {
  margin: 20px 0;
  padding: 15px;
  background-color: #f0f9ff;
  border-radius: 4px;
}

.feature-list h5 {
  margin-top: 0;
  margin-bottom: 10px;
}

.feature-list ul {
  margin: 0;
  padding-left: 20px;
}

.feature-list li {
  margin: 5px 0;
  color: #67c23a;
}

.action-buttons {
  margin-top: 20px;
  display: flex;
  gap: 10px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.detail-actions {
  margin-top: 20px;
  display: flex;
  gap: 10px;
}

.empty-state {
  text-align: center;
  padding: 40px;
}

.type-display {
  font-family: monospace;
}

.type-display h4 {
  margin-top: 20px;
  margin-bottom: 10px;
  color: #303133;
}

.type-display pre {
  background-color: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
  overflow-x: auto;
  margin: 10px 0;
}

.type-display code {
  color: #e6a23c;
  font-size: 13px;
  line-height: 1.5;
}

@media (max-width: 768px) {
  .control-panel {
    grid-template-columns: 1fr;
  }
  
  .control-item {
    flex-direction: column;
    align-items: stretch;
  }
  
  .control-item label {
    min-width: auto;
  }
  
  .stats {
    flex-direction: column;
    gap: 10px;
  }
  
  .action-buttons,
  .detail-actions {
    flex-direction: column;
  }
}
</style>