<template>

  <q-modal :visible="visibleRef" mode="dialog" title=" 查看" width="80%"
           @close="handleClose" :footer="false">


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
  userName: "",
  ipaddr: "",
  loginLocation: "",
  browser: "",
  os: "",
  status: "",
  msg: "",
  loginTime: "",


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
// 提交
const submit = () => {
  dataFormRef.value.validate(valid => {
    if (valid) {
      console.log(dataForm.value)
      if (dataForm.value.id != undefined) {
        // 修改
        baseService.put("/system/syslogininfor", dataForm.value).then(res => {
          proxy.$modal.msgSuccess("修改成功");
          visibleRef.value = false;
          emit("refreshDataList");
        })
      } else {
        //保存
        baseService.post("/system/syslogininfor", dataForm.value).then(res => {
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