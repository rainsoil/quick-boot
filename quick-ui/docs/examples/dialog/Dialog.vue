<template>

  <el-button @click="open">打开弹框</el-button>

  <c7-dialog v-model="visible" :footer="false" title="标题" >

    <span>1111</span>
  </c7-dialog>
  <el-button @click="open2">打开弹框2</el-button>

  <c7-dialog v-model="visible2" :footer="true" title="标题"  @submit="submit" @close="cancel" >

    <span>1111</span>
  </c7-dialog>

</template>

<script setup>
import  {ref} from  'vue'
const  visible = ref(false)
const  open = () => {
  visible.value = true
}

const  visible2 = ref(false)

const  open2 = () => {
  visible2.value = true
}
const  submit =() =>{
  alert("提交")
}
const  cancel =() =>{
  alert("取消")
  visible2.value = false
}

</script>
