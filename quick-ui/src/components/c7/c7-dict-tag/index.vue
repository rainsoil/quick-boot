<template>
  <div>
    <template v-for="(item, index) in options">

      <template v-if="values.includes(item.value)">
        <span
            v-if="(item.elTagType == 'default' || item.elTagType == '') && (item.elTagClass == '' || item.elTagClass == null)"
            :key="item.value"
            :index="index"
            :class="item.elTagClass"
        >{{ item.label + " " }}</span>
        <el-tag
            v-else
            :disable-transitions="true"
            :key="item.value + ''"
            :index="index"
            :type="item.elTagType === 'primary' ? '' : item.elTagType"
            :class="item.elTagClass"
        >{{ item.label + " " }}
        </el-tag>
      </template>
    </template>
    <template v-if="unmatch && showValue">
      {{ unmatchArray | handleArray }}
    </template>
  </div>
</template>

<script setup>
import {computed, defineOptions, defineProps, ref} from 'vue';

defineOptions({
  name: 'c7Tag'
});
// 记录未匹配的项
const unmatchArray = ref([]);

const props = defineProps({
  // 数据
  options: {
    type: Array,
    default: null,
  },
  // 当前的值
  modelValue: [Number, String, Array],
  // 当未找到匹配的数据时，显示value
  showValue: {
    type: Boolean,
    default: true,
  },
  separator: {
    type: String,
    default: ",",
  },
  // 大小 'large' | 'default' | 'small'

  size: {
    type: String,
    default: 'default'
  }
});

const values = computed(() => {
  if (props.modelValue === null || typeof props.modelValue === 'undefined' || props.modelValue === '') return [];
  return Array.isArray(props.modelValue) ? props.modelValue.map(item => '' + item) : String(props.modelValue).split(props.separator);
});

const unmatch = computed(() => {
  console.log(props.options)
  unmatchArray.value = [];
  // 没有value不显示
  if (props.modelValue === null || typeof props.modelValue === 'undefined' || props.modelValue === '' || props.options.length === 0) return false
  // 传入值为数组
  let unmatch = false // 添加一个标志来判断是否有未匹配项
  values.value.forEach(item => {
    if (!props.options.some(v => v.value === item)) {
      unmatchArray.value.push(item)
      unmatch = true // 如果有未匹配项，将标志设置为true
    }
  })
  return unmatch // 返回标志的值
});

function handleArray(array) {
  if (array.length === 0) return "";
  return array.reduce((pre, cur) => {
    return pre + " " + cur;
  });
}
</script>

<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>
