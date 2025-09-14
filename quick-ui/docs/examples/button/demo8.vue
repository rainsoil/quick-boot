<template>
  <div>
    <!-- 基础按钮 -->
    <c7-button>基础按钮</c7-button>

    <!-- 业务类型按钮 -->
    <c7-button btn-type="add" :clickFunction="handleAdd"></c7-button>
    <c7-button btn-type="edit" :clickFunction="handleEdit"></c7-button>
    <c7-button btn-type="delete" :clickFunction="handleDelete" :confirm="true" confirmMessage="确认删除吗？"></c7-button>

    <!-- 自定义按钮 -->
    <c7-button
        type="warning"
        :clickFunction="handleCustom"
        :isSuccessCallback="true"
        @successCallback="onSuccess"
    >
      自定义按钮
    </c7-button>

    <!-- 表单验证按钮 -->
    <el-form ref="formRef" :model="form" :rules="rules">
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <c7-button
          btn-type="submit"
          :clickFunction="handleSubmit"
          :validate="true"
          :validateRef="formRef"
      ></c7-button>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
const formRef = ref()
const form = reactive({
  name: ''
})

const rules = {
  name: [
    { required: true, message: '请输入名称', trigger: 'blur' }
  ]
}

const handleAdd = async () => {
  // 模拟异步操作
  await new Promise(resolve => setTimeout(resolve, 1000))
  return { code: 200, message: '添加成功' }
}

const handleEdit = async () => {
  await new Promise(resolve => setTimeout(resolve, 1000))
  return { code: 200, message: '修改成功' }
}

const handleDelete = async () => {
  await new Promise(resolve => setTimeout(resolve, 1000))
  return { code: 200, message: '删除成功' }
}

const handleCustom = async () => {
  await new Promise(resolve => setTimeout(resolve, 1000))
  return { code: 200, data: { id: 1, name: 'test' } }
}

const handleSubmit = async () => {
  await new Promise(resolve => setTimeout(resolve, 1000))
  return { code: 200, message: '提交成功' }
}

const onSuccess = (result) => {
  console.log('操作成功:', result)
}
</script>
