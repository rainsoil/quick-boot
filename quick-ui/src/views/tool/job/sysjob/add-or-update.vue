<template>

  <c7-dialog :visible="visibleRef" mode="dialog" :title="(!dataForm.id)?'新增':'修改'"
             @close="handleClose" @submit="submit" :footer="getType === '1'">


    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" label-width="140px" :disabled="getType !== '1'">


      <el-row>
        <el-col :span="12">
          <el-form-item label="任务名称" prop="jobName">
            <el-input v-model="dataForm.jobName"
                      placeholder="请输入任务名称"/>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="任务组名" prop="jobGroup">
            <c7-select dict-type="sys_job_group" v-model="dataForm.jobGroup"
                       placeholder="请选择任务组名">
            </c7-select>


          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="20">
          <el-form-item label="调用对象" prop="invokeTarget">
            <template #label>
                        <span>
                           调用对象
                           <el-tooltip placement="top">
                              <template #content>
                                 <div>
                                    调用对象必须实现 ITask接口
                                 </div>
                              </template>
                              <el-icon><question-filled/></el-icon>
                           </el-tooltip>
                        </span>
            </template>
            <el-input v-model="dataForm.invokeTarget"
                      placeholder="请输入调用目标字符串"/>

          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="20">

          <el-form-item label="参数" prop="params">
            <el-input v-model="dataForm.params"
                      type="textarea"
                      placeholder="请输入参数"/>
          </el-form-item>

        </el-col>
        <el-col :span="8">

        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="cron表达式" prop="cronExpression">
            <el-input v-model="dataForm.cronExpression"
                      placeholder="请输入cron执行表达式">
              <template #append>
                <el-button type="primary" @click="handleShowCron">
                  生成表达式
                  <i class="el-icon-time el-icon--right"></i>
                </el-button>
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="20">
          <el-form-item label="下次执行时间" prop="nextTime">
            <el-input v-model="dataForm.nextTime" type="textarea"
                      rows="5"
                      placeholder="下次执行时间">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="20">
          <el-form-item label="计划执行错误策略" prop="misfirePolicy">
            <c7-radio dict-type="JOB_MISFIRE_POLICY" v-model="dataForm.misfirePolicy"></c7-radio>
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="是否并发执行" prop="concurrent">

            <c7-radio dict-type="sys_yes_no" v-model="dataForm.concurrent"></c7-radio>

          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="状态" prop="status">

            <c7-radio dict-type="sys_job_status" v-model="dataForm.status"></c7-radio>
          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="备注信息" prop="remark">
            <el-input v-model="dataForm.remark" type="textarea"
                      placeholder="请输入备注信息"/>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>
  </c7-dialog>
  <el-dialog title="Cron表达式生成器" v-model="openCron" append-to-body destroy-on-close>
    <crontab ref="crontabRef" @hide="openCron=false" @fill="crontabFill" :expression="expression"></crontab>
  </el-dialog>

</template>

<script setup>
import {c7Dialog, c7Table, c7Radio, c7Select} from 'c7-plus'
import {reactive, ref} from "vue";
import baseService from "@/service/baseService.js";
import Crontab from '@/components/Crontab'


const {proxy} = getCurrentInstance();
const emit = defineEmits(["refreshDataList"]);
const visibleRef = ref(false);
const dataForm = ref({
  id: undefined,
  jobName: "",
  jobGroup: "",
  invokeTarget: "",
  cronExpression: "",
  misfirePolicy: "1",
  concurrent: "0",
  status: "0",
  remark: "",
  params: "",
  nextTime: ""


})
const handleClose = () => {
  visibleRef.value = false;
};
//  校验
const rules = ref(
    {
      // xxx: [{required: true, message: '请输入xxx', trigger: 'blur'}]
      jobName: [{required: true, message: '请输入任务名称', trigger: 'blur'}],
      jobGroup: [{required: true, message: '请输入任务组名', trigger: 'blur'}],
      invokeTarget: [{required: true, message: '请输入调用目标字符串', trigger: 'blur'}],
      cronExpression: [{required: true, message: '请输入cron执行表达式', trigger: 'blur'}],
      misfirePolicy: [{
        required: true,
        message: '请输入计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
        trigger: 'blur'
      }],
      concurrent: [{required: true, message: '请输入是否并发执行（0允许 1禁止）', trigger: 'blur'}],
      status: [{required: true, message: '请输入状态（0正常 1暂停）', trigger: 'blur'}],


    }
);

const getType = ref('');
// 初始化方法
const init = (id, type) => {
  visibleRef.value = true;
  getType.value = type;
  if (id) {
    dataForm.value.id = id;
    getInfo(id);
  }

}
// 根据id查询详情
const getInfo = (id) => {
  baseService.get("/quartz/sysjob/" + id).then(res => {
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
        baseService.put("/quartz/sysjob", dataForm.value).then(res => {
          proxy.$modal.msgSuccess("修改成功");
          visibleRef.value = false;
          emit("refreshDataList");
        })
      } else {
        //保存
        baseService.post("/quartz/sysjob", dataForm.value).then(res => {
          proxy.$modal.msgSuccess("新增成功");
          visibleRef.value = false;
          emit("refreshDataList");
        })
      }
    }
  });
}

// cron表达式
const openCron = ref(false);
const expression = ref("");

/** cron表达式按钮操作 */
function handleShowCron() {
  expression.value = dataForm.value.cronExpression;
  openCron.value = true;
}

/** 确定后回传值 */
function crontabFill(value) {
  dataForm.value.cronExpression = value;
}

defineExpose({
  init
})
</script>
