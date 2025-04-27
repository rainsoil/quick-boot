<template>

  <c7-dialog :visible="visibleRef" mode="dialog" title="查看" width="80%" :footer="false"
             @close="handleClose">


    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" label-width="100px">


      <el-row>
        <el-col :span="8">
          <el-form-item label="用户账号" prop="userName">
            {{ dataForm.userName }}
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="登录IP地址" prop="ipaddr">
            {{ dataForm.ipaddr }}
          </el-form-item>
        </el-col>

      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="登录地点" prop="loginLocation">
            {{ dataForm.loginLocation }}

          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="登录时间" prop="loginTime">
            {{ dataForm.loginTime }}
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="8">
          <el-form-item label="浏览器类型" prop="browser">
            {{ dataForm.browser }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="操作系统" prop="os">
            {{ dataForm.os }}
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>

      </el-row>


      <el-row>

      </el-row>

    </el-form>
  </c7-dialog>
</template>

<script setup>
import {c7Dialog} from 'c7-plus'
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
  baseService.get("/system/syslogininfor/" + id).then(res => {
    dataForm.value = res.data;
  })
}
const dataFormRef = ref()


defineExpose({
  init
})
</script>
