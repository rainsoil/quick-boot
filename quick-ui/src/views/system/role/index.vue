<template>
  <div class="app-container">
    <qtable-search :columns="searchColumns" :modelValue="state.dataForm" @handle-reset="state.handleReset"
                   @handle-search="state.getDataList"

                   add-btn-perms="system:role:add"
                   del-btn-perms="system:role:remove"
                   @add-btn-handle="addOrUpdateHandle()"
                   @deleteHandle="state.deleteHandle"
    ></qtable-search>
    <qtable v-loading="state.dataListLoading" :tableData="state.dataList" :columns="jsonColumns"
            :page="state.page" :limit="state.limit" :total="state.total"
            @pageSizeChangeHandle="state.pageSizeChangeHandle"
            @pageCurrentChangeHandle="state.pageCurrentChangeHandle"
            @selection-change="state.dataListSelectionChangeHandle" :table-props={selection:true}>
      <!-- 自定义列, 可以通过 order 配置列的顺序 -->


      <el-table-column label="操作" order="99">
        <template #default="scope">
          <el-tooltip content="修改" placement="top">
            <el-button link type="primary" icon="Edit" @click="addOrUpdateHandle(scope.row.id)"
                       v-hasPermi="['system:role:edit','system:role:query']"></el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top" v-if="scope.row.id !== 1">
            <el-button link type="primary" icon="Delete" @click="state.deleteHandle(scope.row.id)"
                       v-hasPermi="['system:role:remove']"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </qtable>

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="state.getDataList"></add-or-update>


  </div>
</template>

<script setup name="Role">
import tableView from "@/hooks/tableView";
import {reactive, ref, toRefs} from "vue";

import AddOrUpdate from "@/views/system/role/add-or-update.vue";
import QtableSearch from "@/components/qtableSearch/index.vue";

const showSearch = ref(true);

const view = reactive({
  getDataListURL: "/system/role/list",
  getDataListIsPage: true,
  deleteURL: "/system/role",
  deleteIsBatch: true,
  exportURL: "/system/role/export",
  dataForm: {
    beanName: ""
  }
});
const {proxy} = getCurrentInstance();

const state = reactive({...tableView(view), ...toRefs(view)});

const jsonColumns = ref([
  // {
  //   prop: "id",
  //   label: "角色编号",
  //   // width: "150",
  //   // order: 1, // 显示顺序为第二列
  // },
  {
    prop: "roleName",
    label: "角色名称",
    "show-overflow-tooltip": true,
    // width: "150",
    // order: 2, // 显示顺序为第一列
  },
  {
    prop: "roleKey",
    label: "权限字符",
    "show-overflow-tooltip": true,
    // width: "150",
    // order: 3, // 显示顺序为第一列
    // formatter: (value) => (value === "M" ? "Male" : "Female"),
  }, {
    prop: "roleSort",
    label: "显示顺序",

    // width: "150",
    // order: 4, // 显示顺序为第一列
  },
  {
    prop: "status",
    label: "状态",
    "show-overflow-tooltip": true,
    // width: "150",
    dictType: "sys_normal_disable",
    // order: 5, // 显示顺序为第一列
  },
  {
    prop: "createTime",
    label: "创建时间",
    // width: "150",
    // order: 6, // 显示顺序为第一列
  },
])
const searchColumns = ref([
  {
    prop: "roleName",
    label: "角色名称",

  },
  {
    prop: "roleKey",
    label: "权限字符",

  },
  {
    prop: "status",
    label: "状态",
    type: 'dict',
    // width: "150",
    dictType: "sys_normal_disable",
    // order: 5, // 显示顺序为第一列
  },
])

const addKey = ref(0);
const addOrUpdateRef = ref();
const addOrUpdateHandle = (id) => {

  addKey.value++;
  nextTick(() => {
    addOrUpdateRef.value.init(id);
  });
};

// 状态下拉
const handleSelectChange = (value) => {
  console.log("选中的值为:", value);
}
</script>
