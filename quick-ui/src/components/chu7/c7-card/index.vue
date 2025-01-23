<!-- 卡片组件-->
<template>
  <el-card class="box-card">
    <template #header>
      <div class="clearfix">
        <!-- 色块部分 -->
        <span  class="color-block" v-if="props.isShowColorBlock"
              :style="{backgroundColor: colorBlockStyle.backgroundColor}"></span>

        <!-- 文字部分 -->
        <span class="text" :style="textStyle">{{ props.label }}</span>
        <el-button style="float: right; padding: 3px 0" text @click="toggleContent">
          {{ isExpanded ? '收起' : '展开' }}
        </el-button>
      </div>
    </template>
    <transition name="fade">
      <div v-if="isExpanded">
        <!-- 这里是你的卡片内容 -->

        <slot></slot>
      </div>
    </transition>
  </el-card>
</template>

<script setup>
import {ref} from 'vue';

// 定义参数
const props = defineProps({
  // 定义参数
  // 卡片标题
  label: {
    type: String,
    default: ''
  },
  // 是否展示色块
  isShowColorBlock: {
    type: Boolean,
    default: true
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
});


// 定义是否展开的状态，默认为true表示展开
const isExpanded = ref(true);

// 切换内容显示状态的方法
const toggleContent = () => {
  isExpanded.value = !isExpanded.value;
};

// 样式绑定
const sizeClasses = {
  h1: '2em', // 32px
  h2: '1.5em', // 24px
  h3: '1.17em', // 18.72px
  h4: '1em', // 16px
  h5: '0.83em' // 13.28px
};
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
/* 添加任何必要的样式 */
.clearfix::after {
  content: "";
  clear: both;
  display: table;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */
{
  opacity: 0;
}

.color-block {
  display: inline-block;
  width: 10px; /* 色块宽度 */
  height: 23px; /* 色块高度 */
  margin-right: 8px; /* 色块与文字之间的间距 */
  vertical-align: middle; /* 垂直对齐方式 */
}
/* 可选：为文字添加一些样式 */
.text {

  flex: 1; /* 让文字占据尽可能多的空间 */
  font-weight: bold; /* 加粗字体 */
}

</style>
