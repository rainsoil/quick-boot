<template>
  <el-row :gutter="10" class="mb8" v-if="props.buttons.enable">
    <!-- 新增按钮-->
    <el-button v-if="props.buttons.addBtn && props.buttons.addBtn.enable"
               :type="props.buttons.addBtn.type || 'primary'"
               plain
               :icon="props.buttons.addBtn.icon || 'plus'"
               :disabled="props.buttons.addBtn.disabled || false"
               @click="addBtnHandle()"
    >{{ props.buttons.addBtn.label || '新增' }}
    </el-button>

    <!-- 删除按钮 -->
    <el-button v-if="props.buttons.deleteBtn &&  props.buttons.deleteBtn.enable"
               :type="props.buttons.deleteBtn.type || 'danger'"
               plain
               :icon="props.buttons.deleteBtn.icon || 'Delete'"
               :disabled="props.buttons.deleteBtn.disabled || false"
               @click="state.deleteHandle()"
    >{{ props.buttons.deleteBtn.label || '删除' }}
    </el-button>

    <!-- 导出按钮 -->
    <el-button v-if="props.buttons.exportBtn &&  props.buttons.exportBtn.enable"
               :type="props.buttons.exportBtn.type || 'success' "
               plain
               :icon="props.buttons.exportBtn.icon ||  'Download'"
               :disabled="props.buttons.exportBtn.disabled || false"
               @click="exportHandle()"
    >{{ props.buttons.exportBtn.label || '导出' }}
    </el-button>

    <!-- 按钮扩展-->
    <slot name="appendButton"></slot>
  </el-row>
  <div style="margin-top: 30px"></div>
  <el-table :data="state.dataList" border v-bind="props" @sort-change="dataListSortChangeHandle"
            @selection-change="state.dataListSelectionChangeHandle">
    <!-- 多选-->
    <el-table-column v-if="selection" type="selection" align="center"/>
    <!-- 渲染 JSON 配置生成的列 -->
    <template v-for="(column, index) in sortedColumns" :key="index">
      <el-table-column
          v-if="column.type == 'text' || !column.type"
          :label="column.label"
          :prop="column.prop"
          :sortable="column.sortable"
          :bind="column.props"
      >
        <template #default="scope" v-if="column.formatter">
          {{
            column.formatter ? column.formatter(scope.row[column.prop], scope.row) : scope.row[column.prop]
          }}
        </template>
        <template v-slot="scope" v-if="column.isSlot">
          <slot :name="column.slotName || ('slot_' + column.prop)" v-bind="scope">
            <!-- 默认展示 -->
            {{ scope.row[column.prop] }}
          </slot>
        </template>
      </el-table-column>

      <!-- else if  type == 'dict' -->
      <el-table-column
          v-else-if="column.type == 'dict'"
          :label="column.label"
          :prop="column.prop"
          :sortable="column.sortable"
          :bind="column.props"
      >
        <template #default="scope">
          {{
            state.getDictLabel(column.dictType, scope.row[column.prop])
          }}
        </template>
      </el-table-column>
    </template>
    <el-table-column label="操作" order="99" prop="operate">
      <template v-slot="scope">
        <slot name="operate" v-bind="scope">
          <!-- 默认展示 -->
          <el-button v-if="props.buttons.deleteBtn &&  props.buttons.deleteBtn.enable" type="text" size="small"
                     @click="deleteBtnHandle(scope.row.id)">删除
          </el-button>
        </slot>
      </template>
    </el-table-column>
  </el-table>

  <!-- 分页 -->
  <el-pagination
      v-if="state.getDataListIsPage"
      @size-change="pageSizeChangeHandle"
      @current-change="pageCurrentChangeHandle"
      :current-page="state.page"
      :page-sizes="[10, 20, 50, 100]"
      background
      :page-size="state.limit"
      prev-text="上一页"
      next-text="下一页"
      layout="total, sizes, prev, pager, next, jumper"
      :total="state.total">
  </el-pagination>


</template>

