<template>
  <el-date-picker
      v-bind="$attrs"
      :type="type"
      :format="computedFormat"
      :value-format="computedValueFormat"
      v-model="internalValue"
      @update:modelValue="handleValueChange"
  >
    <slot></slot> <!-- 继承插槽 -->
  </el-date-picker>
</template>

<script setup>
import {ref, computed, watch, toRefs, onMounted, defineOptions} from 'vue';

defineOptions({
  name: 'c7DatePicker'
})
// 定义属性
const props = defineProps({
  modelValue: {
    type: [String, Array, Date],
    default: '',
  },
  type: {
    type: String,
    default: 'date',
  },
  format: {
    type: String,
    default: '',
  },
  valueFormat: {
    type: String,
    default: '',
  },
  rangeMerge: {
    type: Boolean,
    default: true,
  },
});

// 定义事件
const emit = defineEmits(['update:modelValue']);

// 解构 props，保持响应性
const {type, format, valueFormat, modelValue, rangeMerge} = toRefs(props);

// 默认格式映射表，根据不同的 type 设置默认的 format 与 valueFormat
const defaultFormatMap = {
  date: 'YYYY-MM-DD',
  daterange: 'YYYY-MM-DD',
  year: 'YYYY',
  month: 'YYYY-MM',
  datetime: 'YYYY-MM-DD HH:mm:ss',
  datetimerange: 'YYYY-MM-DD HH:mm:ss'
};

// 计算后的 format：如果外部传入则使用传入值，否则使用映射中的默认值
const computedFormat = computed(() => {
  return format.value || defaultFormatMap[type.value] || 'YYYY-MM-DD';
});

// 同理计算 value-format
const computedValueFormat = computed(() => {
  return valueFormat.value || defaultFormatMap[type.value] || 'YYYY-MM-DD';
});

// 用 internalValue 支持 v-model 的双向绑定
const internalValue = ref(modelValue.value);

// 同步外部 modelValue 变化
watch(
    () => modelValue.value,
    (newVal) => {
      console.log("newVal", type, newVal)
      // 当 modelValue 为逗号分隔字符串时，转换为数组
      if (type.value === 'daterange' && typeof newVal === 'string' && rangeMerge.value) {

        const dateRange = newVal.split(',');
        internalValue.value = dateRange.length === 2 ? dateRange : [];
      } else {
        internalValue.value = newVal;
      }

    },
    {immediate: true}
);

// 当值变化时，根据 type 判断是否需要拼接（如 daterange 或 datetimerange 类型）
// 对于 daterange 类型，保持数组格式，不拼接成字符串
const handleValueChange = (newValue) => {
  if (
      (type.value === 'daterange' || type.value === 'datetimerange') &&
      Array.isArray(newValue) && rangeMerge.value

  ) {
    console.log("newValue2", rangeMerge.value, newValue)
    emit('update:modelValue', newValue.join(","));
  } else {
    console.log("newValue", newValue)
    emit('update:modelValue', newValue);
  }
};
</script>
