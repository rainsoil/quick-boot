<template>
  <c7-crud
      ref="crud"
      :list-function="(param) => listFunction(param)"
      :search-param="searchParam"
      @clean-param="searchParam = {}"
      :init="init"
      :init-param="initParam"
      :rowsKey="rowsKey"
      :totalKey="totalKey"
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
    <!-- 搜索表单 -->
    <template #search>
      <el-form ref="form" :model="searchParam">
        <el-row>
          <c7-json-form
              :columns="searchColumns"
              v-model="searchParam"
              @form-change="handleFormChange"
          >
            <!-- 透传 c7-json-form 插槽 -->
            <template
                v-for="item in searchColumns"
                #[slotName(item)]="slotProps"
            >
              <slot
                  :name="slotName(item)"
                  :item="item"
                  :searchParam="searchParam"
                  :modelValue="searchParam[item.prop]"
              />
            </template>
          </c7-json-form>
        </el-row>
      </el-form>
    </template>

    <!-- 操作栏 -->
    <template #operate>
      <slot name="operate"></slot>
    </template>

    <!-- 表格内容 -->
    <template #default>
      <c7-json-table-column :columns="tableColumns">
        <!-- 透传 c7-json-table-column 插槽 -->
        <template
            v-for="item in tableColumns"
            #[slotName(item)]="slotProps"
        >
          <slot
              :name="slotName(item)"
              v-bind="slotProps"
          />
        </template>
      </c7-json-table-column>
    </template>
  </c7-crud>
</template>

<script setup lang="ts">
import {ref} from "vue";
import c7Crud from '../../c7-crud/index'
import c7JsonForm from "../../c7-json-form/index"
import c7JsonTableColumn from "../../c7-json-table-column/index"
import {TableColumnProps} from "../../c7-json-table-column/types/JsonTableColumnTypes";
import {FormColumn} from "../../c7-json-form/types/JsonFormTypes";

defineOptions({
  name: 'c7JsonTable'
})

const emit = defineEmits(['handleFormChange'])

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
  rowKey: {
    type: String,
    default: 'id'
  },
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
  labelWidth: {
    type: Number,
    default: 100
  },
  searchParam: {
    type: Object,
    default: () => ({})
  },
  searchColumns: {
    type: Array as () => FormColumn[],
    default: () => []
  },
  tableColumns: {
    type: Array as () => TableColumnProps[],
    default: () => []
  }
})

// 内部搜索参数绑定
const searchParam = ref({})

// 表单 change 回调
const handleFormChange = (data: Record<string, any>) => {
  emit("handleFormChange", data)
}

// 插槽命名处理函数
const slotName = (item: { slotName?: string; prop: string }) =>
    item.slotName || `slot_${item.prop}`
</script>

<style scoped>
</style>
