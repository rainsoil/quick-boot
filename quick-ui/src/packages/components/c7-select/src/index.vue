<template>
  <el-select
      v-bind="$attrs"
      v-on="$listeners"
      :model-value="modelValue"
      :loading="loading"
      :remote="remote"
      filterable
      :remote-method="remoteSearchMethod"
      @update:modelValue="handleUpdateModelValue"
  >
    <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
    />
  </el-select>
</template>

<script setup>
import {ref, reactive, watch, onMounted, defineOptions, toRefs} from 'vue';
import dictHook from '../../../hooks/dictHook.ts'

defineOptions({
  name: 'c7Select'
})


const emit = defineEmits([])
const props = defineProps({
  modelValue: {type: [ Array], default: ''},
  url: {type: String, default: ''},
  params: {type: Object, default: () => ({})},
  dictType: {type: String, default: ''},
  dataList: {type: Array, default: () => []},
  remote: {type: Boolean, default: false},

});

const state = reactive({...dictHook()});

let options = ref([]); // 显示的选项
const loading = ref(false); // 加载状态

// 当props发生变化时自动调用fetchData
watch([() => props.url, () => props.dictType, () => props.dataList, () => props.remote], fetchData);

onMounted(() => {
  fetchData();
});

// 请求数据的方法
function fetchData() {

  // let dictType = props.dictType;
  state.getDict(props.dataList, props.dictType, props.url, {}, loading.value).then(res => {
    options.value = res || [];
  });

}


function remoteSearchMethod(query) {
  let params = {
    query: query
  };

  state.getDict(null, null, props.url, params, loading.value).then(res => {
    options.value = res || [];

  });
}


// 处理 v-model 的更新
function handleUpdateModelValue(value) {
  emit('update:modelValue', value);
}
</script>

<style scoped>
/* 可以根据需要进行样式调整 */
</style>
