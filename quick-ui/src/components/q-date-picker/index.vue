<template>
  <el-date-picker
      v-model="date"
      :placeholder="placeholder"
      :type="props.type"
      :format="computedFormat"
      :value-format="computedValueFormat"
      :picker-options="pickerOptions"
      @change="handleChange"
  ></el-date-picker>
</template>

<script setup>
import {ref, watch, toRefs, computed} from 'vue';

const props = defineProps({
  modelValue: {
    type: [String, Date, Array],
    default: null
  },
  placeholder: {
    type: String,
    default: '请选择日期'
  },
  type: {
    type: String,
    default: 'date' // 可以是 'date', 'datetime', 'daterange', 'datetimerange', 'year', 'month'
  },
  format: {
    type: String,
    default: undefined
  },
  valueFormat: {
    type: String,
    default: undefined
  },
  join: {
    type: Boolean,
    default: false
  }
});

const emit = defineEmits(['update:modelValue']);

const {modelValue, type, format, valueFormat, join} = toRefs(props);
const date = ref(modelValue.value);

const computedFormat = computed(() => {
  if (format.value !== undefined) {
    return format.value;
  }
  switch (type.value) {
    case 'month':
      return 'YYYY-MM';
    case 'year':
      return 'YYYY';
    case 'datetime':
      return 'YYYY-MM-DD HH:mm';
    default:
      return 'YYYY-MM-DD';
  }
});

const computedValueFormat = computed(() => {
  if (valueFormat.value !== undefined) {
    return valueFormat.value;
  }
  switch (type.value) {
    case 'month':
      return 'YYYY-MM';
    case 'year':
      return 'YYYY';
    case 'datetime':
      return 'YYYY-MM-DD HH:mm';
    default:
      return 'YYYY-MM-DD';
  }
});

const pickerOptions = {
  disabledDate(time) {
    return time.getTime() < Date.now() - 86400000; // 不允许选择今天之前的日期
  }
};
const isRangeType = computed(() => type.value.endsWith('range'));
watch(modelValue, (newVal) => {
  if (isRangeType.value && join.value && typeof newVal === 'string') {
    date.value = newVal.split(',').map(item => item.trim());
  } else {
    date.value = newVal;
  }
});

const handleChange = (value) => {
  let emitValue = value;
  if (join.value && isRangeType.value) {
    emitValue = value.join(',');
  }
  emit('update:modelValue', emitValue);
};
</script>

<style scoped>
/* 这里可以添加一些样式 */
</style>