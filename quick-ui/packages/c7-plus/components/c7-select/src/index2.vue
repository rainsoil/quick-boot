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
import {ref, reactive, watch, onMounted, defineOptions} from 'vue';
import injectService from '../../../service/injectService.js'

defineOptions({
  name: 'c7Select'
})
const emit = defineEmits([])
const props = defineProps({
  modelValue: {type: [String, Number, Array], default: ''},
  url: {type: String, default: ''},
  params: {type: Object, default: () => ({})},
  dictType: {type: String, default: ''},
  dataList: {type: Array, default: () => []},
  remote: {type: Boolean, default: false},

});

const options = ref([]); // 显示的选项
const loading = ref(false); // 加载状态

// 当props发生变化时自动调用fetchData
watch([() => props.url, () => props.dictType, () => props.dataList, () => props.remote], fetchData);

onMounted(fetchData);

// 请求数据的方法
function fetchData() {
  if (props.remote && props.url) {
    // 如果remote为true，使用api和params来进行远程请求
    remoteSearchMethod();
  } else if (props.dataList && props.dataList.length) {
    // 如果dataList有值，直接使用
    options.value = props.dataList;
  } else if (props.url) {

    getDataFromApi(props.params)
  } else if (props.dictType) {
    // 如果dictType有值，从getDict获取数据
    options.value = injectService.getDictByType(props.dictType) || [];
  }
}

// 处理dataList变化的情况
function handleDataListChange() {
  if (props.dataList && props.dataList.length) {
    options.value = props.dataList;
  }
}

function remoteSearchMethod(query) {
  props.params = query;
  getDataFromApi({
    "query": query
  })
}

// 自定义远程搜索方法
function remoteSearch(query) {
  if (props.remote && props.url) {
    props.params = query;
    fetchData();
  }
}

function getDataFromApi(param) {
  // 如果api和params都有值，发请求
  loading.value = true;
  try {
    injectService.postRequest(props.url, param).then(res => {
      let data = res.data;
      options.value = data || [];
    });

  } catch (error) {
    console.error('请求失败:', error);
  } finally {
    loading.value = false;
  }
}

// 处理 v-model 的更新
function handleUpdateModelValue(value) {
  emit('update:modelValue', value);
}
</script>

<style scoped>
/* 可以根据需要进行样式调整 */
</style>
