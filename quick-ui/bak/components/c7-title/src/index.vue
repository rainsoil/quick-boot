<template>

  <h1 class="ep-title">
    <span class="label" :style="labelStyle">{{ label }}</span>
    <span><slot></slot></span>
  </h1>
</template>

<script setup lang="ts">
import {computed, defineOptions, defineProps} from 'vue';
import type {PropType} from 'vue';

defineOptions({
  name: 'c7Title'
});



const props = defineProps({
  /**
   * 标题名称
   */
  label: {
    type: String as PropType<string>,
    default: '默认标题',
  },
  /**
   * 标题大小
   */
  labelSize: {
    type: String as PropType<string>,
    default: 'h1',
  },
  /**
   * 标题颜色
   */
  labelColor: {
    type: String as PropType<string>,
    default: '',
  },
});

const labelStyle = computed(() => {
  let fontSize;

  switch (props.labelSize) {
    case 'h1':
      fontSize = '32px';
      break;
    case 'h2':
      fontSize = '28px';
      break;
    case 'h3':
      fontSize = '24px';
      break;
    case 'h4':
      fontSize = '20px';
      break;
    case 'h5':
      fontSize = '18px';
      break;
    case 'h6':
      fontSize = '16px';
      break;
    default:
      fontSize = /^(\d+)px$/.test(props.labelSize)
          ? props.labelSize
          : '20px';
      break;
  }

  return {
    fontSize,
    '--label-color': props.labelColor || 'transparent',
  };
});
</script>

<style lang="scss" scoped>
.ep-title {
  color: #333;
  padding: 18px 0;
  border-bottom: 3px solid #333;
  margin-bottom: 20px;
  font-family: "Microsoft Yahei";
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  .label {
    position: relative;
    font-weight: 700;
    display: inline-block;
    font-size: inherit;
    --label-color: transparent; /* 默认透明色 */
  }

  .label:before {
    content: "";
    display: inline-block;
    position: absolute;
    height: 3px;
    width: 100%;
    bottom: -18px;
    background-color: var(--label-color);
    visibility: visible;
  }

  .label[style*="--label-color: transparent"]::before {
    visibility: hidden;
  }
}
</style>
