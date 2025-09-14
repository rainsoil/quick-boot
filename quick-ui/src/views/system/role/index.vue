<template>
  <div class="app-container">
    <!-- 角色管理表格 -->
    <c7-json-table
      ref="tableRef"
      :listFunction="listRole"
      :tableColumns="tableColumns"
      :searchColumns="searchColumns"
      :tableProps="tableProps"
      rowsKey="data.records"
      totalKey="data.total"
      @addBtnHandle="handleAdd"
      @editBtnHandle="handleEdit"
      @deleteBtnHandle="handleDelete"
      @refreshDataList="refreshData"
    >
      <!-- 操作列插槽 -->
      <template #table-operate="scope">
        <c7-button
          type="primary"
          link
          icon="Edit"
          @click="handleEdit(scope.row)"
        >
          修改
        </c7-button>
        <el-dropdown trigger="click" @command="(command) => handleDropdownCommand(command, scope.row)">
          <c7-button type="info" link icon="MoreFilled">
            更多
          </c7-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="delete" icon="Delete">
                删除
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </template>
    </c7-json-table>

    <!-- 新增/编辑角色弹窗 -->
    <add-or-update
      :key="addKey"
      ref="addOrUpdateRef"
      @refreshDataList="refreshData"
    />
  </div>
</template>


<script setup>
import { ref, getCurrentInstance, nextTick } from "vue";
import { ElMessage, ElMessageBox } from 'element-plus';
import AddOrUpdate from "./add-or-update.vue";
import { listRole, delRole } from '@/api/system/role.js';
import { C7JsonTable, C7Button } from '@/components/c7';

// 获取当前实例和字典数据
const { proxy } = getCurrentInstance();
const dictData = proxy.useDict("sys_normal_disable");
const sys_normal_disable = dictData.sys_normal_disable;

// 表格引用
const tableRef = ref();
const addOrUpdateRef = ref();
const addKey = ref(0);

// 搜索字段配置
const searchColumns = ref([
  {
    prop: "roleName",
    label: "角色名称",
    type: "input"
  },
  {
    prop: "roleKey", 
    label: "权限字符",
    type: "input"
  },
  {
    prop: "status",
    label: "状态",
    type: "select",
    dataList: sys_normal_disable
  }
]);

// 表格列配置
const tableColumns = ref([
  {
    label: "角色名称",
    prop: "roleName",
    showOverflowTooltip: true
  },
  {
    label: "权限字符",
    prop: "roleKey",
    showOverflowTooltip: true
  },
  {
    label: "显示顺序",
    prop: "roleSort"
  },
  {
    label: "状态",
    prop: "status",
    columnType: 'tag',
    dictList: sys_normal_disable
  },
  {
    label: "创建时间",
    prop: "createTime"
  },
  {
    label: "操作",
    prop: "table-operate",
    width: 160,
    fixed: "right"
  }
]);

// 表格配置
const tableProps = ref({
  selection: true,
  showAdd: true,
  showEdit: true,
  showDelete: true,
  showRefresh: true
});

// 事件处理函数
const handleAdd = () => {
  addKey.value++;
  nextTick(() => {
    addOrUpdateRef.value.init();
  });
};

const handleEdit = (row) => {
  addKey.value++;
  nextTick(() => {
    addOrUpdateRef.value.init(row.id);
  });
};

const handleDelete = async (roleIdOrRows) => {
  try {
    let ids = [];
    let message = '';
    
    // 判断参数类型
    if (Array.isArray(roleIdOrRows)) {
      // 来自表格组件的批量删除
      ids = roleIdOrRows.map(row => row.id);
      message = `是否确认删除选中的${ids.length}个角色？`;
    } else {
      // 来自操作列的单个删除
      ids = [roleIdOrRows];
      message = `是否确认删除角色编号为"${roleIdOrRows}"的数据项？`;
    }
    
    await ElMessageBox.confirm(message, '系统提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    });
    
    // 批量删除
    for (const id of ids) {
      await delRole(id);
    }
    
    ElMessage.success('删除成功');
    refreshData();
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败');
    }
  }
};

const refreshData = () => {
  tableRef.value?.getDataList();
};

// 处理下拉菜单命令
const handleDropdownCommand = (command, row) => {
  switch (command) {
    case 'delete':
      handleDelete(row.id);
      break;
  }
};
</script>
