<!--


-->

<template>
  <c7-crud
      ref="crud"
      :list-function="(param) => listFunction(param)"
      :search-param="searchParam"
      @clean-param="searchParam = {}"
      :init="init"
      :init-param="initParam"
      :row-key="rowKey"
      :page-total="pageTotal"
      :page-page="pagePage"
      :page-limit="pageLimit"
      :page-sizes="pageSizes"
      :page-layout="pageLayout"
      :page-background="pageBackground"
      :page-auto-scroll="pageAutoScroll"
      :show-search="showSearch"
      :label-width="labelWidth"
      @update:search-param="val => searchParam = val"
      @update:show-search="val => showSearch = val"
  >

    <!-- search-->
    <template #search>
      <el-form ref="form" :model="searchParam">
        <el-row>
          <!-- 表单组件 -->
          <c7-json-form
              :columns="searchColumns"
              v-model="searchParam"
              @form-change="handleFormChange"
          >
            <slot name="search"></slot>


          </c7-json-form>
        </el-row>
      </el-form>
    </template>
    <template #operate>
      <slot name="operate"></slot>
    </template>
    <template #default>
      <c7-json-table-column
          :columns="tableColumns"
      >
        <!-- 自定义插槽列内容 -->
        <template #default="{ row, index }">
          <slot name="tableColumnSlot" :row="row" :index="index"/>
        </template>
      </c7-json-table-column>
    </template>
  </c7-crud>
</template>

<script setup lang="ts">
import {ref} from "vue";
// import {c7Crud, c7JsonForm, c7JsonTableColumn} from "c7-plus";
import c7Crud from '../../c7-crud/index'
import {TableColumnProps} from "../../c7-json-table-column/types/JsonTableColumnTypes.js";
import {FormColumn} from "../../c7-json-form/types/JsonFormTypes";
// 定义事件
const emit = defineEmits(['handleFormChange'])
defineOptions({
  name: 'c7JsonTable'
})
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
  },
  // 搜索字段
  searchColumns: {
    type: Array as () => FormColumn[],
    default: () => []
  },
  tableColumns: {
    type: Array as () => TableColumnProps[],
    default: () => []
  },
})

const searchParam = ref({})


// 表单变化事件处理
const handleFormChange = (data: Record<string, any>) => {
  emit("handleFormChange", data)
}
</script>

<style scoped>

</style>
