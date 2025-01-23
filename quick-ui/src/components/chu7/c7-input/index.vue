<template>

  <el-col :span="config.span">
    <el-form-item :prop="config.prop" :label="config.label" :label-width="config.labelWidth">
      <!-- 使用具名插槽或作用域插槽时可以添加额外的属性 -->
      <template v-for="(_, slot) of slots" #[slot]="scope">
        <slot :name="slot" v-bind="scope"/>
      </template>

      <!-- 默认插槽，如果父组件没有提供内容，则显示 el-input -->
      <el-input
          v-bind="$attrs"
          v-on="$listeners"
          v-model="localValue"
          :placeholder="config.placeholder"
          v-if="!slots.default"
      />

      <!-- 如果父组件提供了默认插槽内容，则显示这些内容 -->
      <template v-else>
        <slot v-bind:value="localValue"></slot>
      </template>
    </el-form-item>
  </el-col>
</template>

<script setup>
import {ref, computed, useSlots} from 'vue';
// 定义参数
const props = defineProps({
  // 父组件传过来的值

  // 栅格占据的列数
  span: {
    type: String,
    default: 12
  },
  // 表单域标签的宽度
  labelWidth: {
    type: String,
    default: '140px'
  },
  // 标签文本
  label: {
    type: String,
    default: ''
  },
  // 属性
  prop: {
    type: String,
    default: ''
  },
  // 绑定的属性值
  modelValue: {
    type: [String, Number],
    default: ''
  },

  placeholder: {
    type: String,
    default: ''
  }

})
// 定义组件的emits
const emit = defineEmits(['update:modelValue']);


// 创建一个本地值引用，用于处理 v-model 绑定
const localValue = ref(props.modelValue);

// 监听 props 中 modelValue 的变化并更新本地值
watch(() => props.modelValue, (newValue) => {
  localValue.value = newValue;
}, {immediate: true});

// 当本地值变化时，通知父组件更新
watch(localValue, (newValue) => {
  emit('update:modelValue', newValue);
});

// 获取当前组件的插槽
const slots = useSlots();

// 配置对象
const config = computed(() => {
  return {
    // 栅格占据的列数
    span: props.span,
    // 表单域标签的宽度
    labelWidth: props.labelWidth,
    // 标签文本
    label: props.label,
    // 属性
    prop: props.prop
  }
})

</script>
