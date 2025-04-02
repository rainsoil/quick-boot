<template>


  <c7-table :table-data="dataList" :columns="columns">
    <!-- 自定义 slot -->
    <template #address="{ row }">
      <span style="color: blue;">{{ row.address }}</span>
    </template>
  </c7-table>
</template>

<script setup lang="ts">

import {ITableColumn} from '../../../packages/components/interface'
import {ref} from "vue";

const columns = ref<Array<ITableColumn>>([
  {
    prop: 'name',
    label: '姓名'
  },
  {
    prop: 'age',
    label: '年龄'
  },
  {
    prop: 'sex',
    label: '性别',
    formatter: (value, row) => {
      return value === 1 ? '男' : '女'
    }
  },
  {
    prop: 'address',
    label: '地址',
    isSlot: true,
    slotName: 'address'

  }
])
const dataList = ref([
  {
    name: '张三',
    age: 18,
    sex: 1,
    address: '北京'
  },
  {
    name: '李四',
    age: 19,
    sex: 0,
    address: '北京'
  },
  {
    name: '王五',
    age: 20,
    sex: 1,
    address: '北京'
  }
])

</script>
