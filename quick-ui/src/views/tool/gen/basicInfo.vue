<<!-- 基础信息-->
<template>


  <el-form :model="dataForm" :rules="rules" ref="dataFormRef" label-width="120px">

    <el-divider content-position="left">基本信息</el-divider>
    <el-row>
      <el-col :span="12">
        <el-form-item label="表名称" prop="tableName">
          <el-input v-model="dataForm.tableName" :disabled placeholder="请输入表名称"/>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="表描述" prop="tableComment">
          <el-input v-model="dataForm.tableComment" :disabled placeholder="请输入表描述"/>
        </el-form-item>
      </el-col>
      <el-divider content-position="left">生成信息</el-divider>

      <el-col :span="12">
        <el-form-item label="实体类名称" prop="className">
          <el-input v-model="dataForm.className" :disabled placeholder="请输入实体类名称"/>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="作者" prop="functionAuthor">
          <el-input v-model="dataForm.functionAuthor" :disabled placeholder="请输入作者"/>
        </el-form-item>
      </el-col>


      <el-col :span="12">
        <el-form-item label="模块名" prop="moduleName">
          <el-input v-model="dataForm.moduleName" :disabled placeholder="请输入模块名"/>
        </el-form-item>
      </el-col>


      <el-col :span="12">
        <el-form-item label="生成代码方式" prop="genType">
          <q-dict-select :dict-list="genTypeDict" v-model="dataForm.genType" type="radio"/>
          <!--          <el-input v-model="dataForm.genType" :disabled placeholder="请输入生成代码方式"/>-->
        </el-form-item>
      </el-col>


      <el-col :span="12" v-if="dataForm.genType == '1'">
        <el-form-item label="生成包路径" prop="genPath">
          <el-input v-model="dataForm.genPath" :disabled placeholder="请输入生成包路径"/>
        </el-form-item>
      </el-col>


      <el-col :span="12">
        <el-form-item label="权限校验" prop="verifyPermission">
          <q-dict-select v-model="dataForm.verifyPermission" type="radio" dict-type="sys_yes_no">
          </q-dict-select>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item label="上级菜单" prop="parentId">
          <q-dict-select v-model="dataForm.parentId" type="cascader" dic-url="/system/menu/tree" check-strictly="true">
          </q-dict-select>
        </el-form-item>
      </el-col>




    </el-row>

    <div class="dialog-footer">
      <el-button type="primary" @click="submit">确 定</el-button>

    </div>


  </el-form>


</template>

<script setup>

import {ref} from "vue";
import baseService from "@/service/baseService.js";
import QDictSelect from "@/components/q-dict-select/index.vue";

const dataFormRef = ref();
// 表单
const dataForm = ref({
  tableName: "",
  tableComment: "",
  className: "",
  functionAuthor: "",
  moduleName: "",
  genType: "",
  genPath: "",
  verifyPermission: "0",
  parentId: "0"
})
const {proxy} = getCurrentInstance();
const genTypeDict = ref([
  {label: "zip压缩包", value: "0"},
  {label: "自定义路径", value: "1"}
])
// 参数校验
const rules = ref({
      tableName: [{required: true, message: '请输入表名', trigger: 'blur'}],
      tableComment: [{required: true, message: '请输入表描述', trigger: 'blur'}],
      className: [{required: true, message: '请输入实体类名称', trigger: 'blur'}],
      functionAuthor: [{required: true, message: '请输入作者', trigger: 'blur'}],
      moduleName: [{required: true, message: '请输入模块名', trigger: 'blur'}],
      // dictType: [{required: true, message: '请输入字典类型', trigger: 'blur'}],
    }
)

const interfaceUrl = "/generator/gentable";
const submit = () => {
  dataFormRef.value.validate(valid => {
    if (valid) {
      (dataForm.value.id != undefined ? baseService.put : baseService.post)(interfaceUrl, dataForm.value).then((res) => {
        proxy.$modal.msgSuccess("操作成功");
        // visibleRef.value = false;
        // emit("refreshDataList");
      });
    }
  });
}

// 获取详情
const getInfo = (tableId) => {
  baseService.get("/generator/gentable/" + tableId).then(res => {
    dataForm.value = res.data;
  })
}
const init = (tableId) => {
  console.log(tableId)
  if (tableId) {
    getInfo(tableId)
  }
}
defineExpose({
  init
})
</script>