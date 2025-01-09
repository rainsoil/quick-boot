<template>

  <q-modal :visible="visibleRef" mode="dialog" :title="(!dataForm.id)?'新增':'修改'"
           @close="handleClose" footer="false">


    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" label-width="100px" disabled="false">


      <el-row>
        <el-col :span="20">
          <el-form-item label="任务名称" prop="jobName">
            <el-input v-model="dataForm.jobName"
                      placeholder="请输入任务名称"/>

          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="10">
          <el-form-item label="任务组名" prop="jobGroup">
            <q-dict-select v-model="dataForm.jobGroup" dictType="sys_job_group"
                           type="select">
            </q-dict-select>

          </el-form-item>
        </el-col>

        <el-col :span="10">
          <el-form-item label="执行时间" prop="createTime">
            <el-input v-model="dataForm.createTime"
                      placeholder="请输入执行时间"/>
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="调用对象" prop="invokeTarget">
            <el-input v-model="dataForm.invokeTarget"
            />

          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="20">
          <el-form-item label="参数" prop="params">
            <el-input v-model="dataForm.params" type="textarea"
            />

          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="10">
          <el-form-item label="执行状态" prop="status">

            <q-dict-select v-model="dataForm.status" dictType="sys_common_status"
                           type="select"></q-dict-select>
          </el-form-item>
        </el-col>

        <el-col :span="10">
          <el-form-item label="耗时" prop="timeConsuming">
            <el-input v-model="dataForm.timeConsuming"
            />

          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="异常信息" prop="exceptionInfo">
            <el-input v-model="dataForm.exceptionInfo" type="textarea"
                      rows="10"
            />

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
import QDictSelect from "@/components/q-dict-select/index.vue";


const {proxy} = getCurrentInstance();
const emit = defineEmits(["refreshDataList"]);
const visibleRef = ref(false);
const dataForm = ref({
  id: "",
  jobName: "",
  jobGroup: "",
  invokeTarget: "",
  status: "",
  exceptionInfo: "",
  createTime: "",
  params: "",
  timeConsuming: ""


})
const handleClose = () => {
  visibleRef.value = false;
};
//  校验
const rules = ref(

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
  baseService.get("/quartz/sysjoblog/" + id).then(res => {
    dataForm.value = res.data;
  })
}
const dataFormRef = ref()


defineExpose({
  init
})
</script>