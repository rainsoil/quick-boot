<template>


  <el-row :gutter="10" class="mb8" v-if="props.buttons.enable">
    <!-- 新增按钮-->
    <el-button v-if="props.buttons.addBtn.enable"
               :type="props.buttons.addBtn.type"
               plain
               :icon="props.buttons.addBtn.icon"
               :disabled="props.buttons.addBtn.disabled"
               @click="addBtnHandle()"
    >{{ props.buttons.addBtn.label }}
    </el-button>

    <!--修改按钮 -->
    <el-button v-if="props.buttons.deleteBtn.enable"
               :type="props.buttons.deleteBtn.type"
               plain
               :icon="props.buttons.deleteBtn.icon"
               :disabled="props.buttons.deleteBtn.disabled"
               @click="deleteBtnHandle()"
    >{{ props.buttons.deleteBtn.label }}

    </el-button>
  </el-row>
  <div style="margin-top: 30px"></div>
  <el-table :data="tableData" border v-bind="props">
    <!-- 多选-->
    <el-table-column v-if="selection" type="selection" align="center"/>
    <!-- 渲染 JSON 配置生成的列 -->
    <template v-for="(column, index) in sortedColumns" :key="index">



      <el-table-column

          v-if="column.type == 'text' || !column.type"
          :label="column.label"
          :prop="column.prop"
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
<!--        <template v-slot="scope" v-if="column.props.dictType">-->
<!--          <dict-tag :dict-type=column.props.dictType :value="scope.row[column.props.prop]"/>-->
<!--          &lt;!&ndash;          <span>{{ state.getDictLabel("sys_normal_disable", scope.row.status)}}</span>&ndash;&gt;-->
<!--          &lt;!&ndash;                    <span>{{ scope.row }}</span>&ndash;&gt;-->
<!--        </template>-->
      </el-table-column>

    </template>
  </el-table>

</template>

<script setup lang="ts">
import {IButton, IColumn, IColumnEnum} from '../../c7-search/src/search.js'
import {ITableColumn} from '../../interface'
import tableView from "../../../hooks/tableView";
import {ref, defineOptions, PropType, computed} from 'vue'

defineOptions({
  name: 'c7Table'
})
const emit = defineEmits(["addBtnHandle", "deleteHandle", "exportHandle"])

const props = defineProps({

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
      deleteBtn: IButton
      // 导出

      // 导入

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
    })
  }
})


const addBtnHandle = () => {
  emit("addBtnHandle")
}
// 编辑按钮
const deleteBtnHandle = () => {
  emit("deleteHandle")
}


// 排序列：这里只排序 JSON 列，因为插槽内容是直接渲染的
const sortedColumns = computed(() => {
  return props.columns.sort((a, b) => a.order - b.order);
});
</script>
