<template>
  <el-upload
      v-model:file-list="fileList"
      :action="props.uploadUrl"
      :list-type="props.listType"
      :on-preview="handlePictureCardPreview"
      :before-remove="handleDelete"
      :on-success="handleUploadSuccess"
      :before-upload="handleBeforeUpload"
      :on-error="handleError"
      :limit="limit"
      :accept="fileType"
      :headers="headers"
      :class="{ hide: fileList.length >= limit  } "
      :show-file-list="true"
  >
    <el-icon>
      <Plus/>
    </el-icon>
  </el-upload>
  <!-- 上传提示 -->
  <div class="el-upload__tip">
    请上传 xxx材料
    <template v-if="fileSize">
      大小不超过 <b style="color: #f56c6c">{{ fileSize }}MB</b>
    </template>
    <template v-if="fileType">
      格式为 <b style="color: #f56c6c">{{ fileType }}</b>
    </template>
    的文件
  </div>
  <el-dialog v-model="dialogVisible">
    <el-image w-full :src="dialogImageUrl" alt="Preview Image" :preview-src-list="srcList"/>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref, watch} from 'vue'
import {Plus} from '@element-plus/icons-vue'

defineOptions(
    {
      name: 'c7Upload'
    }
)

// 参数
const props = defineProps({
  modelValue: [String],
  //  上传url
  uploadUrl: {
    type: String,
    default: ''
  },
  // 大小限制(MB)
  fileSize: {
    type: Number,
    default: 5,
  },
  // 限制上传文件的类型（后缀），多个用逗号分隔，例如 "jpg,png,gif"
  fileType: {
    type: String,
    default: 'jpg,png'
  },
  // 图片数量限制
  limit: {
    type: Number,
    default: 1,
  },

  // 删除文件的接口
  deleteUrl: {
    type: String,
    default: ''
  },
  headers: {
    type: Object,
    default: () => {
      return {}
    }
  },
  listType: {
    type: String,
    default: 'picture-card'
  },

});

const number = ref(0);
let loadingInstance : any;
const emit = defineEmits();
const uploadList = ref([]);

// 上传前loading加载
function handleBeforeUpload(file : any) {
// 文件类型校验（根据后缀判断）
  if (props.fileType) {
    const allowedTypes = props.fileType.split(',').map(t => t.trim().toLowerCase())
    const fileExtension = file.name.split('.').pop().toLowerCase()
    if (!allowedTypes.includes(fileExtension)) {
      ElMessage.error(`只允许上传 ${allowedTypes.join(', ')} 类型的文件`)
      return false
    }
  }

  if (props.fileSize) {
    const isLt = file.size / 1024 / 1024 < props.fileSize;
    if (!isLt) {
      ElMessage.error(`上传文件大小不能超过 ${props.fileSize} MB!`);
      return false;
    }
  }
  loading("正在上传文件，请稍候...");
  number.value++;
}

import {ElLoading, ElMessage, UploadProps, UploadUserFile} from 'element-plus'


const srcList = ref([])
const fileList = ref<UploadUserFile[]>([])

const dialogImageUrl = ref('')
const dialogVisible = ref(false)

// 删除图片
function handleDelete(file: any) {
  const findex = fileList.value.map(f => f.name).indexOf(file.name);
  let deleteFile = fileList.value[findex].url;
  if (findex > -1 && uploadList.value.length === number.value) {

    console.log(fileList.value, uploadList.value, findex)
    fileList.value.splice(findex, 1);
    emit("update:modelValue", listToString(fileList.value, ","));
    // 这里调用后端接口 去删除
    if (props.deleteUrl) {
      let deleteParams = {
        fileUrl: deleteFile
      }
      // injectService.postRequest(props.deleteUrl, deleteParams, props.headers).then(res => {
      //   if (res.code === 200) {
      //     ElMessage.success("删除成功")
      //   } else {
      //     ElMessage.error("删除失败")
      //   }
      // });
      // console.log("调用接口去删除,参数为:{}", deleteParams)
    }

    return false;
  }
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  srcList.value = []
  dialogImageUrl.value = uploadFile.url!
  srcList.value.push(uploadFile.url!)
  dialogVisible.value = true
}
watch(() => props.modelValue, val => {
  if (val) {
    // 首先将值转为数组
    const list = Array.isArray(val) ? val : props.modelValue.split(",");
    // 然后将数组转为对象数组
    fileList.value = list.map(item => {
      if (typeof item === "string") {
        item = {name: item, url: item};
      }
      return item;
    });
  } else {
    fileList.value = [];
    return [];
  }
}, {deep: true, immediate: true});

// 上传成功回调
function handleUploadSuccess(res, file) {
  if (res.code === 200) {
    uploadList.value.push({
      name: res.data.fileName,
      url: res.data.url,
      fileId: res.data.id
    })
    console.log(uploadList.value, srcList)
    uploadedSuccessfully();
    console.log(uploadList.value, srcList)
  } else {
    number.value--;
    closeLoading();
    ElMessage.error(res.msg);
    // proxy.$refs.imageUpload.handleRemove(file);
    uploadedSuccessfully();
  }
}

// 上传结束处理
function uploadedSuccessfully() {
  console.log("33", uploadList.value, fileList)
  if (number.value > 0 && uploadList.value.length === number.value) {
    console.log("11111")
    fileList.value = fileList.value.filter(f => f.url !== undefined).concat(uploadList.value);
    uploadList.value = [];
    number.value = 0;
    emit("update:modelValue", listToString(fileList.value, ","));

    console.log("44", uploadList.value, fileList)
    closeLoading();
  }
}

// 对象转成指定字符串分隔
function listToString(list, separator) {
  let strs = "";
  separator = separator || ",";
  for (let i in list) {
    if (undefined !== list[i].url && list[i].url.indexOf("blob:") !== 0) {
      strs += list[i].url + separator;
    }
  }
  return strs != "" ? strs.substr(0, strs.length - 1) : "";
}

// 打开遮罩层
function loading(content) {
  loadingInstance = ElLoading.service({
    lock: true,
    text: content,
    background: "rgba(0, 0, 0, 0.7)",
  })
}

function  handleError(error){
  console.log(error)
}

// 关闭遮罩层
function closeLoading() {
  loadingInstance.close();
}
</script>
