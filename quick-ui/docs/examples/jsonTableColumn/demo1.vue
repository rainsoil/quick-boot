<!-- demo1.vue -->
<template>
  <div>
    <h2>JSON Table Column 示例</h2>
    <el-table :data="tableData">
      <c7-json-table-column
          :columns="columns"
      >
        <template #slotTest2="{ row, index }">
          <span>id=>{{ row.id }}</span>
        </template>
        <!-- 自定义插槽列内容 -->
        <template #slotTest="{ row, index }">
          <el-button @click="handleCustomAction(row, index)">操作{{ index }}</el-button>
        </template>


      </c7-json-table-column>
    </el-table>

  </div>
</template>

<script setup>
import {ref} from 'vue'
import {c7JsonTableColumn} from 'c7-plus'

// 示例数据
const tableData = ref([
  {
    id: 1,
    name: 'Vue',
    tags: '框架',
    avatar: 'https://e-assets.gitee.com/gitee-community-web/_next/static/media/person.9b9c6c64.png',
    images: 'https://e-assets.gitee.com/gitee-community-web/_next/static/media/person.9b9c6c64.png,https://e-assets.gitee.com/gitee-community-web/_next/static/media/person.9b9c6c64.png',
    //   'https://via.placeholder.com/40',
    //   'https://via.placeholder.com/40/FF0000'
    // ]
  },
  {
    id: 2,
    name: 'React',
    tags: '库',
    avatar: 'https://e-assets.gitee.com/gitee-community-web/_next/static/media/person.9b9c6c64.png',
    images: 'https://e-assets.gitee.com/gitee-community-web/_next/static/media/person.9b9c6c64.png,https://e-assets.gitee.com/gitee-community-web/_next/static/media/person.9b9c6c64.png',
    //   'https://via.placeholder.com/40/00FF00',
    //   'https://via.placeholder.com/40/0000FF'
    // ]
  }
])

// 列配置
const columns = [
  {
    columnType: "text",
    label: 'ID',
    prop: 'id',
    width: 80,
    align: 'center'
  },
  {
    columnType: "text",
    label: '名称',
    prop: 'name',
    align: 'left'
  },
  {
    columnType: "tag",
    label: '标签',
    prop: 'tags',
    dictList: [
      {label: '框架', value: '框架', elTagType: 'success'},
      {label: '库', value: '库', elTagType: 'warning'},
      {label: '前端', value: '前端', elTagType: 'primary'}
    ]
  },
  {
    columnType: "image",
    label: '头像',
    prop: 'avatar',
    width: 80
  },
  {
    columnType: "image",
    label: '多图展示',
    prop: 'images',
    width: 150
  },
  {
    columnType: "slot",
    label: '自定义操作',
    width: 120,
    slotName: 'slotTest'
  },
  {
    columnType: "slot",
    label: '自定义卡槽',
    width: 120,
    slotName: 'slotTest2'
  }
]

// 自定义操作处理
const handleCustomAction = (row, index) => {
  alert(`点击了第${index}行操作按钮，数据：${JSON.stringify(row)}`)
}
</script>

<style scoped>
/* 添加一些示例样式 */
h2 {
  margin-bottom: 15px;
  color: #333;
}
</style>
