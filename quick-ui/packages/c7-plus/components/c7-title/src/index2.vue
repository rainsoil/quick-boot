<template>
  <el-card shadow="always" class="button-container" :style="buttonContainerStyle">
    <template #default>
      <!-- 使用 div 包裹内容，并应用 flex 布局 -->
      <div class="content-wrapper">

        <!-- 色块部分 -->
        <div class="color-block" v-if="props.isShowColorBlock"
             :style="{backgroundColor: colorBlockStyle.backgroundColor}"></div>

        <!-- 文字部分 -->
        <span class="text" :style="textStyle">{{ props.label }}</span>

        <!-- 按钮部分，放置在 div 的最后面，这样它会靠右 -->
        <slot></slot>
      </div>
    </template>
  </el-card>
</template>

<script setup>
import {ref, computed, useSlots, defineOptions,watch} from 'vue';

defineOptions({
  name: 'c7Title'
})
// 定义属性
const props = defineProps({
  // label  组件标题
  label: {
    type: String,
    default: '',
    required: true
  },
  // 背景色(当为空的时候不显示)
  backgroundColor: {
    type: String,
    default: '#f0f2f5'
  },
  // 是否展示色块
  isShowColorBlock: {
    type: Boolean,
    default: false
  },
  // 色块颜色
  colorBlockColor: {
    type: String,
    default: '#409eff'
  },
  //文本内容大小
  textSize: {
    type: String,
    default: 'h2',
    validator(value) {
      return ['h1', 'h2', 'h3', 'h4', 'h5'].includes(value);
    }
  },
  // 是否加粗
  isBold: {
    type: Boolean,
    default: true
  }
})
// 样式绑定
const sizeClasses = {
  h1: '2em', // 32px
  h2: '1.5em', // 24px
  h3: '1.17em', // 18.72px
  h4: '1em', // 16px
  h5: '0.83em' // 13.28px
};

// 计算属性
const buttonContainerStyle = computed(() => {
  if (!props.backgroundColor) {
    return '';
  } else return {
    backgroundColor: props.backgroundColor
  }
})
// 色块计算属性
const colorBlockStyle = computed(() => {
  console.log(props.colorBlockColor)
  return {
    backgroundColor: props.colorBlockColor
  }
})
// 文本内容样式
const textStyle = computed(() => {
  return {
    fontWeight: `${props.isBold ? 'bold' : 'normal'}`,
    fontSize: `${sizeClasses[props.textSize]}`,
    //让文字占据尽可能多的空间
    flex: 1
  }
})
</script>

<style scoped>
/* 应用 Flexbox 布局 */
.content-wrapper {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
}

/* 定义色块样式 */
.color-block {
  width: 12px; /* 设置色块宽度 */
  height: 25px; /* 设置色块高度 */
  margin-right: 8px; /* 设置与文字的间距 */
}

/* 可选：为文字添加一些样式 */
.text {

  flex: 1; /* 让文字占据尽可能多的空间 */
  font-weight: bold; /* 加粗字体 */
}

/* 确保按钮靠右 */
.right-button {
  margin-left: auto; /* 强制按钮推到右侧 */
}
</style>
