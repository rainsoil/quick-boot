<template>
  <div class="app-container">
    <!-- 字典数据表格 -->
    <c7-json-table
      ref="tableRef"
      :init="false"
      :initParam="initParam"
      :listFunction="listDictData"
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
      <template #table-operate="scope">
        <C7ButtonGroup>
          <C7Button 
            link
            type="primary" 
            icon="Edit"
            @click="handleEdit(scope.row)" 
            v-hasPermi="['system:dict:edit']"
          >
            编辑
          </C7Button>
          <C7Button 
            link
            type="danger" 
            icon="Delete"
            @click="handleDelete(scope.row.dictCode)" 
            v-hasPermi="['system:dict:remove']"
          >
            删除
          </C7Button>
        </C7ButtonGroup>
      </template>
    </c7-json-table>

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update 
      :key="addKey" 
      ref="addOrUpdateRef" 
      @refreshDataList="refreshData"
    />
  </div>
</template>
<script setup>
import {C7JsonTable, C7JsonForm, C7Button, C7ButtonGroup} from "@/components/c7";
import { reactive, ref, toRefs, getCurrentInstance, nextTick } from "vue";
import { ElMessage, ElMessageBox } from 'element-plus';
import AddOrUpdate from "./data-add-or-update.vue";
import { listData as listDictData, delData as delDictData } from '@/api/system/dict/data.js';

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
    label: "字典标签",
    prop: "dictLabel",
    type: "input",
    placeholder: "请输入字典标签"
  },
  {
    label: "字典键值",
    prop: "dictValue",
    type: "input",
    placeholder: "请输入字典键值"
  },
  {
    label: "状态",
    prop: "status",
    type: "select",
    dictType: "sys_normal_disable"
  }
]);

// 表格列配置
const tableColumns = ref([
  {
    label: "字典类型",
    prop: "dictType",
    showOverflowTooltip: true
  },
  {
    label: "字典标签",
    prop: "label",
    showOverflowTooltip: true
  },
  {
    label: "字典键值",
    prop: "value",
    showOverflowTooltip: true
  },
  {
    label: "字典排序",
    prop: "dictSort",
    width: 100
  },
  {
    label: "状态",
    prop: "status",
    columnType: 'tag',
    dictList: sys_normal_disable
  },
  {
    label: "备注",
    prop: "remark",
    showOverflowTooltip: true
  },
  {
    label: "创建时间",
    prop: "createTime",
    width: 180
  },
  {
    label: "操作",
    prop: "table-operate",
    width: 260,
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
    addOrUpdateRef.value.init(row.dictCode);
  });
};

const handleDelete = async (dictCodeOrRows) => {
  try {
    let ids = [];
    let message = '';
    
    // 判断参数类型
    if (Array.isArray(dictCodeOrRows)) {
      // 来自表格组件的批量删除
      ids = dictCodeOrRows.map(row => row.dictCode);
      message = `是否确认删除选中的${ids.length}个字典数据？`;
    } else {
      // 来自操作列的单个删除
      ids = [dictCodeOrRows];
      message = `是否确认删除字典数据编号为"${dictCodeOrRows}"的数据项？`;
    }
    
    await ElMessageBox.confirm(message, '系统提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    });
    
    // 批量删除
    for (const id of ids) {
      await delDictData(id);
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

// 操作按钮配置（配置式使用）
const operateActions = [
  {
    key: 'edit',
    label: '修改',
    type: 'primary',
    icon: 'Edit',
    handler: 'handleEdit'
  },
  {
    key: 'delete',
    label: '删除',
    type: 'danger',
    icon: 'Delete',
    confirm: '确定删除该字典项吗？',
    handler: 'handleDelete'
  }
]

// 获取操作按钮配置（旧版本兼容）
const getOperateActions = (row) => {
  return [
    {
      command: 'edit',
      label: '修改',
      type: 'primary',
      icon: 'Edit'
    },
    {
      command: 'delete',
      label: '删除',
      type: 'danger',
      icon: 'Delete'
    }
  ]
}

// 处理操作按钮点击
const handleOperateAction = ({ command, data }) => {
  switch (command) {
    case 'edit':
      handleEdit(data);
      break;
    case 'delete':
      handleDelete(data.dictCode);
      break;
  }
};
const  initParam = ref({})
// 初始化函数
const init = (dictType) => {
  console.log('init called with dictType:', dictType);
  initParam.value = {
    dictType: dictType,
  }
  console.log('initParam set to:', initParam.value)
  // 不需要手动调用 getDataList，initParam 变化会自动触发
};

defineExpose({
  init
})
</script>

<style scoped>


</style>


