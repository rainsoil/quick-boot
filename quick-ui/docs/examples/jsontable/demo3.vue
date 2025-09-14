<template>
  <div>
    <h2>复杂表格功能示例</h2>
    
    <c7-json-table
      ref="crud"
      :list-function="getTableData"
      :searchParam="searchParam"
      @cleanParam="searchParam = {}"
      :pageTotal="pageTotal"
      :pageSizes="[10, 20, 50, 100]"
      :searchColumns="searchColumns"
      :tableColumns="tableColumns"
      @handleFormChange="handleFormChange"
    >
      <!-- 操作栏插槽 -->
      <template #operate>
        <el-button type="primary" @click="handleAdd">新增</el-button>
        <el-button type="success" @click="handleBatchEdit">批量编辑</el-button>
        <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
        <el-button type="warning" @click="handleExport">导出</el-button>
      </template>

      <!-- 自定义表格列插槽 -->
      <template #slot_actions="{ row, index }">
        <el-button size="small" type="primary" @click="handleView(row)">查看</el-button>
        <el-button size="small" type="success" @click="handleEdit(row)">编辑</el-button>
        <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
      </template>

      <!-- 状态插槽 -->
      <template #slot_status="{ row }">
        <el-tag :type="row.status === 'active' ? 'success' : row.status === 'inactive' ? 'danger' : 'warning'">
          {{ row.status === 'active' ? '启用' : row.status === 'inactive' ? '禁用' : '待审核' }}
        </el-tag>
      </template>

      <!-- 评分插槽 -->
      <template #slot_rating="{ row }">
        <el-rate v-model="row.rating" disabled show-score />
      </template>
    </c7-json-table>
  </div>
</template>

<script setup>
import { ref } from 'vue'
// 搜索参数
const searchParam = ref({
  name: '',
  email: '',
  department: '',
  status: ''
})

// 分页总数
const pageTotal = ref(500)

// 搜索表单配置
const searchColumns = [
  {
    label: '姓名',
    prop: 'name',
    type: 'input',
    placeholder: '请输入姓名',
    span: 6
  },
  {
    label: '邮箱',
    prop: 'email',
    type: 'input',
    placeholder: '请输入邮箱',
    span: 6
  },
  {
    label: '部门',
    prop: 'department',
    type: 'select',
    placeholder: '请选择部门',
    span: 6,
    dataList: [
      { label: '技术部', value: 'tech' },
      { label: '产品部', value: 'product' },
      { label: '设计部', value: 'design' },
      { label: '运营部', value: 'operation' }
    ]
  },
  {
    label: '状态',
    prop: 'status',
    type: 'select',
    placeholder: '请选择状态',
    span: 6,
    dataList: [
      { label: '启用', value: 'active' },
      { label: '禁用', value: 'inactive' },
      { label: '待审核', value: 'pending' }
    ]
  }
]

// 表格列配置
const tableColumns = [
  {
    columnType: 'text',
    label: 'ID',
    prop: 'id',
    width: 80,
    align: 'center',
    sortable: true
  },
  {
    columnType: 'text',
    label: '姓名',
    prop: 'name',
    width: 120,
    align: 'left',
    showOverflowTooltip: true
  },
  {
    columnType: 'text',
    label: '邮箱',
    prop: 'email',
    width: 200,
    align: 'left',
    showOverflowTooltip: true
  },
  {
    columnType: 'text',
    label: '年龄',
    prop: 'age',
    width: 80,
    align: 'center',
    sortable: true
  },
  {
    columnType: 'tag',
    label: '性别',
    prop: 'gender',
    width: 80,
    align: 'center',
    dictList: [
      { label: '男', value: 'male', elTagType: 'primary' },
      { label: '女', value: 'female', elTagType: 'success' }
    ]
  },
  {
    columnType: 'tag',
    label: '部门',
    prop: 'department',
    width: 120,
    align: 'center',
    dictList: [
      { label: '技术部', value: 'tech', elTagType: 'primary' },
      { label: '产品部', value: 'product', elTagType: 'success' },
      { label: '设计部', value: 'design', elTagType: 'warning' },
      { label: '运营部', value: 'operation', elTagType: 'info' }
    ]
  },
  {
    columnType: 'slot',
    label: '状态',
    prop: 'status',
    width: 100,
    align: 'center',
    slotName: 'slot_status'
  },
  {
    columnType: 'slot',
    label: '评分',
    prop: 'rating',
    width: 150,
    align: 'center',
    slotName: 'slot_rating'
  },
  {
    columnType: 'text',
    label: '薪资',
    prop: 'salary',
    width: 120,
    align: 'right',
    sortable: true,
    formatter: (row, column, cellValue) => {
      return `¥${cellValue.toLocaleString()}`
    }
  },
  {
    columnType: 'text',
    label: '创建时间',
    prop: 'createTime',
    width: 150,
    align: 'center',
    sortable: true
  },
  {
    columnType: 'slot',
    label: '操作',
    width: 200,
    align: 'center',
    fixed: 'right',
    slotName: 'slot_actions'
  }
]

// 模拟数据获取函数
const getTableData = (params) => {
  console.log('获取表格数据，参数：', params)
  
  return new Promise((resolve) => {
    setTimeout(() => {
      const mockData = {
        'data.data.records': [
          {
            id: 1,
            name: '张三',
            email: 'zhangsan@example.com',
            age: 25,
            gender: 'male',
            department: 'tech',
            status: 'active',
            rating: 4.5,
            salary: 15000,
            createTime: '2024-01-15 10:30:00'
          },
          {
            id: 2,
            name: '李四',
            email: 'lisi@example.com',
            age: 28,
            gender: 'female',
            department: 'product',
            status: 'active',
            rating: 4.8,
            salary: 18000,
            createTime: '2024-01-14 14:20:00'
          },
          {
            id: 3,
            name: '王五',
            email: 'wangwu@example.com',
            age: 30,
            gender: 'male',
            department: 'design',
            status: 'inactive',
            rating: 3.8,
            salary: 12000,
            createTime: '2024-01-13 09:15:00'
          }
        ],
        'data.data.total': 500
      }
      resolve(mockData)
    }, 500)
  })
}

// 表单变化处理
const handleFormChange = (data) => {
  console.log('表单数据变化：', data)
}

// 新增处理
const handleAdd = () => {
  alert('新增用户')
}

// 批量编辑处理
const handleBatchEdit = () => {
  alert('批量编辑')
}

// 批量删除处理
const handleBatchDelete = () => {
  if (confirm('确定要批量删除选中的用户吗？')) {
    alert('批量删除成功')
  }
}

// 导出处理
const handleExport = () => {
  alert('导出数据')
}

// 查看处理
const handleView = (row) => {
  alert(`查看用户：${row.name}`)
}

// 编辑处理
const handleEdit = (row) => {
  alert(`编辑用户：${row.name}`)
}

// 删除处理
const handleDelete = (row) => {
  if (confirm(`确定要删除用户 ${row.name} 吗？`)) {
    alert(`删除用户：${row.name}`)
  }
}
</script>

<style scoped>
h2 {
  margin-bottom: 15px;
  color: #333;
}
</style> 