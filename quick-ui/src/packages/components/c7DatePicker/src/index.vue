<template>

  <el-col :span="config.span">
    <el-form-item :prop="config.prop" :label="config.label" :label-width="config.labelWidth">
      <!-- 使用具名插槽或作用域插槽时可以添加额外的属性 -->
      <template v-for="(_, slot) of slots" #[slot]="scope">
        <slot :name="slot" v-bind="scope"/>
      </template>

      <!-- 默认插槽，如果父组件没有提供内容，则显示 el-input -->
      <!--      <el-input-->
      <!--          v-bind="$attrs"-->
      <!--          v-on="$listeners"-->
      <!--          v-model="localValue"-->
      <!--          :placeholder="config.placeholder"-->
      <!--          v-if="!slots.default"-->
      <!--      />-->
      <el-date-picker
          v-model="localValue"
          :format="config.format"
          :value-format="config.valueFormat"
          v-bind="$attrs"
          v-on="$listeners"
          :placeholder="config.placeholder"
          v-if="!slots.default"
          :type="config.type"
      />
      <!-- 如果父组件提供了默认插槽内容，则显示这些内容 -->
      <template v-else>
        <slot v-bind:value="localValue"></slot>
      </template>
    </el-form-item>
  </el-col>
</template>
<script setup>
import {ref, computed, useSlots, defineOptions,watch} from 'vue';
defineOptions({
  name: 'C7DatePicker'
})

//参数
const props = defineProps({
  // 栅栏占据的列数
  span: {
    type: String,
    default: 8
  },
  // 表单域标签的款第
  labelWidth: {
    type: String,
    default: '140'
  },
  // 标签文本
  label: {
    type: String,
    default: ''
  },
  // 输入框占位文本
  placeholder: {
    type: String,
    default: '请选择'
  },
  // 属性名称
  prop: {
    type: String,
    default: ''
  },
  // 绑定值
  modelValue: {
    type: Object,
    default: ''
  },

  format: {
    type: String,
    default: 'YYYY-MM-DD'
  },

  valueFormat: {
    type: String,
    default: 'YYYY-MM-DD'
  },
  type: {
    type: String,
    default: 'date'
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
    prop: props.prop,
    // 提示信息
    placeholder: props.placeholder,
    format: props.format,
    valueFormat: props.valueFormat,
    type: props.type

  }
})

</script>
<style lang="scss" scoped>
</style>
