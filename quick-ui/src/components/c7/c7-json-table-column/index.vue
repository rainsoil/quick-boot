<template>
  <template v-if="sortedColumns.length > 0">
    <el-table-column
        v-for="(item, index) in sortedColumns"
        :key="index"
        :type="item.type"
        :index="item.index"
        :label="item.label"
        :column-key="item.columnKey"
        :prop="item.prop"
        :width="item.width"
        :min-width="item.minWidth"
        :fixed="item.fixed"
        :render-header="item.renderHeader"
        :sortable="item.sortable"
        :sort-method="item.sortMethod"
        :sort-by="item.sortBy"
        :sort-orders="item.sortOrders"
        :resizable="item.resizable"
        :formatter="item.formatter"
        :show-overflow-tooltip="item.showOverflowTooltip"
        :align="item.align"
        :header-align="item.headerAlign"
        :class-name="item.className"
        :label-class-name="item.labelClassName"
        :selectable="item.selectable"
        :reserve-selection="item.reserveSelection"
        :filters="item.filters"
        :filter-placement="item.filterPlacement"
        :filter-class-name="item.filterClassName"
        :filter-multiple="item.filterMultiple"
        :filter-method="item.filterMethod"
        :filtered-value="item.filteredValue"
        :tooltip-formatter="item.tooltipFormatter"
    >
      <!-- 当columnType为TAG时使用c7DictTag组件 -->
      <template v-if="item.columnType === 'tag'" #default="{ row }">
        <c7-dict-tag :options="item.dictList" :modelValue="row[item.prop]"/>
      </template>


      <!-- 当columnType为IMAGE时使用el-image组件 -->
      <template v-else-if="item.columnType === 'image'" #default="{ row }">
        <!--      {{row[item.prop] && row[item.prop].split(',')}}-->
        <!--        <el-image preview-teleported :preview-src-list="row[item.prop] && row[item.prop].split(',')" />-->
        <!--      <c7-title label="预览图片,并指定大小"></c7-title>-->

        <c7-preview :urls="row[item.prop]" width="100px" height="100px" cover-type="file"></c7-preview>
      </template>
      <!-- 当columnType为SLOT时透传插槽 -->
      <template v-else-if="item.columnType === 'slot'" #default="{ row, $index }">
        <slot   :name="item.slotName || ('slot_' + item.prop)"
                :row="row" :index="$index"/>
      </template>
    </el-table-column>
  </template>
</template>

<script setup lang="ts">
import {computed, defineProps, defineEmits} from 'vue'
import c7DictTag from '../c7-dict-tag/index.vue'
import c7Preview from '../c7-preview/index.vue'

// 定义类型枚举
enum ColumnEnumType {
  TEXT = 'text',
  IMAGE = 'image',
  TAG = 'tag',
  SLOT = 'slot'
}

// 定义列配置属性接口
interface TableColumnProps {
  columnType: ColumnEnumType;
  visible?: boolean;
  order?: number;
  type?: 'selection' | 'index' | 'expand';
  index?: number | ((row: any, rowIndex: number) => number);
  columnKey?: string;
  label?: string;
  prop?: string;
  width?: string | number;
  minWidth?: string | number;
  fixed?: boolean | 'left' | 'right';
  sortable?: boolean | 'custom';
  sortMethod?: (a: any, b: any) => number;
  sortBy?: string | string[] | ((row: any, index: number) => any);
  sortOrders?: ('ascending' | 'descending' | null)[];
  resizable?: boolean;
  formatter?: (row: any, column: any, cellValue: any, index: number) => any;
  showOverflowTooltip?: boolean | object;
  align?: 'left' | 'center' | 'right';
  headerAlign?: 'left' | 'center' | 'right';
  className?: string;
  labelClassName?: string;
  selectable?: (row: any, index: number) => boolean;
  reserveSelection?: boolean;
  filters?: Array<{ text: string; value: any }>;
  filterPlacement?: string;
  filterMultiple?: boolean;
  filterMethod?: (value: any, row: any, column: any) => boolean;
  filteredValue?: any[];
  [key: string]: any;
}

defineOptions({
  name: 'c7JsonTableColumn'
})
const emit = defineEmits(['update:modelValue'])

const props = defineProps({
  columns: {
    type: Array as () => TableColumnProps[],
    default: () => []
  },
  modelValue: {
    type: Object as () => Record<string, any>,
    default: () => ({})
  }
})

// 优化后的计算属性
const sortedColumns = computed(() => {
  return props.columns
      .filter(column => column.visible !== false)
      .map((item, index) => ({
        ...item,
        order: item.order ?? index
      }))
      .sort((a, b) => a.order - b.order)
})
</script>
