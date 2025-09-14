<template>
  <div>
    <h2>实际应用场景示例</h2>
    
    <div class="demo-section">
      <h3>电商管理系统</h3>
      <div class="button-group">
        <el-button type="primary" @click="currentPage = 'products'">商品管理</el-button>
        <el-button type="success" @click="currentPage = 'orders'">订单管理</el-button>
        <el-button type="warning" @click="currentPage = 'customers'">客户管理</el-button>
      </div>
      
      <c7-switch-form
        :showIndexs="pages"
        v-model="currentPage"
        @update:modelValue="handlePageChange"
      >
        <template #products>
          <div class="page-content">
            <h3>商品管理</h3>
            <p>管理商品信息，包括添加、编辑、删除商品。</p>
            <el-table :data="productData" style="width: 100%">
              <el-table-column prop="name" label="商品名称" />
              <el-table-column prop="price" label="价格" />
              <el-table-column prop="stock" label="库存" />
              <el-table-column label="操作">
                <template #default="scope">
                  <el-button size="small" @click="viewProduct(scope.row)">查看</el-button>
                  <el-button size="small" type="primary" @click="editProduct(scope.row)">编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </template>

        <template #productDetail>
          <div class="page-content">
            <h3>商品详情</h3>
            <p>显示商品的详细信息。</p>
            <el-descriptions :column="2" border>
              <el-descriptions-item label="商品名称">iPhone 15</el-descriptions-item>
              <el-descriptions-item label="价格">¥5999</el-descriptions-item>
              <el-descriptions-item label="库存">100</el-descriptions-item>
              <el-descriptions-item label="状态">上架</el-descriptions-item>
            </el-descriptions>
          </div>
        </template>

        <template #orders>
          <div class="page-content">
            <h3>订单管理</h3>
            <p>查看和管理订单信息。</p>
            <el-table :data="orderData" style="width: 100%">
              <el-table-column prop="orderNo" label="订单号" />
              <el-table-column prop="customer" label="客户" />
              <el-table-column prop="amount" label="金额" />
              <el-table-column label="操作">
                <template #default="scope">
                  <el-button size="small" @click="viewOrder(scope.row)">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </template>

        <template #orderDetail>
          <div class="page-content">
            <h3>订单详情</h3>
            <p>显示订单的详细信息。</p>
            <el-descriptions :column="2" border>
              <el-descriptions-item label="订单号">O202401001</el-descriptions-item>
              <el-descriptions-item label="客户">张三</el-descriptions-item>
              <el-descriptions-item label="金额">¥5999</el-descriptions-item>
              <el-descriptions-item label="状态">已发货</el-descriptions-item>
            </el-descriptions>
          </div>
        </template>

        <template #customers>
          <div class="page-content">
            <h3>客户管理</h3>
            <p>管理客户信息。</p>
            <el-table :data="customerData" style="width: 100%">
              <el-table-column prop="name" label="姓名" />
              <el-table-column prop="phone" label="电话" />
              <el-table-column prop="email" label="邮箱" />
              <el-table-column label="操作">
                <template #default="scope">
                  <el-button size="small" @click="viewCustomer(scope.row)">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </template>

        <template #customerDetail>
          <div class="page-content">
            <h3>客户详情</h3>
            <p>显示客户的详细信息。</p>
            <el-descriptions :column="2" border>
              <el-descriptions-item label="姓名">张三</el-descriptions-item>
              <el-descriptions-item label="电话">13800138000</el-descriptions-item>
              <el-descriptions-item label="邮箱">zhangsan@example.com</el-descriptions-item>
              <el-descriptions-item label="等级">VIP</el-descriptions-item>
            </el-descriptions>
          </div>
        </template>
      </c7-switch-form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
// 页面切换
const currentPage = ref('products')
const handlePageChange = (pageName) => {
  console.log('页面切换：', pageName)
}

const pages = ref([
  {
    title: '商品管理',
    name: 'products'
  },
  {
    title: '商品详情',
    name: 'productDetail',
    header: true,
    closeIndex: 'products'
  },
  {
    title: '订单管理',
    name: 'orders'
  },
  {
    title: '订单详情',
    name: 'orderDetail',
    header: true,
    closeIndex: 'orders'
  },
  {
    title: '客户管理',
    name: 'customers'
  },
  {
    title: '客户详情',
    name: 'customerDetail',
    header: true,
    closeIndex: 'customers'
  }
])

// 商品数据
const productData = ref([
  { name: 'iPhone 15', price: 5999, stock: 100 },
  { name: 'MacBook Pro', price: 12999, stock: 50 },
  { name: 'iPad Pro', price: 6999, stock: 80 }
])

// 订单数据
const orderData = ref([
  { orderNo: 'O202401001', customer: '张三', amount: 5999 },
  { orderNo: 'O202401002', customer: '李四', amount: 12999 },
  { orderNo: 'O202401003', customer: '王五', amount: 6999 }
])

// 客户数据
const customerData = ref([
  { name: '张三', phone: '13800138000', email: 'zhangsan@example.com' },
  { name: '李四', phone: '13800138001', email: 'lisi@example.com' },
  { name: '王五', phone: '13800138002', email: 'wangwu@example.com' }
])

// 查看商品
const viewProduct = (row) => {
  currentPage.value = 'productDetail'
  console.log('查看商品：', row)
}

// 编辑商品
const editProduct = (row) => {
  console.log('编辑商品：', row)
}

// 查看订单
const viewOrder = (row) => {
  currentPage.value = 'orderDetail'
  console.log('查看订单：', row)
}

// 查看客户
const viewCustomer = (row) => {
  currentPage.value = 'customerDetail'
  console.log('查看客户：', row)
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