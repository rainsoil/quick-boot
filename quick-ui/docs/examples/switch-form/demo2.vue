<template>
  <div>
    <h2>高级切换功能示例</h2>
    
    <div class="demo-section">
      <h3>多级页面切换</h3>
      <div class="button-group">
        <el-button type="primary" @click="currentPage = 'dashboard'">仪表板</el-button>
        <el-button type="success" @click="currentPage = 'users'">用户管理</el-button>
        <el-button type="warning" @click="currentPage = 'settings'">系统设置</el-button>
      </div>
      
      <c7-switch-form
        :showIndexs="pages"
        v-model="currentPage"
        @update:modelValue="handlePageChange"
      >
        <template #dashboard>
          <div class="page-content">
            <h3>仪表板</h3>
            <p>这是仪表板页面，显示系统概览信息。</p>
            <el-row :gutter="20">
              <el-col :span="6">
                <el-card>
                  <template #header>总用户数</template>
                  <div class="stat-number">1,234</div>
                </el-card>
              </el-col>
              <el-col :span="6">
                <el-card>
                  <template #header>今日活跃</template>
                  <div class="stat-number">567</div>
                </el-card>
              </el-col>
            </el-row>
          </div>
        </template>

        <template #users>
          <div class="page-content">
            <h3>用户管理</h3>
            <p>这是用户管理页面，可以进行用户相关操作。</p>
            <el-table :data="userData" style="width: 100%">
              <el-table-column prop="name" label="姓名" />
              <el-table-column prop="email" label="邮箱" />
              <el-table-column prop="role" label="角色" />
              <el-table-column label="操作">
                <template #default="scope">
                  <el-button size="small" @click="viewDetail(scope.row)">查看详情</el-button>
                  <el-button size="small" type="primary" @click="editUser(scope.row)">编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </template>

        <template #userDetail>
          <div class="page-content">
            <h3>用户详情</h3>
            <p>这是用户详情页面，显示用户的详细信息。</p>
            <el-descriptions :column="2" border>
              <el-descriptions-item label="用户ID">1001</el-descriptions-item>
              <el-descriptions-item label="用户名">zhangsan</el-descriptions-item>
              <el-descriptions-item label="真实姓名">张三</el-descriptions-item>
              <el-descriptions-item label="邮箱">zhangsan@example.com</el-descriptions-item>
            </el-descriptions>
          </div>
        </template>

        <template #userEdit>
          <div class="page-content">
            <h3>编辑用户</h3>
            <p>这是用户编辑页面，可以修改用户信息。</p>
            <el-form :model="editForm" label-width="100px">
              <el-form-item label="用户名">
                <el-input v-model="editForm.username" />
              </el-form-item>
              <el-form-item label="真实姓名">
                <el-input v-model="editForm.realName" />
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="editForm.email" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary">保存</el-button>
                <el-button>取消</el-button>
              </el-form-item>
            </el-form>
          </div>
        </template>

        <template #settings>
          <div class="page-content">
            <h3>系统设置</h3>
            <p>这是系统设置页面，可以配置系统参数。</p>
            <el-form :model="settingsForm" label-width="120px">
              <el-form-item label="系统名称">
                <el-input v-model="settingsForm.systemName" />
              </el-form-item>
              <el-form-item label="维护模式">
                <el-switch v-model="settingsForm.maintenance" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary">保存设置</el-button>
              </el-form-item>
            </el-form>
          </div>
        </template>
      </c7-switch-form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { c7SwitchForm } from 'c7-plus'

// 页面切换
const currentPage = ref('dashboard')
const handlePageChange = (pageName) => {
  console.log('页面切换：', pageName)
}

const pages = ref([
  {
    title: '仪表板',
    name: 'dashboard'
  },
  {
    title: '用户管理',
    name: 'users'
  },
  {
    title: '用户详情',
    name: 'userDetail',
    header: true,
    closeIndex: 'users'
  },
  {
    title: '编辑用户',
    name: 'userEdit',
    header: true,
    closeIndex: 'users'
  },
  {
    title: '系统设置',
    name: 'settings'
  }
])

// 用户数据
const userData = ref([
  { name: '张三', email: 'zhangsan@example.com', role: '管理员' },
  { name: '李四', email: 'lisi@example.com', role: '普通用户' },
  { name: '王五', email: 'wangwu@example.com', role: '访客' }
])

// 编辑表单
const editForm = ref({
  username: 'zhangsan',
  realName: '张三',
  email: 'zhangsan@example.com'
})

// 设置表单
const settingsForm = ref({
  systemName: '示例系统',
  maintenance: false
})

// 查看详情
const viewDetail = (row) => {
  currentPage.value = 'userDetail'
  console.log('查看详情：', row)
}

// 编辑用户
const editUser = (row) => {
  currentPage.value = 'userEdit'
  console.log('编辑用户：', row)
}
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

.button-group {
  margin-bottom: 15px;
}

.button-group .el-button {
  margin-right: 10px;
}

.page-content {
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 4px;
  min-height: 200px;
}

.page-content h3 {
  margin-bottom: 15px;
  color: #333;
}

.page-content p {
  margin-bottom: 15px;
  color: #666;
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
  text-align: center;
}
</style> 