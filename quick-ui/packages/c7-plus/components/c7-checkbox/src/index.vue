<template>
  <div>
    <!-- 全选/全不选复选框：仅当 indeterminate 为 true 时显示 -->
    <el-checkbox
        v-if="indeterminate"
        v-model="allChecked"
        :indeterminate="isIndeterminate"
        style="margin-bottom: 8px;"
    >
      全选
    </el-checkbox>
    <!-- 复选框组 -->
    <el-checkbox-group
        v-model="checkList"
        @update:modelValue="handleUpdateModelValue"
    >
      <el-checkbox
          v-for="item in options"
          :key="item.value"
          :value="item.value"
          :label="item.label"
      >
      </el-checkbox>
    </el-checkbox-group>
  </div>
</template>

<script setup>
import { defineOptions, reactive, ref, watch, onMounted, computed } from "vue";
import {dictHook} from '../../../hooks/dictHook.ts'

defineOptions({
  name: 'c7Checkbox'
});

// 定义 update:modelValue 事件
const emit = defineEmits(['update:modelValue']);

// 支持 modelValue 为数组或逗号分隔的字符串
const props = defineProps({
  modelValue: { type: [Array, String], default: () => [] },
  url: { type: String, default: '' },
  params: { type: Object, default: () => ({}) },
  dictType: { type: String, default: '' },
  dataList: { type: Array, default: () => [] },
  // 当 indeterminate 为 true 时，显示全选/全不选复选框
  indeterminate: { type: Boolean, default: false },
  // 当 rangeMerge 为 true 时，选中结果用逗号分隔
  rangeMerge: {type: Boolean, default: true},

});

const state = reactive({...dictHook()});

const checkList = ref([]); // 内部存储当前选中的值
let options = ref([]);    // 用于显示的选项
const loading = ref(false); // 加载状态

// 监听 url、dictType、dataList 及 remote 变化时重新加载数据
watch(
    [() => props.url, () => props.dictType, () => props.dataList],
    fetchData
);

onMounted(() => {
  // 如果父组件有绑定值，初始化默认选中
  if (props.modelValue) {
    if (props.rangeMerge && typeof props.modelValue === 'string') {
      checkList.value = props.modelValue.split(",");
    } else if (Array.isArray(props.modelValue)) {
      checkList.value = props.modelValue;
    }
  }
  fetchData();
});

/**
 * 加载数据：通过 dictHook 获取字典或 API 数据
 */
function fetchData() {
  dictHook().getDict2(props.dataList, props.dictType, props.url, {}, loading.value).then(
      (res) => {
        options.value = res || [];
      }
  );
}

/**
 * 处理复选框组变化：
 * 当 rangeMerge 为 true 时，将数组用逗号连接后发出更新事件
 */
function handleUpdateModelValue(value) {
  if (props.rangeMerge) {
    emit('update:modelValue', value.join(","));
  } else {
    emit('update:modelValue', value);
  }
}

/**
 * 计算属性：全选状态
 * - 如果所有选项均选中，则 allChecked 为 true
 */
const allChecked = computed({
  get() {
    return options.value.length > 0 && checkList.value.length === options.value.length;
  },
  set(val) {
    if (val) {
      checkList.value = options.value.map(item => item.value); // 全选所有
    } else {
      checkList.value = []; // 全不选
    }
    handleUpdateModelValue(checkList.value);
  }
});

/**
 * 计算属性：部分选中状态，用于控制全选复选框的 indeterminate 状态
 */
const isIndeterminate = computed(() => {
  const selected = checkList.value.length;
  return selected > 0 && selected < options.value.length;
});
</script>
