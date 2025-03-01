<template>
  <h1 class="ep-title">
    <span class="label" :style="labelStyle">{{ label }}</span>
    <span><slot></slot></span>
  </h1>
</template>

<script setup>
import {computed, defineOptions, defineProps} from 'vue';
defineOptions({
  name: 'c7Title'
})

// 定义属性
const props = defineProps({
  label: {
    type: String,
    default: '默认标题',
  },
  labelSize: {
    type: String,
    default: 'h1', // 默认是 h1 大小
  },
  labelColor: {
    type: String,
    default: '#24a6d9', // 默认空字符串，表示没有颜色
  },
});

// 计算 label 的 fontSize 和 label:before 的背景颜色
const labelStyle = computed(() => {
  let fontSize;

  // 根据传入的 labelSize 设置不同的字体大小
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
      // 如果是自定义的 px 值
      fontSize = /^(\d+)px$/.test(props.labelSize) ? props.labelSize : '20px'; // 默认 20px
      break;
  }

  // 设置一个自定义 CSS 变量来控制背景色
  return {
    fontSize,
    '--label-color': props.labelColor || 'transparent', // 如果 labelColor 为空，使用 transparent
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
    background-color: var(--label-color); /* 使用 CSS 变量来绑定背景色 */
    visibility: visible; /* 确保显示时才有色块 */
  }

  /* 如果 labelColor 为空，隐藏色块 */
  .label[style*="--label-color: transparent"]::before {
    visibility: hidden;
  }
}
</style>
