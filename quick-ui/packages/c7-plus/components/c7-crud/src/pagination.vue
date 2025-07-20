<template>
  <div class="pagination">
    <el-pagination
        :background="background"
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        layout="prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
</template>

<script setup>
import {computed, onMounted, onUnmounted} from 'vue'
import {scrollTo} from '../../../utils/scroll-to.js'
import 'element-plus/es/components/pagination/style/css'
import {ElPagination} from 'element-plus'


// Props 定义
const props = defineProps({
  total: {
    required: true,
    type: Number
  },
  page: {
    type: Number,
    default: 1
  },
  limit: {
    type: Number,
    default: 20
  },
  pageSizes: {
    type: Array,
    default: () => [10, 20, 30, 50]
  },
  pagerCount: {
    type: Number,
    default: document.body.clientWidth < 992 ? 5 : 7
  },
  layout: {
    type: String,
    default: 'prev, pager, next, jumper'
  },
  background: {
    type: Boolean,
    default: true
  },
  autoScroll: {
    type: Boolean,
    default: true
  },
  hidden: {
    type: Boolean,
    default: false
  }
})

// Emits
const emit = defineEmits()

// 响应式布局（移动端隐藏部分控件）
const responsiveLayout = computed(() => {
  const isMobile = document.body.clientWidth < 768
  return isMobile ? 'prev, pager, next' : props.layout
})

// 分页器状态
const currentPage = computed({
  get() {
    return props.page
  },
  set(val) {
    emit('update:page', val)
  }
})
const pageSize = computed({
  get() {
    return props.limit
  },
  set(val) {
    emit('update:limit', val)
  }
})

// 防抖滚动函数
let scrollTimeout = null
const debouncedScrollTo = (x = 0, y = 0, duration = 300) => {
  if (scrollTimeout) clearTimeout(scrollTimeout)
  scrollTimeout = setTimeout(() => {
    scrollTo(x, y)
  }, duration)
}

// 事件处理
function handleSizeChange(val) {
  if (currentPage.value * val > props.total) {
    currentPage.value = 1
  }
  emit('pagination', {page: currentPage.value, limit: val})
  if (props.autoScroll) {
    debouncedScrollTo(0, 800)
  }
}

function handleCurrentChange(val) {
  emit('pagination', {page: val, limit: pageSize.value})
  if (props.autoScroll) {
    debouncedScrollTo(0, 800)
  }
}

// 响应式调整 pagerCount
const updatePagerCount = () => {
  const newPagerCount = document.body.clientWidth < 992 ? 5 : 7
  // 可通过 emit 更新父组件的 pagerCount 状态，或直接在 computed 中使用
}

onMounted(() => {
  window.addEventListener('resize', updatePagerCount)
})
onUnmounted(() => {
  window.removeEventListener('resize', updatePagerCount)
})
</script>


