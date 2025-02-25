<template>
  <component
      :is="type === 'drawer' ? 'el-drawer' : 'el-dialog'"
      v-bind="mergedProps"
      :visible.sync="visible"
      @open="handleOpen"
      @close="handleClose"
  >
    <slot name="title" v-if="type === 'dialog'">{{ mergedProps.title }}</slot>
    <slot></slot>
    <template v-if="type === 'dialog'" #footer>
      <slot name="footer"></slot>
    </template>
  </component>
</template>

<script>
import { defineComponent, computed } from 'vue';

export default defineComponent({
  name: 'CustomDrawerDialog',
  props: {
    type: {
      type: String,
      default: 'dialog', // 默认使用 Dialog
      validator: (value) => ['dialog', 'drawer'].includes(value),
    },
    visible: {
      type: Boolean,
      required: true,
    },
    drawerProps: {
      type: Object,
      default: () => ({}),
    },
    dialogProps: {
      type: Object,
      default: () => ({}),
    },
  },
  emits: ['update:visible', 'open', 'close'],
  setup(props, { emit }) {
    const mergedProps = computed(() => {
      return props.type === 'drawer'
          ? { ...props.drawerProps }
          : { ...props.dialogProps };
    });

    const handleOpen = () => {
      emit('open');
    };

    const handleClose = () => {
      emit('close');
      emit('update:visible', false);
    };

    return {
      mergedProps,
      handleOpen,
      handleClose,
    };
  },
});
</script>

<style scoped>
/* 可以根据需求添加样式 */
</style>
