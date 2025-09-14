<template>
  <div class="json-table">
    <!-- 搜索表单 -->
    <el-form v-if="showSearch" ref="searchForm" :model="searchParam" >
      <el-row gutter="20">
        <JsonForm
            :columns="searchColumns"
            v-model="searchParam"
        />
        <el-col :span="8">
          <el-form-item>
            <el-button type="primary" @click="handleSearch">搜索</el-button>
            <el-button @click="handleReset">重置</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <!-- 操作栏 -->
    <div class="operate-bar" v-if="$slots.operate || showAdd || showEdit || showDelete || showRefresh || showExport || showImport">
      <slot name="operate">
        <!-- 默认操作按钮 -->
        <el-button v-if="showAdd" type="primary" icon="Plus" @click="handleAdd">新增</el-button>
        <el-button v-if="showEdit" type="success" icon="Edit" @click="handleEdit" :disabled="!hasSelection">修改</el-button>
        <el-button v-if="showDelete" type="danger" icon="Delete" @click="handleDelete" :disabled="!hasSelection">删除</el-button>
        <el-button v-if="showExport" type="warning" icon="Download" @click="handleExport">导出</el-button>
        <el-button v-if="showImport" type="warning" icon="Upload" @click="handleImport">导入</el-button>
      </slot>
    </div>

    <!-- 表格 -->
    <el-table
        :data="tableData"
        :row-key="rowKey"
        v-loading="loading"
        @selection-change="handleSelectionChange"
        style="width: 100%"
        table-layout="auto"
        :tree-props="tableProps.treeProps"
        :default-expand-all="tableProps.defaultExpandAll"
        :lazy="tableProps.lazy"
    >
      <!-- 多选列 -->
      <el-table-column
          v-if="showSelection"
          type="selection"
          width="55"
      />
      
      <!-- 序号列 -->
      <el-table-column
          v-if="showIndex"
          type="index"
          label="序号"
          width="60"
      />

      <!-- 数据列 -->
      <el-table-column
          v-for="column in tableColumns"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :min-width="column.minWidth"
          :align="column.align || 'left'"
          :show-overflow-tooltip="column.showOverflowTooltip !== false"
      >
        <template #default="{ row, $index }">
        <!-- 操作列插槽 -->
        <slot
            v-if="column.prop === 'table-operate'"
            name="table-operate"
            :row="row"
            :index="$index"
        />
          <!-- 自定义插槽 -->
          <slot
              v-else-if="column.slotName"
              :name="column.slotName"
              :row="row"
              :index="$index"
              :value="row[column.prop]"
          />
          <!-- 字典标签显示 -->
          <el-tag
              v-else-if="column.columnType === 'tag' && column.dictList && Array.isArray(column.dictList) && column.dictList.length > 0"
              :type="getTagType(row[column.prop], column.dictList)"
              size="small"
          >
            {{ getDictLabel(row[column.prop], column.dictList) }}
          </el-tag>
          <!-- 默认显示 -->
          <span v-else>{{ row[column.prop] }}</span>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        v-if="!pageHidden"
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="pageSizes"
        :total="computedTotal"
        :layout="pageLayout"
        :background="pageBackground"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        class="pagination"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch, defineOptions, defineProps } from 'vue'
import JsonForm from '../c7-json-form/index.vue'

defineOptions({ name: 'JsonTable' })

const props = defineProps({
  // 数据相关
  listFunction: { type: Function, required: false },
  tableData: { type: Array, default: () => [] },
  rowKey: { type: String, default: 'id' },
  rowsKey: { type: String, default: 'data.records' },
  totalKey: { type: String, default: 'data.total' },
  
  // 搜索相关
  showSearch: { type: Boolean, default: true },
  searchColumns: { type: Array, default: () => [] },
  searchParam: { type: Object, default: () => ({}) },
  
  // 表格相关
  tableColumns: { type: Array, default: () => [] },
  tableProps: { type: Object, default: () => ({}) },
  showIndex: { type: Boolean, default: true },
  
  // 分页相关
  total: { type: Number, default: 0 },
  pageSizes: { type: Array, default: () => [10, 20, 30, 50] },
  pageLayout: { type: String, default: 'total, sizes, prev, pager, next, jumper' },
  pageBackground: { type: Boolean, default: true },
  pageHidden: { type: Boolean, default: false },
  
  // 初始化
  init: { type: Boolean, default: true },
  initParam: { type: Object, default: () => ({}) }
})

const emit = defineEmits(['update:searchParam', 'selection-change', 'addBtnHandle', 'editBtnHandle', 'deleteBtnHandle', 'refreshDataList', 'exportBtnHandle', 'importBtnHandle'])

// 响应式数据
const loading = ref(false)
const internalTableData = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const searchParam = ref({ ...props.searchParam })
const total = ref(0)
const selectedRows = ref([])

// 计算属性
const tableData = computed(() => {
  // 如果传入了 tableData，直接使用
  if (props.tableData && props.tableData.length > 0) {
    return props.tableData
  }
  // 否则使用内部数据
  return internalTableData.value
})
const computedTotal = computed(() => total.value)

