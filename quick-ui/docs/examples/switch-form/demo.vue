<template>

  <el-button type="primary" @click="showIndex = 'list'">列表</el-button>
  <el-button type="danger" @click="showIndex = 'form'">表单</el-button>
  <div></div>
  <c7-switch-form
      :showIndexs="showIndexs"
      v-model="showIndex"
      @update:modelValue="console.log($event)"
  >

    <template #list>
      <div>
        列表
      </div>
    </template>

    <template #form>
      <div>
        表单
      </div>
    </template>
  </c7-switch-form>
</template>
<script setup>
import {ref} from 'vue'

const showIndexs = ref([
  {
    title: '列表',
    name: 'list',

  },
  {
    title: '表单',
    name: 'form',
    header: true,
    closeIndex: 'list'
  }
])
const showIndex = ref('list')

</script>
