<!--select 默认值 -->
<template>

  <el-row :gutter="10">
    <el-col :span="8">
      <c7-select :data-list="colors" v-model="choose" :separator="false">

        <template #label="{ label, value }">
          <el-tag :color="value" style="margin-right: 8px" size="small" />
          <span :style="{ color: value }">{{ label }}</span>

        </template>
      </c7-select>
    </el-col>

    <el-col :span="8">
      <span>选中的数据:{{ choose }}</span>
    </el-col>
  </el-row>

</template>
<script setup>
import {ref} from 'vue'
const choose = ref([])

const colors = [
  {
    value: '#E63415',
    label: 'red',
  },
  {
    value: '#FF6600',
    label: 'orange',
  },
  {
    value: '#FFDE0A',
    label: 'yellow',
  },
  {
    value: '#1EC79D',
    label: 'green',
  },
  {
    value: '#14CCCC',
    label: 'cyan',
  },
  {
    value: '#4167F0',
    label: 'blue',
  },
  {
    value: '#6222C9',
    label: 'purple',
  },
]
colors.forEach((color) => {
  choose.value.push(color.value)
})
</script>
