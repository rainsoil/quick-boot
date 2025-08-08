<template>
  <div>
    <h2>动态列配置示例</h2>
    
    <div style="margin-bottom: 20px;">
      <el-button @click="toggleColumnVisibility">切换列显示</el-button>
      <el-button @click="addColumn">添加列</el-button>
      <el-button @click="removeColumn">删除列</el-button>
      <el-button @click="resetColumns">重置列</el-button>
    </div>
    
    <el-table :data="tableData" border stripe>
      <c7-json-table-column :columns="dynamicColumns">
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
        
        <!-- 自定义列插槽 -->
        <template #slot_custom="{ row }">
          <el-button size="small" @click="handleCustom(row)">自定义操作</el-button>
        </template>
      </c7-json-table-column>
    </el-table>
    
    <div style="margin-top: 20px;">
      <h3>当前列配置：</h3>
      <pre>{{ JSON.stringify(dynamicColumns, null, 2) }}</pre>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { c7JsonTableColumn } from 'c7-plus'

// 示例数据
const tableData = ref([
  {
    id: 1,
    name: '产品A',
    price: 100,
    stock: 50,
    category: 'electronics',
    status: 'active',
    rating: 4.5,
    sales: 1000,
    createTime: '2024-01-15'
  },
  {
    id: 2,
    name: '产品B',
    price: 200,
    stock: 30,
    category: 'clothing',
    status: 'active',
    rating: 4.2,
    sales: 800,
    createTime: '2024-01-10'
  },
  {
    id: 3,
    name: '产品C',
    price: 150,
    stock: 0,
    category: 'books',
    status: 'inactive',
    rating: 3.8,
    sales: 500,
    createTime: '2024-01-05'
  }
])

// 基础列配置
const baseColumns = [
  {
    columnType: 'text',
    label: 'ID',
    prop: 'id',
    width: 80,
    align: 'center',
    sortable: true,
    visible: true
  },
  {
    columnType: 'text',
    label: '产品名称',
    prop: 'name',
    width: 150,
    align: 'left',
    showOverflowTooltip: true,
    sortable: true,
    visible: true
  },
  {
    columnType: 'text',
    label: '价格',
    prop: 'price',
    width: 100,
    align: 'right',
    sortable: true,
    formatter: (row, column, cellValue) => {
      return `¥${cellValue}`
    },
    visible: true
  },
  {
    columnType: 'text',
    label: '库存',
    prop: 'stock',
    width: 100,
    align: 'center',
    sortable: true,
    visible: true
  },
  {
    columnType: 'tag',
    label: '分类',
    prop: 'category',
    width: 120,
    align: 'center',
    dictList: [
      { label: '电子产品', value: 'electronics', elTagType: 'primary' },
      { label: '服装', value: 'clothing', elTagType: 'success' },
      { label: '图书', value: 'books', elTagType: 'warning' }
    ],
    visible: true
  },
  {
    columnType: 'slot',
    label: '状态',
    prop: 'status',
    width: 100,
    align: 'center',
    slotName: 'slot_status',
    visible: true
  },
  {
    columnType: 'slot',
    label: '评分',
    prop: 'rating',
    width: 150,
    align: 'center',
    slotName: 'slot_rating',
    visible: true
  },
  {
    columnType: 'text',
    label: '销量',
    prop: 'sales',
    width: 100,
    align: 'right',
    sortable: true,
    visible: true
  },
  {
    columnType: 'text',
    label: '创建时间',
    prop: 'createTime',
    width: 120,
    align: 'center',
    sortable: true,
    visible: true
  },
  {
    columnType: 'slot',
    label: '操作',
    width: 200,
    align: 'center',
    fixed: 'right',
    slotName: 'slot_actions',
    visible: true
  }
]

// 可选列配置
const optionalColumns = [
  {
    columnType: 'slot',
    label: '自定义列',
    width: 120,
    align: 'center',
    slotName: 'slot_custom',
    visible: false
  },
  {
    columnType: 'text',
    label: '备注',
    prop: 'remark',
    width: 150,
    align: 'left',
    showOverflowTooltip: true,
    visible: false
  }
]

// 动态列配置
const dynamicColumns = ref([...baseColumns])

// 切换列显示
const toggleColumnVisibility = () => {
  // 随机切换一些列的可见性
  dynamicColumns.value.forEach(column => {
    if (Math.random() > 0.5) {
      column.visible = !column.visible
    }
  })
}

// 添加列
const addColumn = () => {
  const availableColumns = optionalColumns.filter(col => 
    !dynamicColumns.value.some(dynamicCol => dynamicCol.label === col.label)
  )
  
  if (availableColumns.length > 0) {
    const randomColumn = availableColumns[Math.floor(Math.random() * availableColumns.length)]
    dynamicColumns.value.push({
      ...randomColumn,
      visible: true
    })
  } else {
    alert('没有更多可添加的列了')
  }
}

// 删除列
const removeColumn = () => {
  const removableColumns = dynamicColumns.value.filter(col => 
    col.label !== 'ID' && col.label !== '产品名称' && col.label !== '操作'
  )
  
  if (removableColumns.length > 0) {
    const randomIndex = Math.floor(Math.random() * removableColumns.length)
    const columnToRemove = removableColumns[randomIndex]
    const index = dynamicColumns.value.findIndex(col => col.label === columnToRemove.label)
    
    if (index > -1) {
      dynamicColumns.value.splice(index, 1)
    }
  } else {
    alert('没有可删除的列了')
  }
}

// 重置列
const resetColumns = () => {
  dynamicColumns.value = [...baseColumns]
}

// 编辑操作
const handleEdit = (row) => {
  alert(`编辑产品：${row.name}`)
}

// 删除操作
const handleDelete = (row) => {
  if (confirm(`确定要删除产品 ${row.name} 吗？`)) {
    alert(`删除产品：${row.name}`)
  }
}

// 自定义操作
const handleCustom = (row) => {
  alert(`自定义操作：${row.name}`)
}
</script>

<style scoped>
h2 {
  margin-bottom: 15px;
  color: #333;
}

h3 {
  margin-top: 20px;
  color: #666;
}

pre {
  background-color: #f5f5f5;
  padding: 10px;
  border-radius: 4px;
  font-size: 12px;
  overflow-x: auto;
}
</style> 