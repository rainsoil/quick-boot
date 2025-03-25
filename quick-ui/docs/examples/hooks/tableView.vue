<template>
  <el-row>
    <el-col :span="24">
      <el-button
          type="primary"
          plain
          icon="Plus"
          @click="alertDataListSelections"
      >选中的数据
      </el-button>

      <el-button
          type="primary"
          plain
          icon="Plus"
          @click="alertDataListSelectionsId"
      >选中的数据id
      </el-button>

      <el-button
          type="primary"
          plain
          icon="Plus"
          @click="state.deleteHandle"
      >删除
      </el-button>


      <el-button
          type="primary"
          plain
          icon="Plus"
          @click="state.handleReset"
      >重置
      </el-button>



    </el-col>
  </el-row>
  <el-table :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" @sort-change="state.dataListSortChangeHandle">
    <el-table-column type="selection"></el-table-column>
    <el-table-column label="id" prop="id" sortable ></el-table-column>
    <el-table-column label="姓名" prop="name"></el-table-column>
    <el-table-column label="性别" prop="sex">

      <template #default="scope">
        {{ state.getDictLabel("sys_user_sex", scope.row.sex) }}
      </template>
    </el-table-column>
    <el-table-column label="操作" order="99" width="80px">
      <template #default="scope">

        <el-tooltip content="删除" placement="top">
          <el-button link type="primary" icon="Delete" @click="state.deleteHandle(scope.row.id)"
          >删除
          </el-button>
        </el-tooltip>
      </template>
    </el-table-column>

  </el-table>
  <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit"
                 :total="state.total" layout="total, sizes, prev, pager, next, jumper"
                 @size-change="state.pageSizeChangeHandle"
                 @current-change="state.pageCurrentChangeHandle"></el-pagination>

</template>

<script setup lang="ts">
import {reactive, ref, toRefs} from "vue";
import { tableHook, IViewHooksOptions} from "c7-plus";
const view = reactive<IViewHooksOptions>({
  getDataListURL: "/mockapi/crud/tableView",
  getDataListIsPage: true,
  deleteURL: "/mockapi/crud/tableView",
  deleteIsBatch: true,
  exportURL: "",
  dataForm: {}
});
const state = reactive({...tableHook(view), ...toRefs(view)});

const alertDataListSelections = () => {
  alert(JSON.stringify(state.dataListSelections))
}

const alertDataListSelectionsId = () => {
  alert(JSON.stringify(state.dataListSelectionsIds))
}
</script>
