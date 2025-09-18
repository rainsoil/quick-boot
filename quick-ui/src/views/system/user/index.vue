<template>
  <div class="app-container">
    <!-- 用户管理表格 -->
    <c7-json-table 
      ref="tableRef"
      :listFunction="listUser" 
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
        <C7ButtonGroup>
          <C7Button 
            type="primary" 
            link 
            icon="Edit" 
            @click="handleEdit(scope.row)"
            v-hasPermi="['system:user:edit']"
          >
            修改
          </C7Button>
          <C7Button 
            type="danger" 
            link 
            icon="Delete" 
            @click="handleDelete(scope.row.userId)"
            v-hasPermi="['system:user:remove']"
          >
            删除
          </C7Button>
          <C7Button 
            type="warning" 
            link 
            icon="Key" 
            @click="handleResetPwd(scope.row)"
            v-hasPermi="['system:user:resetPwd']"
          >
            重置密码
          </C7Button>
        </C7ButtonGroup>
      </template>
    </c7-json-table>

    <!-- 新增/编辑用户弹窗 -->
    <add-or-update 
      :key="addKey" 
      ref="addOrUpdateRef" 
      @refreshDataList="refreshData"
    />
  </div>
</template>


<script setup>
import { C7JsonTable, C7Button, C7ButtonGroup } from "@/components/c7";
import { ref, getCurrentInstance, nextTick } from "vue";
import { ElMessage, ElMessageBox } from 'element-plus';
import AddOrUpdate from "./add-or-update.vue";
import { listUser, delUser, resetUserPwd } from '@/api/system/user.js';
import useUserStore from '@/store/modules/user';

// 获取当前实例和字典数据
const { proxy } = getCurrentInstance();
const dictData = proxy.useDict("sys_normal_disable", "sys_user_sex");
const sys_normal_disable = dictData.sys_normal_disable;
const sys_user_sex = dictData.sys_user_sex;

// 表格引用
const tableRef = ref();
const addOrUpdateRef = ref();
const addKey = ref(0);

// 调试权限信息
console.log('用户权限列表:', useUserStore().permissions);
console.log('检查权限 system:user:edit111:', proxy.checkPermission('system:user:edit111'));

// 表格配置
const tableProps = ref({
  selection: true,
  showAdd: proxy.checkPermission('system:user:add'), // 直接使用权限控制
  showEdit: proxy.checkPermission('system:user:edit'),
  showDelete: proxy.checkPermission('system:user:remove'),
  showRefresh: true,
  showExport: proxy.checkPermission('system:user:export'),
  showImport: proxy.checkPermission('system:user:import'),
  border: true,
  stripe: true,
  height: 'auto'
});

// 搜索字段配置
const searchColumns = ref([
  {
    label: "用户账号",
    prop: "userName",
    type: "input",
    placeholder: "请输入用户账号",
    clearable: true
  },
  {
    label: "用户昵称", 
    prop: "nickName",
    type: "input",
    placeholder: "请输入用户昵称",
    clearable: true
  },
  {
    label: "手机号码",
    prop: "phonenumber", 
    type: "input",
    placeholder: "请输入手机号码",
    clearable: true
  },
  {
    label: "帐号状态",
    prop: "status",
    type: "select",
    placeholder: "请选择帐号状态",
    dataList: sys_normal_disable,
    clearable: true
  },
  {
    label: "创建时间",
    prop: "createTime",
    type: "daterange",
    placeholder: ["开始日期", "结束日期"]
  }
]);

// 表格列配置
const tableColumns = ref([
  {
    label: "用户账号",
    prop: "userName",

    showOverflowTooltip: true
  },
  {
    label: "用户昵称",
    prop: "nickName", 

    showOverflowTooltip: true
  },
  {
    label: "用户邮箱",
    prop: "email",

    showOverflowTooltip: true
  },
  {
    label: "手机号码",
    prop: "phonenumber",

  },
  {
    label: "角色",
    prop: "roleNames",

    showOverflowTooltip: true
  },
  {
    label: "帐号状态",
    prop: "status",
    columnType: 'tag',
    dictList: sys_normal_disable,

  },
  {
    label: "创建时间",
    prop: "createTime",

  },
  {
    label: "最后登录时间", 
    prop: "loginDate",

  },
  {
    label: "操作",
    prop: "table-operate",
    width: 160,
    fixed: "right"
  }
]);

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

const handleDelete = async (userIdOrRows) => {
  try {
    let ids = [];
    let message = '';
    
    // 判断参数类型
    if (Array.isArray(userIdOrRows)) {
      // 来自表格组件的批量删除
      ids = userIdOrRows.map(row => row.id);
      message = `是否确认删除选中的${ids.length}个用户？`;
    } else {
      // 来自操作列的单个删除
      ids = [userIdOrRows];
      message = `是否确认删除用户编号为"${userIdOrRows}"的数据项？`;
    }
    
    await ElMessageBox.confirm(message, '系统提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    });
    
    // 批量删除
    for (const id of ids) {
      await delUser(id);
    }
    
    ElMessage.success('删除成功');
    refreshData();
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败');
    }
  }
};

const handleResetPwd = async (row) => {
  try {
    await ElMessageBox.confirm(
      '是否确认重置用户"' + row.userName + '"的密码？',
      '系统提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    );
    await resetUserPwd(row.id);
    ElMessage.success('重置成功');
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('重置失败');
    }
  }
};

// 处理下拉菜单命令
const handleDropdownCommand = (command, row) => {
  switch (command) {
    case 'delete':
      handleDelete(row.id);
      break;
    case 'resetPwd':
      handleResetPwd(row);
      break;
  }
};

const refreshData = () => {
  tableRef.value?.getDataList();
};
</script>
<style scoped lang="scss">

</style>
