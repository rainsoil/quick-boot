<template>
  <div class="app-container">

    <form-layout>
      <template #form>
        <el-form :model="state.dataForm" ref="queryRef" v-show="showSearch" :inline="true" label-width="68px">
          <el-form-item label="角色名称" prop="roleName">
            <el-input
                v-model="state.dataForm.roleName"
                placeholder="请输入角色名称"
                clearable
                style="width: 240px"
            />
          </el-form-item>
          <el-form-item label="权限字符" prop="roleKey">
            <el-input
                v-model="state.dataForm.roleKey"
                placeholder="请输入权限字符"
                clearable
                style="width: 240px"
            />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select
                v-model="state.dataForm.status"
                placeholder="角色状态"
                clearable
                style="width: 240px"
            >
              <el-option
                  v-for="dict in sys_normal_disable"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Search" @click="state.getDataList">搜索</el-button>
            <el-button icon="Refresh" @click="state.handleReset">重置</el-button>
          </el-form-item>
        </el-form>
      </template>


      <template #extra>
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="addOrUpdateHandle"
            v-hasPermi="['system:role:add']"
        >新增
        </el-button>
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:role:edit']"
        >修改
        </el-button>
      </template>
    </form-layout>

    <qtable v-loading="state.dataListLoading" :data="tableData" :columns="jsonColumns"
            @selection-change="state.dataListSelectionChangeHandle" :columnsOrder="[ 'age','name', 'address']">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column prop="address" label="Address" sortable/>

      <!-- 自定义 slot -->
      <template v-slot:customSlot="{ row }">
        <span>{{ row.roleSort }} - Custom Content</span>
      </template>
    </qtable>

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update :key="addKey" ref="addOrUpdateRef" @refreshDataList="state.getDataList"></add-or-update>


  </div>
</template>

<script setup name="Role">

import tableView from "@/hooks/tableView";
import {reactive, ref, toRefs} from "vue";
import formLayout from '@/components/form-layout/index.vue'
import qtable from "@/components/qtable/index.vue";
import AddOrUpdate from "@/views/system/role/add-or-update.vue";

const showSearch = ref(true);

const view = reactive({
  getDataListURL: "/system/role/list",
  getDataListIsPage: true,
  deleteURL: "/sys/schedule",
  deleteIsBatch: true,
  exportURL: "/system/role/export",
  dataForm: {
    beanName: ""
  }
});
const state = reactive({...tableView(view), ...toRefs(view)});

const jsonColumns = ref([
  {prop: 'name', label: 'Name', sortable: true},
  {prop: 'age', label: 'Age', sortable: true, formatter: (row, column, cellValue) => `${cellValue} years old`,},

  {
    prop: 'custom',
    label: 'Custom Slot',
    slot: 'customSlot',
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

</script>
