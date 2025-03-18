import {
  defineComponent,
  ref,
  toRefs,
  inject,
  renderSlot,
  PropType,
  computed,
} from "vue";
import type { IPageProps } from "./types";
import { has } from "lodash-es";
import {ElPagination} from 'element-plus'
export default defineComponent({
  props: {
    page: {
      type: Object as PropType<IPageProps>,
      default: () => ({ current: 1, size: 10 }),
    },
  },
  emits: ["update:page", "page-change"],
  setup(props, { attrs, emit, slots }) {
    let page = computed({
      get: () => props.page,
      set: (val) => emit("update:page", val),
    });

    const sizeChange = (pageSize: number) =>
      emit("page-change", page.value, "pageSize");
    const currentChange = (currentPage: number) =>
      emit("page-change", page.value, "currentPage");

    /* 渲染分页组件 */
    const renderPagination = () => (
      <div class="quick-table-pagination">
        <ElPagination
          v-model:current-page={page.value.current}
          v-model:pageSize={page.value.size}
          total={page.value.total||0}
          background
          layout='total,prev,pager,next,sizes,jumper'
          {...attrs}
          onCurrent-change={currentChange}
          onSize-change={sizeChange}
          />
        </div>
    );

   
    
    return {
      ...toRefs(props),
      slots,
      renderPagination,
    };
  },
  render() {
    const {
      slots: { tfooter },
      renderPagination,
    } = this;
    return (
      <>
      { tfooter? renderSlot(this.slots, "tfooter"): renderPagination()}
      </>
     
    );
  },
});
