import { defineComponent, toRefs, PropType, inject, renderSlot } from "vue";
import type { ItemCols, ISlots } from "../types";
import { has, isFunction } from "lodash-es";

export default defineComponent({
  props: {
    column: {
      type: Object as PropType<ItemCols>,
      required: true,
      default: () => { },
    },
  },
  setup(props) {
    const { colSlots }: Record<string, any> = inject("TableObserver") as any;
    return {
      ...toRefs(props),
      colSlots,
    };
  },
  render() {
    const {
      column: { prop, label, render, ...property },
      colSlots,
    } = this;
    return (
      <>
        <el-table-column prop={prop} label={label} show-overflow-tooltip {...property}
          v-slots={{
            header: (scope: any) => {
              if (has(colSlots, `${prop}.header`)) return renderSlot(colSlots, `${prop}.header`, scope)
              if (label && isFunction(label)) return label(scope)
              return label

            },
            default: (scope: any) => {
              if (has(colSlots, `${prop}`)) return renderSlot(colSlots, `${prop}`, scope)
              if (render && isFunction(render)) return render(scope)
              return scope.row[prop]
            }
          }}>
        </el-table-column>
      </>
    );
  },
});