// 从 tableProps 中获取按钮显示配置
const showAdd = computed(() => props.tableProps?.showAdd || false)
const showEdit = computed(() => props.tableProps?.showEdit || false)
const showDelete = computed(() => props.tableProps?.showDelete || false)
const showRefresh = computed(() => props.tableProps?.showRefresh || false)
const showExport = computed(() => props.tableProps?.showExport || false)
const showImport = computed(() => props.tableProps?.showImport || false)
const showSelection = computed(() => props.tableProps?.selection || false)
const hasSelection = computed(() => selectedRows.value.length > 0)

// 监听搜索参数变化
watch(() => props.searchParam, (newVal) => {
  searchParam.value = { ...newVal }
}, { deep: true })

// 监听初始化参数变化
watch(() => props.initParam, (newVal, oldVal) => {
  // 只有当initParam真正发生变化时才重新获取数据
  if (newVal && Object.keys(newVal).length > 0) {
    const newValStr = JSON.stringify(newVal)
    const oldValStr = JSON.stringify(oldVal || {})
    
    if (newValStr !== oldValStr) {
      console.log('initParam changed, refetching data:', newVal)
      fetchData()
    }
  }
}, { deep: true, immediate: false })

// 获取数据
const fetchData = async () => {
  // 如果传入了 tableData，不需要调用 API
  if (props.tableData && props.tableData.length > 0) {
    return
  }
  
  if (!props.listFunction) {
    console.warn('listFunction is required when tableData is not provided')
    return
  }
  
  loading.value = true
  try {
    const params = {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      ...searchParam.value,
      ...props.initParam
    }
    console.log("params",params)
    
    const response = await props.listFunction(params)
    console.log('C7JsonTable fetchData response:', response)
    console.log('C7JsonTable rowsKey:', props.rowsKey)
    console.log('C7JsonTable totalKey:', props.totalKey)
    
    // 根据配置的键路径解析数据
    if (response) {
      // 解析数据列表
      const rowsPath = props.rowsKey.split('.')
      let rowsData = response
      for (const key of rowsPath) {
        rowsData = rowsData?.[key]
      }
      internalTableData.value = Array.isArray(rowsData) ? rowsData : []
      console.log('C7JsonTable parsed rowsData:', rowsData)
      console.log('C7JsonTable internalTableData:', internalTableData.value)
      
      // 解析总数
      const totalPath = props.totalKey.split('.')
      let totalData = response
      for (const key of totalPath) {
        totalData = totalData?.[key]
      }
      total.value = typeof totalData === 'number' ? totalData : 0
      console.log('C7JsonTable parsed totalData:', totalData)
      console.log('C7JsonTable total:', total.value)
    } else {
      internalTableData.value = []
      total.value = 0
    }
  } catch (error) {
    console.error('获取数据失败:', error)
    internalTableData.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  currentPage.value = 1
  emit('update:searchParam', { ...searchParam.value })
  fetchData()
}

// 重置
const handleReset = () => {
  searchParam.value = {}
  currentPage.value = 1
  emit('update:searchParam', {})
  fetchData()
}

// 分页大小变化
const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  fetchData()
}

// 当前页变化
const handleCurrentChange = (page) => {
  currentPage.value = page
  fetchData()
}

// 选择变化
const handleSelectionChange = (selection) => {
  selectedRows.value = selection
  emit('selection-change', selection)
}

// 按钮事件处理
const handleAdd = () => {
  emit('addBtnHandle')
}

const handleEdit = () => {
  if (selectedRows.value.length === 1) {
    emit('editBtnHandle', selectedRows.value[0])
  }
}

const handleDelete = () => {
  if (selectedRows.value.length > 0) {
    emit('deleteBtnHandle', selectedRows.value)
  }
}

const handleRefresh = () => {
  emit('refreshDataList')
  fetchData()
}

const handleExport = () => {
  emit('exportBtnHandle')
}

const handleImport = () => {
  emit('importBtnHandle')
}

// 字典翻译辅助函数
const getDictLabel = (value, dictList) => {
  if (!dictList || !Array.isArray(dictList) || dictList.length === 0) return value
  const dictItem = dictList.find(item => item.value === value || item.value === String(value))
  return dictItem ? dictItem.label : value
}

const getTagType = (value, dictList) => {
  if (!dictList || !Array.isArray(dictList) || dictList.length === 0) return ''
  const dictItem = dictList.find(item => item.value === value || item.value === String(value))
  if (dictItem && dictItem.elTagType) {
    return dictItem.elTagType
  }
  // 默认根据值判断类型
  if (value === '0' || value === 0) return 'success'
  if (value === '1' || value === 1) return 'danger'
  return 'info'
}

// 组件挂载时获取数据
onMounted(() => {
  if (props.init) {
    fetchData()
  }
})

// 暴露方法给父组件
defineExpose({
  getDataList: fetchData,
  refreshData: fetchData
})
</script>

<style scoped>
.json-table {
  width: 100%;
}

.search-form {
  margin-bottom: 16px;
  padding: 16px;
  background: #f5f5f5;
  border-radius: 4px;
}

.operate-bar {
  margin-bottom: 16px;
}

.pagination {
  margin-top: 16px;
  text-align: right;
}

@media (max-width: 768px) {
  .search-form {
    padding: 12px;
  }
  
  .pagination {
    text-align: center;
  }
}
</style>
