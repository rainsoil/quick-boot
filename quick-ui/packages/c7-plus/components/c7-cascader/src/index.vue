<template>
  <el-cascader
      v-bind="$attrs"
      v-model="selectedValue"
      :options="options"
      :props="props2"

  >

  </el-cascader>
</template>

<script setup>
import {ref, watch, onMounted, defineOptions, computed} from 'vue';
import {useFetchOptions} from '../../../hooks/c7Hook.ts'
import {jsonGet} from "../../../utils/utils.ts";

defineOptions({
  name: 'c7Cascader'
})
const props = defineProps({
  // v-model 绑定值
  modelValue: {
    type: [Array, String, Number],
    default: () => []
  },
  // 异步获取数据的函数
  fetchData: {type: Function, default: null},
  // 数据格式化回调，接收原始 list，返回格式化后的 list
  dataFormatter: {type: Function, default: null},
  // el-option 的 label 字段名
  labelKey: {type: String, default: 'label'},
  // el-option 的 value 字段名
  valueKey: {type: String, default: 'value'},
  // 异步结果中列表数据所在的路径
  resultKey: {type: String, default: 'data'},
  // 静态数据列表，当 fetchData 为 null 时使用
  dataList: {type: Array, default: () => []},
  // 异步获取数据时的额外参数
  fetchParams: {type: Object, default: () => ({})},


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
  parentNodeFetchFunction: {
    type: Function, default: null
  },
  checkStrictly: {
    type: Boolean,
    default: false
  },
  rootParentId: {

    type: String,
    default: -1
  }
});

const {options, loading, fetchAndUpdate} = useFetchOptions({
  fetchData: props.fetchData,
  fetchParams: props.fetchParams,
  resultKey: props.resultKey,
  dataFormatter: props.dataFormatter,
  dataList: props.dataList,
  lazy: props.lazy,

})


const props2 = {
  lazy: props.lazy,
  checkStrictly: props.checkStrictly,
  lazyLoad(node, resolve) {
    const {level} = node
    console.log("node", node, node.value)
    // loadLoadOptions(null == node.value ? -1 : node.value).then(res => {
    //   let resData = res.data || [];
    //   resolve(resData)
    // });
    console.log("node", (node.value ? node.value : null))
    props.fetchData(node.value ? node.value : props.rootParentId).then(res => {
      // options.value = res.data.data;
      let resData = res.data.data || [];
      resolve(resData)
    });
  },
}
// 用于向父组件更新绑定值
const emit = defineEmits(['update:modelValue']);
// 组件挂载后自动加载数据（仅在非远程且 autoLoad=true 时）
onMounted(() => {

  // if (!props.lazy) {
  //   fetchAndUpdate('')
  //
  //
  // } else {
  loadOptions(props.modelValue);
  // }
})

const  loadOptions = async  (value) => {
  if (fetchAndUpdate) {
    await  fetchAndUpdate('')
    if (loading.value){
      console.log("options", options.value, options)
    }
  }
  if (!loading.value){
    if (props.resultType === 1) {
      selectedValue.value = value;
    } else if (props.resultType === 2 && typeof value === 'string') {
      selectedValue.value = value.split(',')
    } else if (props.resultType === 3 && typeof value === 'string') {
      // 绑定为 数组的最后一个元素,分为两种情况  非懒加载模式下,需要根据绑定的值 遍历出来所有节点 获取整个父级节点数据, 懒加载模式下 需要调用函数获取
      if (!props.lazy) {
        const path = findPath(options.value, value);
        console.log("path", path, value, options.value)
        if (path.length) {
          selectedValue.value = path;
        }
      } else {
        console.log("懒加载", props.lazy)
        props.parentNodeFetchFunction(value).then(res => {
          selectedValue.value = jsonGet(res, props.resultKey, [])
          console.log("value", selectedValue.value, res, props.resultKey, jsonGet(res, props.resultKey, []))
        });
      }
    }
  }


}

//
// // 计算属性：双向绑定处理，内部维护数组/字符串之间的相互转换
// const selectedValue = computed({
//   get: () => {
//     let value = props.modelValue
//     // if (props.resultType === 2 && typeof value === 'string') {
//     //   value = value.split(',')
//     // } else if (props.resultType === 3 && typeof value === 'string') {
//     //   // 绑定为 数组的最后一个元素,分为两种情况  非懒加载模式下,需要根据绑定的值 遍历出来所有节点 获取整个父级节点数据, 懒加载模式下 需要调用函数获取
//     //   if (!props.lazy) {
//     //     const path = findPath(options.value, value);
//     //     console.log("path", path)
//     //     if (path.length) {
//     //       value = path;
//     //     }
//     //   } else {
//     //     console.log("懒加载", props.lazy)
//     //     props.parentNodeFetchFunction(value).then(res => {
//     //       value = jsonGet(res, props.resultKey, [])
//     //       console.log("value", value, res, props.resultKey)
//     //     });
//     //   }
//     //   return value;
//     // }
//     return value
//   },
//   set: (value) => {
//     console.log(value, props.resultType, props.resultType === 3)
//     let payload = value
//     if (props.resultType === 2) {
//       payload = value.join(',')
//     } else if (props.resultType === 3) {
//       payload = (value && value.length ? value[value.length - 1] : '')
//     }
//     /**
//      * 结果类型：
//      * 1：绑定值为原生数组（如：[1,2,3]）
//      * 2：绑定值为逗号分隔字符串（如："1,2,3"）
//      * 3：绑定值为数组的最后一个元素（如：3），反显时需根据此值查找完整路径
//      */
//     emit('update:modelValue', payload)
//   }
// })
const selectedValue = ref([]);
watch(selectedValue, (value) => {
  console.log(value, props.resultType, props.resultType === 3)
  let payload = value
  if (props.resultType === 2) {
    payload = value.join(',')
  } else if (props.resultType === 3) {
    payload = (value && value.length ? value[value.length - 1] : '')
  }
  /**
   * 结果类型：
   * 1：绑定值为原生数组（如：[1,2,3]）
   * 2：绑定值为逗号分隔字符串（如："1,2,3"）
   * 3：绑定值为数组的最后一个元素（如：3），反显时需根据此值查找完整路径
   */
  emit('update:modelValue', payload)
})

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
</script>
