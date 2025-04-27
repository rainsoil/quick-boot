<template>

  <c7-dialog :visible="visibleRef" mode="dialog" :title="(!dataForm.id)?'新增':'修改'"
             @close="handleClose" @submit="submit">


    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" label-width="100px">

      <el-row>
        <el-col :span="12">
          <el-form-item label="字典标签" prop="dictLabel">
            <el-input v-model="dataForm.dictLabel" placeholder="请输入字典标签"/>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="字典键值" prop="dictValue">
            <el-input v-model="dataForm.dictValue" placeholder="请输入字典键值"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="字典排序" prop="dictSort">
            <el-input-number v-model="dataForm.dictSort" controls-position="right" :min="0"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="样式属性" prop="cssClass">
            <el-input v-model="dataForm.cssClass" placeholder="请输入样式属性"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="回显样式" prop="listClass">
            <c7-select dict-type="sys_dict_list_class" v-model="dataForm.listClass"></c7-select>
          </el-form-item>

        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">

            <c7-radio dict-type="sys_normal_disable" v-model="dataForm.status" placeholder="请选择状态"></c7-radio>

          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="dataForm.remark" type="textarea" placeholder="请输入内容"></el-input>
          </el-form-item>
        </el-col>
      </el-row>


    </el-form>
  </c7-dialog>
</template>


<script setup>
import {c7Dialog, c7Select, c7Radio} from 'c7-plus'
import {reactive, ref} from "vue";
import baseService from "@/service/baseService.js";


const {proxy} = getCurrentInstance();
const emit = defineEmits(["refreshDataList"]);

const visibleRef = ref(false);
const dataForm = ref({
  dictType: "",
  id: undefined,
  dictLabel: "",
  dictValue: "",
  dictSort: 0,
  cssClass: "",
  listClass: "default",
  status: "0",
  remark: ""
})
const handleClose = () => {
  visibleRef.value = false;
};

const rules = ref(
    {
      dictType: [{required: true, message: '请输入字典类型', trigger: 'blur'}],
      dictLabel: [{required: true, message: '请输入字典标签', trigger: 'blur'}],
      dictValue: [{required: true, message: '请输入字典键值', trigger: 'blur'}],
      dictSort: [{required: true, message: '请输入字典排序', trigger: 'blur'}],

    }
);
const init = (dictType, id) => {
  console.log(dictType, id)
  visibleRef.value = true;
  dataForm.value.id = id;
  dataForm.value.dictType = dictType
  if (id) {
    getInfo(id);
  }

}
const getInfo = (id) => {
  baseService.get("/system/dict/data/" + id).then(res => {
    dataForm.value = res.data;
  })
}
const dataFormRef = ref()
const submit = () => {
  dataFormRef.value.validate(valid => {
    if (valid) {
      console.log(dataForm.value)
      if (dataForm.value.id != undefined) {
        // 修改
        baseService.put("/system/dict/data", dataForm.value).then(res => {
          proxy.$modal.msgSuccess("修改成功");
          visibleRef.value = false;
          emit("refreshDataList");
        })
      } else {
        //保存
        baseService.post("/system/dict/data", dataForm.value).then(res => {
          proxy.$modal.msgSuccess("新增成功");
          visibleRef.value = false;
          emit("refreshDataList");
        })
      }
    }
  });
}

defineExpose({
  init
})
</script>
