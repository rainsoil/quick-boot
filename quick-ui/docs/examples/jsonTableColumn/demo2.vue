<template>
  <div>
    <h2>不同类型列配置示例</h2>
    
    <el-table :data="tableData" border stripe>
      <c7-json-table-column :columns="columns">
        <!-- 自定义操作插槽 -->
        <template #slot_actions="{ row, index }">
          <el-button size="small" type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
        
        <!-- 状态插槽 -->
        <template #slot_status="{ row }">
          <el-tag :type="row.status === 'active' ? 'success' : 'danger'">
            {{ row.status === 'active' ? '启用' : '禁用' }}
          </el-tag>
        </template>
        
        <!-- 评分插槽 -->
        <template #slot_rating="{ row }">
          <el-rate v-model="row.rating" disabled show-score />
        </template>
      </c7-json-table-column>
    </el-table>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { c7JsonTableColumn } from 'c7-plus'

// 示例数据
const tableData = ref([
  {
    id: 1,
    name: '张三',
    age: 25,
    email: 'zhangsan@example.com',
    phone: '13800138001',
    department: '技术部',
    position: '前端工程师',
    salary: 15000,
    status: 'active',
    rating: 4.5,
    avatar: 'https://via.placeholder.com/50x50/409EFF/FFFFFF?text=张',
    images: 'https://via.placeholder.com/100x100/67C23A/FFFFFF?text=1,https://via.placeholder.com/100x100/E6A23C/FFFFFF?text=2',
    joinDate: '2023-01-15',
    tags: 'vue,react,typescript'
  },
  {
    id: 2,
    name: '李四',
    age: 28,
    email: 'lisi@example.com',
    phone: '13800138002',
    department: '产品部',
    position: '产品经理',
    salary: 18000,
    status: 'active',
    rating: 4.8,
    avatar: 'https://via.placeholder.com/50x50/67C23A/FFFFFF?text=李',
    images: 'https://via.placeholder.com/100x100/409EFF/FFFFFF?text=3,https://via.placeholder.com/100x100/F56C6C/FFFFFF?text=4',
    joinDate: '2022-08-20',
    tags: '产品,设计,用户研究'
  },
  {
    id: 3,
    name: '王五',
    age: 30,
    email: 'wangwu@example.com',
    phone: '13800138003',
    department: '设计部',
    position: 'UI设计师',
    salary: 16000,
    status: 'inactive',
    rating: 4.2,
    avatar: 'https://via.placeholder.com/50x50/E6A23C/FFFFFF?text=王',
    images: 'https://via.placeholder.com/100x100/909399/FFFFFF?text=5',
    joinDate: '2023-03-10',
    tags: '设计,sketch,figma'
  }
])

// 列配置
const columns = [
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
    label: '年龄',
    prop: 'age',
    width: 80,
    align: 'center',
    sortable: true
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
    label: '手机号',
    prop: 'phone',
    width: 130,
    align: 'center'
  },
  {
    columnType: 'tag',
    label: '部门',
    prop: 'department',
    width: 120,
    dictList: [
      { label: '技术部', value: '技术部', elTagType: 'primary' },
      { label: '产品部', value: '产品部', elTagType: 'success' },
      { label: '设计部', value: '设计部', elTagType: 'warning' },
      { label: '运营部', value: '运营部', elTagType: 'info' }
    ]
  },
  {
    columnType: 'tag',
    label: '职位',
    prop: 'position',
    width: 150,
    dictList: [
      { label: '前端工程师', value: '前端工程师', elTagType: 'primary' },
      { label: '后端工程师', value: '后端工程师', elTagType: 'primary' },
      { label: '产品经理', value: '产品经理', elTagType: 'success' },
      { label: 'UI设计师', value: 'UI设计师', elTagType: 'warning' }
    ]
  },
  {
    columnType: 'text',
    label: '薪资',
    prop: 'salary',
    width: 120,
    align: 'right',
    formatter: (row, column, cellValue) => {
      return `¥${cellValue.toLocaleString()}`
    }
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
    columnType: 'image',
    label: '头像',
    prop: 'avatar',
    width: 80,
    align: 'center'
  },
  {
    columnType: 'image',
    label: '作品集',
    prop: 'images',
    width: 150,
    align: 'center'
  },
  {
    columnType: 'text',
    label: '入职日期',
    prop: 'joinDate',
    width: 120,
    align: 'center',
    sortable: true
  },
  {
    columnType: 'tag',
    label: '技能标签',
    prop: 'tags',
    width: 200,
    dictList: [
      { label: 'vue', value: 'vue', elTagType: 'success' },
      { label: 'react', value: 'react', elTagType: 'primary' },
      { label: 'typescript', value: 'typescript', elTagType: 'warning' },
      { label: '产品', value: '产品', elTagType: 'info' },
      { label: '设计', value: '设计', elTagType: 'warning' },
      { label: 'sketch', value: 'sketch', elTagType: 'danger' },
      { label: 'figma', value: 'figma', elTagType: 'success' }
    ]
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

// 编辑操作
const handleEdit = (row) => {
  alert(`编辑用户：${row.name}`)
}

// 删除操作
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