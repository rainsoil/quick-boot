<template>
  <div>
    <!-- 单选按钮组 -->
    <el-radio-group
        v-model="innerValue"
        @change="handleChange"
    >
      <el-radio
          v-for="item in options"
          :key="item.value"
          :label="item.value"
      >
        {{ item.label }}
      </el-radio>
    </el-radio-group>
  </div>
</template>

<script setup>
import {defineOptions, ref, watch, onMounted, computed} from 'vue';
import {useDict} from '../../../hooks/dictHook.ts';

// 组件名称
defineOptions({
  name: 'c7Radio'
});

// 引入字典或远程数据加载方法
const {getDict2} = useDict();

// 定义更新事件
const emit = defineEmits(['update:modelValue', 'change']);

// 接收属性
const props = defineProps({
  // 支持单选值类型，可为字符串或数字
  modelValue: {type: [String, Number], default: ''},
  url: {type: String, default: ''},
  params: {type: Object, default: () => ({})},
  dictType: {type: String, default: ''},
  dataList: {type: Array, default: () => []}
});

// 内部绑定值
const innerValue = computed({
  get() {
    return props.modelValue;
  },
  set(val) {
    emit('update:modelValue', val);
  }
});

// 选项列表和加载状态
const options = ref([]);
const loading = ref(false);

// 拉取数据函数
function fetchData() {
  getDict2(props.dataList, props.dictType, props.url, props.params, loading.value)
      .then(res => {
        options.value = res || [];
      });
}

// 当 props 变化时重新加载
watch(
    [() => props.url, () => props.dictType, () => props.dataList],
    fetchData,
    {immediate: true}
);

// 处理选中后触发 change 事件
function handleChange(val) {
  // 通过 innerValue 的 setter 已经发出更新
  // 额外需要时可在此执行其他逻辑
  emit('change', val);
}
</script>
