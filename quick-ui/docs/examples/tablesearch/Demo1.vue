<template>


  <c7-table-search :columns="columns" v-model="form" @add-btn-handle="addOrUpdateHandle" @deleteHandle="deleteHandler" >

    <template #customSlot="{ item, modelValue }">
      <!-- 可使用 ElementPlus 的组件扩展 -->
      <el-input v-model="form.customSlot" placeholder="自定义扩展输入框"></el-input>
    </template>

  </c7-table-search>


  <span>绑定的值:{{ form }}</span>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {c7TableSearch} from "c7-plus";
import {IColumn, IColumnEnum} from "c7-plus";

const sexDict = ref([
  {
    value: '1',
    label: '男'
  },
  {
    value: '2',
    label: '女'
  }
])

const placeOfResidence = ref([
  {
    value: '1',
    label: '北京市',
    children: [
      {
        value: '11',
        label: '北京市',
        children: [
          {
            value: '111',
            label: '东城区'
          },
          {
            value: '112',
            label: '西城区'
          },
          {
            value: '113',
            label: '崇文区'
          },
          {
            value: '114',
            label: '宣武区'
          },

        ]
      }
    ]
  },
  {
    value: '2',
    label: '天津市'
    ,
    children: [
      {
        value: '21',
        label: '天津市',
        children: [
          {
            value: '221',
            label: '和平区'
          },

        ]
      }
    ]
  }
])


const columns = ref<Array<IColumn>>([
  {
    label: '姓名',
    prop: 'name',
    span: 12,
    required: true,
    change: (column, value) => {
      console.log(column, value)
    },
    order: 1

  },
  {
    label: '年龄',
    prop: 'age',
    span: 12,
    order: 2
  },
  {
    label: '性别',
    prop: 'sex',
    span: 12,
    type: IColumnEnum.SELECT,
    dataList: sexDict.value,
    order: 3
  },
  {
    label: '性别2',
    prop: 'sex2',
    span: 12,
    type: IColumnEnum.SELECT,
    dictType: 'sex',
    order: 3,
    change: (column, value) => {
      console.log(column, value)
    },
    required: true
  },
  {
    label: '居住地',
    prop: 'cascader',
    span: 12,
    type: IColumnEnum.CASCADER,
    order: 4,
    dataList: placeOfResidence.value,
  },
  {
    label: '出生年月',
    prop: 'date',
    span: 12,
    type: IColumnEnum.DATE_PICKER,
    order: 4,
    format: "YYYY-MM-DD",
    valueFormat: "YYYY-MM-DD"
  },

  {
    label: '生卒',
    prop: 'daterange',
    span: 12,
    type: IColumnEnum.DATE_RANGE,
    order: 4,
    format: "YYYY-MM-DD",
    valueFormat: "YYYY-MM-DD",
    columnsWidth: '200px'
  }, {
    label: '卡槽',
    prop: 'customSlot',
    order: 2,
    display: true,
    required: true,
    type: IColumnEnum.SLOT,   // 表示使用自定义卡槽渲染
    slotName: 'customSlot' // 可选，若不提供则默认使用 "slot_customField"
  }


]);

const form = ref({
  name: ''
})

const addOrUpdateHandle = () => {
  console.log('addOrUpdateHandle')
  alert("新增事件")
}
const  deleteHandler = () =>{
  alert("删除事件")
}
</script>
