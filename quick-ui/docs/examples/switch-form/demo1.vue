<template>
  <div>
    <h2>基础切换功能示例</h2>
    
    <div class="demo-section">
      <h3>简单页面切换</h3>
      <div class="button-group">
        <el-button type="primary" @click="currentPage = 'home'">首页</el-button>
        <el-button type="success" @click="currentPage = 'about'">关于</el-button>
        <el-button type="warning" @click="currentPage = 'contact'">联系</el-button>
      </div>
      
      <c7-switch-form
        :showIndexs="simplePages"
        v-model="currentPage"
        @update:modelValue="handlePageChange"
      >
        <template #home>
          <div class="page-content">
            <h3>首页内容</h3>
            <p>这是首页的内容区域，可以包含各种组件和功能。</p>
            <el-card>
              <template #header>
                <span>欢迎信息</span>
              </template>
              <p>欢迎使用我们的应用程序！</p>
            </el-card>
          </div>
        </template>

        <template #about>
          <div class="page-content">
            <h3>关于我们</h3>
            <p>这是关于页面的内容，介绍我们的团队和项目。</p>
            <el-descriptions :column="2" border>
              <el-descriptions-item label="公司名称">示例公司</el-descriptions-item>
              <el-descriptions-item label="成立时间">2024年</el-descriptions-item>
              <el-descriptions-item label="员工数量">100+</el-descriptions-item>
              <el-descriptions-item label="主营业务">软件开发</el-descriptions-item>
            </el-descriptions>
          </div>
        </template>

        <template #contact>
          <div class="page-content">
            <h3>联系我们</h3>
            <p>这是联系页面的内容，包含联系方式和表单。</p>
            <el-form :model="contactForm" label-width="80px">
              <el-form-item label="姓名">
                <el-input v-model="contactForm.name" placeholder="请输入姓名" />
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="contactForm.email" placeholder="请输入邮箱" />
              </el-form-item>
              <el-form-item label="留言">
                <el-input v-model="contactForm.message" type="textarea" placeholder="请输入留言" />
              </el-form-item>
              <el-form-item>
                <el-button type="primary">提交</el-button>
              </el-form-item>
            </el-form>
          </div>
        </template>
      </c7-switch-form>
    </div>

    <div class="demo-section">
      <h3>带返回按钮的页面切换</h3>
      <div class="button-group">
        <el-button type="primary" @click="currentPageWithHeader = 'main'">主页面</el-button>
        <el-button type="success" @click="currentPageWithHeader = 'detail'">详情页</el-button>
        <el-button type="warning" @click="currentPageWithHeader = 'edit'">编辑页</el-button>
      </div>
      
      <c7-switch-form
        :showIndexs="pagesWithHeader"
        v-model="currentPageWithHeader"
        @update:modelValue="handlePageWithHeaderChange"
      >
        <template #main>
          <div class="page-content">
            <h3>主页面</h3>
            <p>这是主页面，可以查看列表和进行基本操作。</p>
            <el-table :data="tableData" style="width: 100%">
              <el-table-column prop="name" label="姓名" />
              <el-table-column prop="age" label="年龄" />
              <el-table-column prop="email" label="邮箱" />
              <el-table-column label="操作">
                <template #default="scope">
                  <el-button size="small" @click="viewDetail(scope.row)">查看详情</el-button>
                  <el-button size="small" type="primary" @click="editItem(scope.row)">编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </template>

        <template #detail>
          <div class="page-content">
            <h3>详情页面</h3>
            <p>这是详情页面，显示选中项目的详细信息。</p>
            <el-descriptions :column="1" border>
              <el-descriptions-item label="姓名">张三</el-descriptions-item>
              <el-descriptions-item label="年龄">25</el-descriptions-item>
              <el-descriptions-item label="邮箱">zhangsan@example.com</el-descriptions-item>
              <el-descriptions-item label="地址">北京市朝阳区</el-descriptions-item>
            </el-descriptions>
          </div>
        </template>

        <template #edit>
          <div class="page-content">
            <h3>编辑页面</h3>
            <p>这是编辑页面，可以修改项目信息。</p>
            <el-form :model="editForm" label-width="80px">
              <el-form-item label="姓名">
                <el-input v-model="editForm.name" />
              </el-form-item>
              <el-form-item label="年龄">
                <el-input-number v-model="editForm.age" :min="1" :max="120" />
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
      </c7-switch-form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { c7SwitchForm } from 'c7-plus'

// 简单页面切换
const currentPage = ref('home')
const handlePageChange = (pageName) => {
  console.log('页面切换：', pageName)
}

const simplePages = ref([
  {
    title: '首页',
    name: 'home'
  },
  {
    title: '关于我们',
    name: 'about'
  },
  {
    title: '联系我们',
    name: 'contact'
  }
])

// 带返回按钮的页面切换
const currentPageWithHeader = ref('main')
const handlePageWithHeaderChange = (pageName) => {
  console.log('带头部页面切换：', pageName)
}

const pagesWithHeader = ref([
  {
    title: '主页面',
    name: 'main'
  },
  {
    title: '详情页面',
    name: 'detail',
    header: true,
    closeIndex: 'main'
  },
  {
    title: '编辑页面',
    name: 'edit',
    header: true,
    closeIndex: 'main'
  }
])

// 表格数据
const tableData = ref([
  { name: '张三', age: 25, email: 'zhangsan@example.com' },
  { name: '李四', age: 30, email: 'lisi@example.com' },
  { name: '王五', age: 28, email: 'wangwu@example.com' }
])

// 联系表单
const contactForm = ref({
  name: '',
  email: '',
  message: ''
})

// 编辑表单
const editForm = ref({
  name: '张三',
  age: 25,
  email: 'zhangsan@example.com'
})

// 查看详情
const viewDetail = (row) => {
  currentPageWithHeader.value = 'detail'
  console.log('查看详情：', row)
}

// 编辑项目
const editItem = (row) => {
  currentPageWithHeader.value = 'edit'
  console.log('编辑项目：', row)
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
</style> 