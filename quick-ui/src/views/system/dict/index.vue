<template>
  <div class="app-container">

    <c7-switch-form
        :showIndexs="showIndexs"
        v-model="showIndex"
        @update:modelValue="console.log($event)"
    >
      <template #list>
        <c7-table-search :columns="searchColumns" ref="searchRef" v-model="searchParam"
                         @handleSearch="tableRef.getDataList()" @handleReset="tableRef.handleReset()"></c7-table-search>
        <c7-table :tableProps="tableProps" :columns="jsonColumns" ref="tableRef" :tableParam="searchParam"
                  :selection="true" @addBtnHandle="addBtnHandle">
          <template #dictType="scope">
            <a @click="toData(scope.row)">{{ scope.row.dictType }}</a>
          </template>
          <template #operate="scope">
            <el-button link type="primary" icon="Edit" @click="addBtnHandle(scope.row.id)"
                       v-hasPermi="['system:user:edit','system:user:query']">修改
            </el-button>
            <el-button link type="primary" icon="Delete" @click="tableRef.deleteBtnHandle(scope.row.id)"
                       v-hasPermi="['system:user:remove']">删除
            </el-button>
          </template>


        </c7-table>
        <!-- 弹窗, 新增 / 修改 -->
        <add-or-update ref="addOrUpdateRef" @refreshDataList="tableRef.getDataList()"></add-or-update>
      </template>

      <template #data>
        <!--字典项列表 -->

        <data-list ref="dataRef" :key="addKey"></data-list>
      </template>

    </c7-switch-form>


  </div>


</template>
<script setup>
import {c7Table, c7TableSearch, c7SwitchForm} from "c7-plus";
import {reactive, ref, toRefs,nextTick } from "vue";
import AddOrUpdate from "./add-or-update.vue";
import DataList from "@/views/system/dict/data.vue";
// 搜索
const searchParam = ref({});
// 搜索字段
const searchColumns = ref([

  {
    label: '字典名称',
    prop: 'dictName',
    type: 'input',
    // columnsWidth: "80px"
  },
  {
    label: '字典类型',
    prop: 'dictType',
    type: 'input',
  },
  {
    prop: "status",
    label: "状态",
    type: 'select',
    dictType: "sys_normal_disable"
  },

]);


// 列表
const tableRef = ref();
const tableProps = reactive({
  getDataListURL: "/system/dict/type/page",
  getDataListIsPage: true,
  deleteURL: "/system/dict/type",
  deleteIsBatch: true,
  dataForm: searchParam

})

// 列表字段配置
const jsonColumns = ref([

  {
    prop: "dictName",
    label: "字典名称",

  },
  {
    prop: "dictType",
    label: "字典类型",
    isSlot: true,
    slotName: "dictType"
  },
  {
    prop: "status",
    label: "状态",
    type: 'dict',
    dictType: "sys_normal_disable"
  },

  {
    prop: "remark",
    label: "备注",
    "show-overflow-tooltip": true,
  },
  {
    prop: "createTime",
    label: "创建时间",
  },

]);
const addKey = ref(0);
const addOrUpdateRef = ref();
const addBtnHandle = (id) => {
  addKey.value++;
  nextTick(() => {
    addOrUpdateRef.value.init(id);
  });
}

const showIndexs = ref([{
  title: '列表',
  name: 'list',

},
  {
    title: '字典项',
    name: 'data',
    header: true,
    closeIndex: 'list'
  }])

const showIndex = ref("list")
const dataRef = ref();
const toData = (row) => {
  addKey.value++;
  showIndex.value = "data";
  nextTick(() => {
    console.log(dataRef)
    dataRef.value.init(row.dictType);
  })
}
</script>

<style scoped>


</style>