<script setup lang="ts">
import {IButton, IColumn, IColumnEnum} from '../../c7-search/src/search.js'
import {IObject, ITableColumn} from '../../../types/ITypes'
import {tableHook} from "../../../hooks/tableHook.ts";
import {IViewHooks, IViewHooksOptions} from "../../../types/ITableHook";
import {ref, defineOptions, PropType, computed, reactive, toRefs} from 'vue'

defineOptions({
  name: 'c7Table'
})
const emit = defineEmits(["addBtnHandle", "deleteHandle", "exportHandle"])

const props = defineProps({
  tableProps: {
    type: Object as PropType<IViewHooksOptions>,
    required: false
  },
  // 列表查询参数
  tableParam: {
    type: Object,
    required: false,
    default: () => {
      return {}
    }
  },
  // 初始化参数(重置的时候不会被重置掉)
  initParam: {
    type: Object,
    required: false,
    default: () => {
      return {}
    }
  },
  // 表格数据
  tableData: {
    type: Array,
    required: true,
    default: () => []
  },
  // 列
  columns: {
    type: Array as PropType<ITableColumn[]>,
    required: true,
    default: () => []
  },
  // 是否多选
  selection: {
    type: Boolean,
    default: false
  },
  // 操作
  buttons: {
    type: Object as PropType<{
      enable: boolean,
      // 新增按钮
      addBtn: IButton,
      // 删除
      deleteBtn: IButton,
      // 导出
      exportBtn: IButton
    }>,
    default: () => ({
      enable: true,
      addBtn: {
        // 是否开启
        enable: true,
        // 按钮文本
        label: '新增',
        // 是否显示
        display: true,
        // 是否禁用
        disabled: false,
        // type
        type: 'primary',
        // icon
        icon: 'plus',
      },
      deleteBtn: {
        // 是否开启
        enable: true,
        // 按钮文本
        label: '删除',
        // 是否显示
        display: true,
        // 是否禁用
        disabled: false,
        // type
        type: 'danger',
        // icon
        icon: 'Delete',
      },
      exportBtn: {
        // 是否开启
        enable: false,
        // 按钮文本
        label: '导出',
        // 是否显示
        display: true,
        // 是否禁用
        disabled: false,
        // type
        type: 'success',
        // icon
        icon: 'Download',
      },
    })
  }
})

const view = reactive<IViewHooksOptions>({
  ...props.tableProps,
})
const state = reactive({...tableHook(view), ...toRefs(view)});

// 分页
const pageSizeChangeHandle = (val: number) => {
  state.pageSizeChangeHandle(val)
}

// 分页(当前页)
const pageCurrentChangeHandle = (val: number) => {
  state.pageCurrentChangeHandle(val)
}

// 排序
const dataListSortChangeHandle = (sort: IObject) => {
  state.dataListSortChangeHandle(sort)
}

const getDataList = () => {
  console.log(props.tableParam, props.initParam, Object.assign(props.initParam, props.tableParam))
  state.dataForm = Object.assign(props.initParam, props.tableParam);
  state.getDataList()
  console.log(props.tableParam)
  console.log(state.dataForm)
  console.log(state.dataList)
}

// 排序列：这里只排序 JSON 列，因为插槽内容是直接渲染的
const sortedColumns = computed(() => {
  return props.columns.sort((a, b) => a.order - b.order);
});
// 重置
const handleReset = () => {
  console.log(props.initParam)
  state.handleReset(props.initParam)
}

const addBtnHandle = () => {
  emit("addBtnHandle")
}
// 编辑按钮
const deleteBtnHandle = (id?: string) => {
  state.deleteHandle(id)
}

// 导出按钮
const exportHandle = () => {
  state.exportHandle()
}

// 多选的数据
const dataListSelections = () => {
  return state.dataListSelections
}

// 多选的id集合
const dataListSelectionsIds = () => {
  return state.dataListSelectionsIds
}

defineExpose({
  getDataList,
  handleReset,
  deleteBtnHandle,
  pageSizeChangeHandle,
  pageCurrentChangeHandle,
  dataListSortChangeHandle,
  dataListSelections,
  dataListSelectionsIds,
})
</script>
