import { defineComponent, toRefs, PropType } from "vue";
import type { ItemCols } from "../types";
import ColumnItem from "./column-item";
import SubColumnItem from "./sub-column-item";
import { has, isArray } from "lodash-es";
export default defineComponent({
  props: {
    column: {
      type: Object as PropType<ItemCols>,
      required: true,
      default: () => { },
    },
  },
  setup(props) {
    return {
      ...toRefs(props),
    };
  },
  render() {
    const {
      column: { children, ...propertys },
    } = this;
    return (
      <>
        <el-table-column {...propertys}>
          {children
            ? children.map((col: ItemCols) => {
              if (
                has(col, "children") && isArray(col.children)) {
                return <SubColumnItem column={col}></SubColumnItem>;
              } else {
                return <ColumnItem column={col}> </ColumnItem>;
              }
            })
            : null}
        </el-table-column>
      </>
    );
  },
});
