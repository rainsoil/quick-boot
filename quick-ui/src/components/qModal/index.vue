<template>
  <component
      :is="currentComponent"
      :model-value="visible"
      v-bind="mergedProps"
      @update:model-value="handleVisibilityChange"
      @close="handleClose"
  >
    <!-- 插入默认插槽内容 -->
    <slot/>

    <!-- 仅当为 Dialog 时才支持 footer 插槽 -->
    <template v-if="$slots.footer && mode === 'dialog' && footer" #footer>
      <slot name="footer" v-if="footer"/>
    </template>

    <template v-if="!$slots.footer && mode === 'dialog'  && footer" #footer>

      <div class="dialog-footer">
        <el-button type="primary" @click="submit">确 定</el-button>
        <el-button @click="handleClose">取 消</el-button>
      </div>
    </template>
    <!-- 仅当为 Dialog 时才支持 footer 插槽 -->
    <template v-if="$slots.footer && mode != 'dialog'" #footer>
      <slot name="footer"/>
    </template>
  </component>
</template>

<script>
export default {
  name: "CustomModal",
  props: {
    footer: {
      type: Boolean,
      default: true,
    },
    mode: {
      type: String,
      default: "dialog", // 可选值 'dialog' 或 'drawer'
      validator: (value) => ["dialog", "drawer"].includes(value),
    },
    visible: {
      type: Boolean,
      required: true,
    },
    modalProps: {
      type: Object,
      default: () => ({}),
    },
  },
  computed: {
    // 动态选择 Dialog 或 Drawer 组件
    currentComponent() {
      return this.mode === "drawer" ? "el-drawer" : "el-dialog";
    },
    // 合并用户传入的配置与默认配置
    mergedProps() {
      return {
        ...this.defaultProps,
        ...this.modalProps,
      };
    },
    // 根据模式返回默认配置
    defaultProps() {
      return this.mode === "drawer"
          ? {
            direction: "rtl", // Drawer 的默认方向
          }
          : {
            width: "50%", // Dialog 的默认宽度
          };
    },
  },
  methods: {
    handleVisibilityChange(newValue) {
      this.$emit("update:visible", newValue);
    },
    handleClose() {
      this.$emit("update:visible", false);
      this.$emit("close");
    },
    submit() {
      this.$emit("submit");
    },

  },
};
</script>
