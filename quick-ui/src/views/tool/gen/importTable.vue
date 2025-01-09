<template>
  <q-modal :visible="visibleRef" mode="dialog" title="表导入"
           :footer="false">
    <div class="app-container">
      <qtable-search :columns="searchColumns" :modelValue="state.dataForm" @handle-reset="state.handleReset"
                     @handle-search="state.getDataList"
                     @deleteHandle="state.deleteHandle"
                     :add-btn="false"
                     :del-btn="false"
      >
        <template #extra>
          <el-col :span="1.5">
            <el-button
                type="primary"
                plain
                icon="Download"
                @click="batchImport"

            >导入
            </el-button>
          </el-col>
        </template>
      </qtable-search>
      <qtable v-loading="state.dataListLoading" :tableData="state.dataList" :columns="jsonColumns"
              :page="state.page" :limit="state.limit" :total="state.total"
              @pageSizeChangeHandle="state.pageSizeChangeHandle"
              @pageCurrentChangeHandle="state.pageCurrentChangeHandle"
              @selection-change="state.dataListSelectionChangeHandle" selection:true>

      </qtable>

    </div>

  </q-modal>
</template>


<script setup>
import tableView from "@/hooks/tableView";
import qModal from '@/components/qModal/index.vue'
import {reactive, ref, toRefs} from "vue";
import baseService from "@/service/baseService.js";
const {proxy} = getCurrentInstance();
const emit = defineEmits(["refreshDataList"]);
const route = useRoute();
const view = reactive({
  getDataListURL: "/generator/gentable/dbTables",
  getDataListIsPage: true,
  createdIsNeed: false,
  deleteURL: "/xxxx",
  deleteIsBatch: true,
  exportURL: "",
  dataForm: {}
});
const state = reactive({...tableView(view), ...toRefs(view)});
const jsonColumns = ref([


  {
    prop: "tableName",
    label: "表名称"

  },

  {
    prop: "tableComment",
    label: "表注释"

  },

]);
const searchColumns = ref([

  {
    prop: "tableName",
    label: "表名称"

  },
  {
    prop: "tableComment",
    label: "表注释"

  },
])

const init = () => {
  visibleRef.value = true;
  state.getDataList();
}

const visibleRef = ref(false);


defineExpose({
  init
})

// 批量导入
const batchImport = () => {
  console.log(state.dataListSelections);
  if (state.dataListSelections.length == 0) {
    proxy.$modal.msgError(`请先选中!`);
    return false;
  }
  let tableNames = state.dataListSelections.map(item => item.tableName);
  baseService.post("/generator/gentable/importTable", tableNames).then(res => {
    proxy.$modal.msgSuccess("操作成功");
    visibleRef.value = false;
    emit("refreshDataList");
  })
}


</script>