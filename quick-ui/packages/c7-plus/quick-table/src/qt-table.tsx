import {
  defineComponent,
  ref,
  toRefs,
  provide,
  PropType,
  onMounted,
  defineExpose,
} from "vue";
import type { ItemCols } from "./types";
import QtColumnItem from "./qt-columns/column-item";
import QtSubColumnItem from "./qt-columns/sub-column-item";
import { has, isArray } from "lodash-es";
import { ElTable } from 'element-plus'

export default defineComponent({
  props: {
    data: {
      type: Array,
      required: true,
      default: () => [],
    },
    columns: {
      type: Array as PropType<Array<ItemCols>>,
      required: true,
      default: () => [],
    },
  },
  setup(props, { attrs, slots, expose }) {
    const tableRef = ref();
    const { append, empty, ...moreSlots } = slots;
    onMounted(() => {
    });

    provide<{
      colSlots: Record<string, any>;
    }>("TableObserver", {
      colSlots: moreSlots,
    });

    const clearSelection = () => tableRef.value.clearSelection();
    const setCurrentRow = (row: any) => tableRef.value.setCurrentRow(row);
    const getSelectionRows = () => tableRef.value.getSelectionRows();
    const toggleRowSelection = (row: any, selected: boolean) =>
      tableRef.value.toggleRowSelection(row, selected);
    const toggleAllSelection = () => tableRef.value.toggleAllSelection();
    const clearSort = () => tableRef.value.clearSort();
    const doLayout = () => tableRef.value.doLayout();
    const clearFilter = (columnKeys?: string[] | undefined) =>
      tableRef.value.clearFilter(columnKeys);
    const toggleRowExpansion = (row: any, expanded?: boolean | undefined) =>
      tableRef.value.toggleRowExpansion(row, expanded);
    const sort = (prop: string, order: string) =>
      tableRef.value.sort(prop, order);

    expose({
      setCurrentRow,
      getSelectionRows,
      toggleRowSelection,
      clearSelection,
      clearFilter,
      toggleAllSelection,
      toggleRowExpansion,
      clearSort,
      doLayout,
      sort,
    });

    return {
      tableRef,
      ...toRefs(props),
      attrs,
      append,
      empty,
    };
  },
  render() {
    const { data, attrs, columns, append, empty } = this;
    const tSlots = { append, empty };
    return (
      <ElTable ref="tableRef" data={data} {...attrs} v-slots={tSlots}>
        {isArray(columns) ? columns.map((col: ItemCols) => {
          if (has(col, "children") && isArray(col.children)) return <QtSubColumnItem column={col}></QtSubColumnItem>;
          return <QtColumnItem column={col}></QtColumnItem>;
        }) : null}
      </ElTable>
    );
  },
});
