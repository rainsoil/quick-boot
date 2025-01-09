<template>

  <q-modal :visible="visibleRef" mode="dialog" title="查看" width="80%" :footer="false"
           @close="handleClose">


    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" label-width="100px" :disabled="true">


      <el-row>
        <el-col :span="12">
          <el-form-item label="方法名称:" prop="method">
            {{ dataForm.method }}

          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="操作人员:" prop="operName">
            {{ dataForm.operName }}
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="10">
          <el-form-item label="操作人员:" prop="operName">
            {{ dataForm.operName }}
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="请求URL:" prop="operUrl">
            {{ dataForm.requestMethod }} {{ dataForm.operUrl }}
          </el-form-item>

        </el-col>
      </el-row>


      <el-row>
        <el-col :span="10">
          <el-form-item label="主机地址" prop="operIp">
            {{ dataForm.operIp }}
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="操作地点" prop="operLocation">
            {{ dataForm.operLocation }}
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="请求参数" prop="operParam">
            {{ dataForm.operParam }}

          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="返回内容" prop="jsonResult">
            {{ dataForm.jsonResult }}
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="8">
          <el-form-item label="操作状态码" prop="status">
            <span :style="dataForm.status!=200  ? 'color: red':''">
              {{ dataForm.status }}
            </span>

          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="操作时间" prop="operTime">
            {{ dataForm.operTime }}

          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="消耗时间" prop="costTime">
            {{ dataForm.costTime }}

          </el-form-item>
        </el-col>
      </el-row>


    </el-form>
  </q-modal>
</template>

<script setup>
import qModal from '@/components/qModal/index.vue'
import {reactive, ref} from "vue";
import baseService from "@/service/baseService.js";


const {proxy} = getCurrentInstance();
const emit = defineEmits(["refreshDataList"]);
const visibleRef = ref(false);
const dataForm = ref({
  id: "",
  method: "",
  requestMethod: "",
  operName: "",
  operUrl: "",
  operIp: "",
  operLocation: "",
  operParam: "",
  jsonResult: "",
  status: "",
  operTime: "",
  costTime: "",


})
const handleClose = () => {
  visibleRef.value = false;
};
//  校验
const rules = ref(
    {
      // xxx: [{required: true, message: '请输入xxx', trigger: 'blur'}]


    }
);

// 初始化方法
const init = (id) => {
  visibleRef.value = true;
  if (id) {
    dataForm.value.id = id;
    getInfo(id);
  }

}
// 根据id查询详情
const getInfo = (id) => {
  baseService.get("/system/sysoperlog/" + id).then(res => {
    dataForm.value = res.data;
  })
}
const dataFormRef = ref()


defineExpose({
  init
})
</script>