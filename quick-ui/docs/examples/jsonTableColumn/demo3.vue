<template>
  <div>
    <h2>表格高级功能示例</h2>
    
    <div style="margin-bottom: 20px;">
      <el-button @click="toggleSelection">切换选择</el-button>
      <el-button @click="clearSelection">清除选择</el-button>
    </div>
    
    <el-table 
      :data="tableData" 
      border 
      stripe
      @selection-change="handleSelectionChange"
      @sort-change="handleSortChange"
    >
      <c7-json-table-column :columns="columns">
        <!-- 自定义操作插槽 -->
        <template #slot_actions="{ row, index }">
          <el-button size="small" type="primary" @click="handleView(row)">查看</el-button>
          <el-button size="small" type="success" @click="handleEdit(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
        
        <!-- 进度条插槽 -->
        <template #slot_progress="{ row }">
          <el-progress 
            :percentage="row.progress" 
            :color="getProgressColor(row.progress)"
            :stroke-width="8"
          />
        </template>
        
        <!-- 开关插槽 -->
        <template #slot_enabled="{ row }">
          <el-switch 
            v-model="row.enabled" 
            @change="handleStatusChange(row)"
            active-text="启用"
            inactive-text="禁用"
          />
        </template>
        
        <!-- 评分插槽 -->
        <template #slot_rating="{ row }">
          <el-rate 
            v-model="row.rating" 
            disabled 
            show-score 
            text-color="#ff9900"
          />
        </template>
      </c7-json-table-column>
    </el-table>
    
    <div style="margin-top: 20px;">
      <h3>选中数据：</h3>
      <pre>{{ JSON.stringify(selectedRows, null, 2) }}</pre>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
// 示例数据
const tableData = ref([
  {
    id: 1,
    name: '项目A',
    category: 'web',
    priority: 'high',
    status: 'active',
    progress: 85,
    rating: 4.5,
    enabled: true,
    budget: 50000,
    team: '前端团队'
  },
  {
    id: 2,
    name: '项目B',
    category: 'mobile',
    priority: 'medium',
    status: 'pending',
    progress: 60,
    rating: 4.2,
    enabled: true,
    budget: 30000,
    team: '移动端团队'
  },
  {
    id: 3,
    name: '项目C',
    category: 'backend',
    priority: 'low',
    status: 'inactive',
    progress: 30,
    rating: 3.8,
    enabled: false,
    budget: 25000,
    team: '后端团队'
  }
])

// 选中的行
const selectedRows = ref([])

// 列配置
const columns = [
  {
    type: 'selection',
    width: 55,
    align: 'center'
  },
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
    label: '项目名称',
    prop: 'name',
    width: 150,
    align: 'left',
    showOverflowTooltip: true,
    sortable: true
  },
  {
    columnType: 'tag',
    label: '项目类型',
    prop: 'category',
    width: 120,
    align: 'center',
    dictList: [
      { label: 'Web项目', value: 'web', elTagType: 'primary' },
      { label: '移动端项目', value: 'mobile', elTagType: 'success' },
      { label: '后端项目', value: 'backend', elTagType: 'warning' }
    ]
  },
  {
    columnType: 'tag',
    label: '优先级',
    prop: 'priority',
    width: 100,
    align: 'center',
    sortable: true,
    dictList: [
      { label: '高', value: 'high', elTagType: 'danger' },
      { label: '中', value: 'medium', elTagType: 'warning' },
      { label: '低', value: 'low', elTagType: 'info' }
    ]
  },
  {
    columnType: 'tag',
    label: '状态',
    prop: 'status',
    width: 100,
    align: 'center',
    dictList: [
      { label: '进行中', value: 'active', elTagType: 'success' },
      { label: '待开始', value: 'pending', elTagType: 'warning' },
      { label: '已暂停', value: 'inactive', elTagType: 'info' }
    ]
  },
  {
    columnType: 'slot',
    label: '进度',
    prop: 'progress',
    width: 150,
    align: 'center',
    slotName: 'slot_progress'
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
    columnType: 'slot',
    label: '启用状态',
    prop: 'enabled',
    width: 120,
    align: 'center',
    slotName: 'slot_enabled'
  },
  {
    columnType: 'text',
    label: '预算',
    prop: 'budget',
    width: 120,
    align: 'right',
    sortable: true,
    formatter: (row, column, cellValue) => {
      return `¥${cellValue.toLocaleString()}`
    }
  },
  {
    columnType: 'tag',
    label: '负责团队',
    prop: 'team',
    width: 120,
    align: 'center',
    dictList: [
      { label: '前端团队', value: '前端团队', elTagType: 'primary' },
      { label: '移动端团队', value: '移动端团队', elTagType: 'success' },
      { label: '后端团队', value: '后端团队', elTagType: 'warning' }
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

// 获取进度条颜色
const getProgressColor = (progress) => {
  if (progress >= 80) return '#67C23A'
  if (progress >= 60) return '#E6A23C'
  if (progress >= 40) return '#F56C6C'
  return '#909399'
}

// 选择变化处理
const handleSelectionChange = (selection) => {
  selectedRows.value = selection
  console.log('选中行变化:', selection)
}

// 排序变化处理
const handleSortChange = ({ column, prop, order }) => {
  console.log('排序变化:', { column, prop, order })
}

// 状态变化处理
const handleStatusChange = (row) => {
  console.log(`项目 ${row.name} 状态变化为: ${row.enabled ? '启用' : '禁用'}`)
}

// 查看操作
const handleView = (row) => {
  alert(`查看项目：${row.name}`)
}

// 编辑操作
const handleEdit = (row) => {
  alert(`编辑项目：${row.name}`)
}

// 删除操作
const handleDelete = (row) => {
  if (confirm(`确定要删除项目 ${row.name} 吗？`)) {
    alert(`删除项目：${row.name}`)
  }
}

// 切换选择
const toggleSelection = () => {
  if (selectedRows.value.length > 0) {
    selectedRows.value = []
  } else {
    selectedRows.value = [...tableData.value]
  }
}

// 清除选择
const clearSelection = () => {
  selectedRows.value = []
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