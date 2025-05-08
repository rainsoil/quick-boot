<template>
  <el-cascader
      v-bind="$attrs"
      v-model="internalValue"
      :options="options"
      :props="props2"

  >

  </el-cascader>
</template>

<script setup>
import {ref, watch, onMounted, defineOptions} from 'vue';
import {injectService} from "../../../service/injectService.ts";

defineOptions({
  name: 'c7Cascader'
})
const props = defineProps({
  // v-model 绑定值
  modelValue: {
    type: [Array, String, Number],
    default: () => []
  },
  // 当不为空时，调用该 API 接口获取级联数据
  api: {
    type: String,
    default: ''
  },
  // 接口调用时的参数
  params: {
    type: Object,
    default: () => ({})
  },
  // 如果没有使用 API，则可以直接传入 dataList
  dataList: {
    type: Array,
    default: () => []
  },
  /**
   * 结果类型：
   * 1：绑定值为原生数组（如：[1,2,3]）
   * 2：绑定值为逗号分隔字符串（如："1,2,3"）
   * 3：绑定值为数组的最后一个元素（如：3），反显时需根据此值查找完整路径
   */
  resultType: {
    type: Number,
    default: 1
  },
  // 懒加载：当为 true 时，开启动态加载下级节点
  lazy: {
    type: Boolean,
    default: false
  },
  /**
   * 当 lazy 为 true 且 resultType = 3 时，反显可能无法匹配上级数据，
   * 此时可传入 parent_url，根据当前节点获取所有父级节点数据
   */
  parentUrl: {
    type: String,
    default: ''
  },
  checkStrictly: {
    type: Boolean,
    default: false
  }
});

const props2 = {
  lazy: props.lazy,
  checkStrictly: props.checkStrictly,
  lazyLoad(node, resolve) {
    const {level} = node
    console.log("node", node)
    loadLoadOptions(null == node.value ? -1 : node.value).then(res => {
      let resData = res.data || [];
      resolve(resData)
    });
  },
}
// 用于向父组件更新绑定值
const emit = defineEmits(['update:modelValue']);

// 存储级联数据（options）
const options = ref([]);
// 内部绑定值（el-cascader 的 v-model 绑定值），数据格式始终为数组（代表完整路径）
const internalValue = ref([]);


/**
 * 加载远程级联数据
 */
function loadLoadOptions(parentId) {

  return injectService.getRequest(props.api, {
    parentId: parentId
  });
}

/**
 * 递归查找指定节点的完整路径（返回由各级节点 value 组成的数组）
 * @param {Array} nodes - 当前级联数据
 * @param {*} target - 目标叶子节点的值
 * @returns {Array} 完整路径数组，若未找到则返回空数组
 */
function findPath(nodes, target) {
  for (const node of nodes) {
    if (node.value === target) {
      return [node.value];
    }
    if (node.children) {
      const childPath = findPath(node.children, target);
      if (childPath.length) {
        return [node.value, ...childPath];
      }
    }
  }
  return [];
}


function loadOptions(value) {
  if (!props.lazy && props.dataList && props.dataList.length > 0) {
    options.value = props.dataList;
  } else if (props.api && !props.lazy) {
    injectService.postRequest(props.api, props.params).then(res => {
      options.value = res.data || [];
    });
  }
  // 判断如果value的类型为string的时候,判断不为空, 如果为数组的时候 判断数组不为空
  if (typeof value === 'string') {
    if (value.trim() === '') {
      return
    }
  } else if (Array.isArray(value)) {
    console.log(value)
    if (value.length === 0) {
      return
    }
  }
  console.log("value", value)
  // 根据resultType判断
  if (props.resultType == 1) {
    // 绑定值为原生数组（如：[1,2,3]）
    internalValue.value = value;
  } else if (props.resultType == 2) {
    // 绑定值为逗号分隔字符串（如："1,2,3"）
    internalValue.value = value.split(',');
  } else if (props.resultType == 3) {
    if (props.lazy) {
      // 绑定值为数组的最后一个元素（如：3），反显时需根据此值查找完整路径
      injectService.getRequest(props.parentUrl, {
        value: value
      }).then(res => {
        internalValue.value = res.data || [];
      });
    } else {
      const path = findPath(options.value, props.modelValue);
      if (path.length) {
        internalValue.value = path;
      }
    }
  }
}


onMounted(() => {
  loadOptions(props.modelValue)
});


// 当 internalValue 变化时，根据 resultType 转换后更新父组件绑定值
watch(internalValue, (newVal) => {
  console.log("newVal", props.resultType, newVal, props.resultType == 2)
  if (props.resultType == 1) {
    emit('update:modelValue', newVal);
  } else if (props.resultType == 2) {
    console.log("newVal2", props.resultType, newVal.join(','))
    emit('update:modelValue', newVal.join(','));
  } else if (props.resultType == 3) {
    emit('update:modelValue', newVal && newVal.length ? newVal[newVal.length - 1] : '');
  }
});
</script>
