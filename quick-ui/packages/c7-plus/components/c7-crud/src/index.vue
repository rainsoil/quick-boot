<template>
  <el-form
      :model="queryParams"
      ref="queryRef"
      v-show="showSearch"
      :inline="true"
      :label-width="labelWidth"
  >
    <!-- 搜索区域的卡槽 -->
    <slot name="search" :queryParams="queryParams"></slot>
    <el-form-item>
      <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
      <el-button icon="Refresh" @click="resetQuery">重置</el-button>
    </el-form-item>
  </el-form>

  <el-row :gutter="10" class="mb8">
    <!-- 操作栏的卡槽 -->
    <slot name="operate"></slot>
  </el-row>

  <!-- 表格数据 -->
  <el-table
      v-loading="loading"
      :data="dataList"
      v-bind="$attrs"
      @selection-change="handleSelectionChange"
  >
    <!-- 多选列 -->
    <el-table-column type="selection" width="55"></el-table-column>
    <!-- 表格列插槽 -->
    <slot></slot>
  </el-table>

  <pagination
      v-show="total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      :page-sizes="pageSizes"
      :layout="pageLayout"
      :background="pageBackground"
      :auto-scroll="pageAutoScroll"
      @pagination="getList"
  />
</template>

<script setup>
import {reactive, ref, onMounted, watch} from 'vue'
import pagination from "./pagination.vue"
import {jsonGet} from '../../../utils/utils.ts'

// 定义 props
const props = defineProps({
  init: {
    type: Boolean,
    default: true
  },
  initParam: {
    type: Object,
    default: () => ({})
  },
  listFunction: {
    type: Function,
    required: true
  },
  /* 行主键字段（用于多选返回主键集合） */
  rowKey: {
    type: String,
    default: 'id'
  },
  /* 分页器配置 */
  pageTotal: {
    type: Number,
    required: true
  },
  pagePage: {
    type: Number,
    default: 1
  },
  pageLimit: {
    type: Number,
    default: 10
  },
  pageSizes: {
    type: Array,
    default: () => [10, 20, 30, 50]
  },
  pageLayout: {
    type: String,
    default: 'prev, pager, next, jumper'
  },
  pageBackground: {
    type: Boolean,
    default: true
  },
  pageAutoScroll: {
    type: Boolean,
    default: true
  },
  pageHidden: {
    type: Boolean,
    default: false
  },
  /* 搜索区域控制 */
  showSearch: {
    type: Boolean,
    default: true
  },
  rowsKey: {
    type: String,
    default: 'rows'
  },
  totalKey: {
    type: String,
    default: 'total'
  },
  /* 新增 label 宽度参数 */
  labelWidth: {
    type: Number,
    default: 100
  },
  /* 新增 searchParam 参数 */
  searchParam: {
    type: Object,
    default: () => ({})
  }
})

// 定义事件
const emit = defineEmits(['update:showSearch', 'selection-change', 'update:searchParam', 'cleanParam'])

// 查询参数
const queryParams = ref({
  pageNum: props.pagePage,
  pageSize: props.pageLimit
})

// 本地搜索参数
const localSearchParam = ref({})

// 监听 searchParam 变化，保持本地状态同步
watch(
    () => props.searchParam,
    (newVal) => {
      localSearchParam.value = {...newVal}
    },
    {immediate: true, deep: true}
)

const showSearch = ref(props.showSearch)
const loading = ref(false)
const dataList = ref([])
const total = ref(props.pageTotal)

// 获取数据
const getList = async () => {
  try {
    loading.value = true
    const params = {
      ...props.initParam,
      ...queryParams.value,
      ...localSearchParam.value // 使用本地搜索参数
    }

    const res = await props.listFunction(params)
    dataList.value = jsonGet(res, props.rowsKey, [])
    total.value = jsonGet(res, props.totalKey, 0)
  } finally {
    loading.value = false
  }
}

// 初始化数据
if (props.init) {
  onMounted(() => {
    getList()
  })
}

// 搜索按钮
const handleQuery = () => {
  queryParams.value.pageNum = 1
  getList()
}

// 重置按钮
const resetQuery = () => {
  queryParams.value = {}
  queryParams.value.pageNum = 1
  queryParams.value.pageSize = 10
  localSearchParam.value = {} // 清空本地搜索参数
  emit('update:searchParam', {}) // 通知父组件更新
  emit('cleanParam') // 通知父组件更新
  handleQuery()
}

// 多选事件处理
const handleSelectionChange = (rows) => {
  // 从选中行中提取主键值
  const selectedKeys = rows.map(row => row[props.rowKey])
  emit('selection-change', selectedKeys)
}
</script>

<style scoped>
.mb8 {
  margin-bottom: 8px;
}
</style>
