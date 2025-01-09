<template>
  <el-table :data="data" v-bind="tableProps">
    <!-- 多选-->
    <el-table-column v-if="tableProps.selection" type="selection" align="center"/>
    <!-- 统一渲染排序后的所有列 -->
    <el-table-column
        v-for="column in combinedColumns"
        :key="column.prop"
        v-bind="column"
        :formatter="getFormatter(column)"
    >


      <!-- 如果有插槽配置则使用插槽 -->
      <template v-if="$slots[column.slot]" v-slot="scope">
        <slot :name="column.slot" :scope="scope"/>
      </template>


    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: "CustomTable",
  props: {
    data: {
      type: Array,
      required: true,
    },
    columns: {
      type: Array,
      default: () => [],
    },
    // tableProps: {
    //   type: Object,
    //   default: () => ({
    //     selection: true
    //   }),
    // },
  },
  computed: {
    // 将 JSON 配置的列和插槽中的列合并并排序
    combinedColumns() {
      const slotColumns = this.$slots.default
          ? this.$slots.default()
              .filter((vnode) => vnode.type.name === "ElTableColumn")
              .map((vnode) => ({
                ...vnode.props,
                slot: vnode.props.prop,
                order: vnode.props.order || 0,
              }))
          : [];

      // 合并 JSON 配置的列和插槽列
      return [...this.columns, ...slotColumns]
          .map((column) => ({
            ...column,
            order: column.order || 0, // 默认 order 值
          }))
          .sort((a, b) => a.order - b.order); // 根据 order 排序
    },
  },
  methods: {
    getFormatter(column) {
      return column.formatter
          ? (row, col, value) => column.formatter(value, row)
          : undefined;
    },
  },
};
</script>
