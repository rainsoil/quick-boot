<template>
  <el-table :data="tableData" border @selection-change="dataListSelectionChangeHandle" v-bind="props">
    <!-- 多选-->
    <el-table-column v-if="selection" type="selection" align="center"/>
    <!-- 渲染 JSON 配置生成的列 -->
    <template v-for="(column, index) in sortedColumns" :key="index">

      <el-table-column
          v-if="column.from === 'json'"
          v-bind="column.props"
          :label="column.props.label"
          :prop="column.props.prop"
      >
        <template #default="scope" v-if="column.props.formatter">
          {{
            column.props.formatter ? column.props.formatter(scope.row[column.props.prop], scope.row) : scope.row[column.props.prop]
          }}
        </template>
        <template v-slot="scope" v-if="column.props.isSolt">
          <slot :name="column.props.prop" :item="scope.row"></slot>
        </template>


        <template v-slot="scope" v-if="column.props.dictType">
          <dict-tag :dict-type=column.props.dictType :value="scope.row[column.props.prop]"/>
          <!--          <span>{{ state.getDictLabel("sys_normal_disable", scope.row.status)}}</span>-->
          <!--                    <span>{{ scope.row }}</span>-->
        </template>
        <template v-slot="scope" v-if="column.props.type == 'file'">
          <image-preview :src="scope.row[column.props.prop]" :width="column.props.width"
                         :show-button="column.props.showButton?column.props.showButton:false"
                         :placeholder="column.props.placeholder?column.props.placeholder:undefined"
                         :height="column.props.height"></image-preview>
          <!--          <dict-tag :dict-type=column.props.dictType :value="scope.row[column.props.prop]"/>-->
          <!--          <span>{{ state.getDictLabel("sys_normal_disable", scope.row.status)}}</span>-->
          <!--                    <span>{{ scope.row }}</span>-->
        </template>

      </el-table-column>
    </template>

    <!-- 渲染用户通过 slot 传入的 el-table-column -->
    <slot/>
  </el-table>
  <el-pagination :current-page="props.page" :page-sizes="[10, 20, 50, 100]" :page-size="props.limit"
                 :total="props.total" layout="total, sizes, prev, pager, next, jumper"
                 @size-change="pageSizeChangeHandle"
                 @current-change="pageCurrentChangeHandle"></el-pagination>

</template>

<script setup>
import {computed} from 'vue';

const emit = defineEmits(["pageSizeChangeHandle", "pageCurrentChangeHandle", "dataListSelectionChangeHandle"]);
// 接收父组件传递的 props
const props = defineProps({
  columns: {
    type: Array,
    default: () => [],
  },
  tableData: {
    type: Array,
    required: true,
  },
  tableProps: {
    type: Object,
    default: () => ({}),
  },
  selection: {
    type: Boolean,
    default: true
  },
  page: {
    type: Number,
    default: 1,
  },
  limit: {
    type: Number,
    default: 10,
  },
  total: {
    type: Number,
    default: 0,
  }


})
const pageSizeChangeHandle = (val) => {
  console.log(val);
  emit('pageSizeChangeHandle', val);
}
const pageCurrentChangeHandle = (val) => {
  console.log(val);
  emit('pageCurrentChangeHandle', val);
}
const dataListSelectionChangeHandle = (val) => {
  console.log("dataListSelectionChangeHandle", val)
  emit("selection-change", val)
}
const mergedColumns = computed(() => {
  // 将 JSON 配置的列合并，并设置其顺序
  return props.columns.map((col, index) => ({
    from: 'json',
    props: col,
    order: col.order || (index + 1) * 10, // 使用 JSON 配置中的 order 或者默认顺序
  }));
});

// 排序列：这里只排序 JSON 列，因为插槽内容是直接渲染的
const sortedColumns = computed(() => {
  return mergedColumns.value.sort((a, b) => a.order - b.order);
});
</script>
