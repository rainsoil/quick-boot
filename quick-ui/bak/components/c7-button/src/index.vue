<!-- 对按钮的封装-->
<template>


  <el-button v-bind="$attrs" v-if="!clickFunction" :icon="icon" :type="type" :plain="plain">

    <div v-if="$slots.default">
      <slot></slot>
    </div>

    <div v-else>
      {{ label }}
    </div>
  </el-button>

  <el-button v-bind="$attrs" v-if="clickFunction" @click="buttonClick" :loading="loading" :type="type" :icon="icon"
             :plain="plain">

    <div v-if="$slots.default">
      <slot></slot>
    </div>
    <div v-else>
      {{ label }}
    </div>
  </el-button>

</template>

<script setup >
import {ref, defineOptions, onMounted} from 'vue';
import pkg from 'lodash';
import {ElMessage, ElMessageBox, FormInstance} from 'element-plus'
import {jsonGet} from "../../../utils/utils.ts";

const {debounce} = pkg;


defineOptions({
  name: 'c7Button'
})
// 成功之后的回调函数
const emit = defineEmits(["successCallback", "errorCallback"])

const props = defineProps({
  type: {
    type: String,
    default: 'primary'
  },
  // 类型 (新增/修改/删除/查询/刷新/普通)
  btnType: {
    type: String,
    default: ''
  },
  // 是否为朴素按钮
  plain: {
    type: Boolean,
    default: true
  },
  // 按钮内容
  label: {
    type: String,
    default: ''
  },
  // promise
  clickFunction: {type: Function, default: null},
// 是否弹出确认框
  confirm: {
    type: Boolean,
    default: false,
  },
  // 确认框提示内容
  confirmMessage: {
    type: String,
    default: "",
  },
  // 是否开启成功之后回调
  isSuccessCallback: {
    type: Boolean,
    default: false,
  },

  // 成功之后提示信息(当isSuccessCallback为false的时候,才启动)
  successMessage: {
    type: String,
    default: "",
  },
  //是否开启失败回调
  isErrorCallback: {
    type: Boolean,
    default: false,
  },
  //检查接口调用是否成功 checkSuccess
  checkSuccess: {
    type: Function,
    default: (res) => res.code === 200,
  },
  // 错误提示类型(msg:直接错误提示,res:从结果中获取)
  errorMessageType: {
    type: String,
    default: "res",
  },
  // 错误提示信息(当isErrorCallback为false的时候,才启动)
  errorMessage: {
    type: String,
    default: "msg",
  },
  // 是否进行参数校验
  validate:
      {
        type: Boolean,
        default: false,
      },
  // 参数校验的ref
  validateRef: {
    type: Object,
    default: null,
  }


})
const icon = ref('');
const plain = ref(props.plain);

const type = ref(props.type)
const label = ref(props.label);
onMounted(() => {
  if (props.btnType) {
    plain.value = true;

    if (props.btnType === 'add') {
      icon.value = 'Plus'
      label.value = '新增'
      type.value = 'primary'
    } else if (props.btnType === 'edit') {
      icon.value = 'Edit'
      label.value = '修改'
      type.value = 'success'
    } else if (props.btnType === 'delete') {
      icon.value = 'Delete'
      label.value = '删除'
      type.value = 'danger'
    } else if (props.btnType === 'query') {
      icon.value = 'Search'
      label.value = '查询'
      plain.value = false;
    } else if (props.btnType === 'refresh') {
      icon.value = 'Refresh'
      label.value = '重置'
      plain.value = false;
      type.value = ''
    } else if (props.btnType === 'upload') {
      icon.value = 'Upload'
      label.value = '上传'
      type.value = 'info'
    } else if (props.btnType === 'download') {
      icon.value = 'Download'
      label.value = '下载'
      type.value = 'warning'
    } else if (props.btnType === 'submit') {
      label.value = '确定'
      type.value = 'primary'
    } else if (props.btnType === 'cancel') {
      label.value = '取消'
      type.value = 'info'
    }
  }

});
let isProcessing = ref(false); // 用于防止重复点击

const buttonClick = debounce(() => {
  if (isProcessing.value) return; // 如果已经有请求在处理，直接返回

  if (props.confirm) {
    confirm();
  } else {
    buttonClickHandler();
  }
}, 300); // 假设防抖间隔为300毫秒
const confirm = () => {
  ElMessageBox.confirm(props.confirmMessage, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    buttonClickHandler();
  }).catch(() => {
    // 确认框取消时也需要重置状态
    isProcessing.value = false;
  });
}
const loading = ref(false)

// 按钮点击事件
async function buttonClickHandler() {
  loading.value = true
  try {
    if (props.validate && props.validateRef) {
      if (!props.validateRef) return

      props.validateRef.validate((valid, fields) => {
        if (valid) {
          props.clickFunction()
        } else {
          emit('errorCallback', fields);
        }
      })

    } else {
      const result = await props.clickFunction()
      if (result) {
        if (props.checkSuccess(result)) {

          // 成功回调
          if (props.isSuccessCallback) {
            emit('successCallback', result);
          } else {
            ElMessage.success(props.successMessage);

          }
        } else {
          let msg = '';
          if (props.errorMessageType === 'res') {
            msg = jsonGet(result, props.errorMessage);
          } else if (props.errorMessageType === 'msg') {
            msg = props.errorMessage;
          }
          if (props.isErrorCallback) {
            emit('errorCallback', result, msg);
          } else {
            ElMessage.error(msg);
          }
        }
      }
    }


  } catch (err) {
    ElMessage.error(err);
  } finally {
    loading.value = false                      // 结束加载
  }


}

</script>

