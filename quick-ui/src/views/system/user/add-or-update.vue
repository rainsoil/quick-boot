<template>

  <q-modal :visible="visibleRef" mode="dialog" :title="(!dataForm.id)?'新增':'修改'"
           @close="handleClose" @submit="submit">


    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" label-width="100px">


      <el-row>
        <el-col :span="20">
          <el-form-item label="用户账号" prop="userName">
            <el-input v-model="dataForm.userName" :disabled="(!dataForm.id)?false:true"
                      placeholder="请输入用户账号"/>

          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="用户昵称" prop="nickName">
            <el-input v-model="dataForm.nickName"
                      placeholder="请输入用户昵称"/>

          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="用户邮箱" prop="email">
            <el-input v-model="dataForm.email"
                      placeholder="请输入用户邮箱"/>

          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="手机号码" prop="phonenumber">
            <el-input v-model="dataForm.phonenumber"
                      placeholder="请输入手机号码"/>

          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="用户性别" prop="sex">
            <q-dict-select v-model="dataForm.sex" dictType="sys_user_sex"
                           type="radio">
            </q-dict-select>

          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="密码" prop="password">
            <el-input v-model="dataForm.password" type="password"
                      placeholder="请输入密码"/>

          </el-form-item>
        </el-col>
      </el-row>


      <el-row>
        <el-col :span="20">
          <el-form-item label="帐号状态" prop="status">
            <q-dict-select v-model="dataForm.status" dictType="COMMON_STATUS"
                           type="radio">
            </q-dict-select>

          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="20">
          <el-form-item label="角色" prop="roleIds">
            <el-select
                v-model="dataForm.roleIds"
                multiple
                placeholder="请选择角色"
                style="width: 240px"
            >
              <el-option
                  v-for="item in roleData"
                  :key="item.id"
                  :label="item.roleName"
                  :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="20">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="dataForm.remark" type="textarea"
                      placeholder="请输入备注"/>

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
  userName: "",
  nickName: "",
  email: "",
  phonenumber: "",
  sex: "",
  password: "",
  status: "0",
  remark: "",
  roleIds: ''


})
const handleClose = () => {
  visibleRef.value = false;
};

const validatePassword = (rule, value, callback) => {

  if (!dataForm.value.id && (value == '' || value.length == 0)) {
    return callback(new Error("密码不能为空"));
  }
  callback();
}
//  校验
const rules = ref(
    {
      // xxx: [{required: true, message: '请输入xxx', trigger: 'blur'}]
      userName: [{required: true, message: '请输入用户账号', trigger: 'blur'}],
      nickName: [{required: true, message: '请输入用户昵称', trigger: 'blur'}],
      password: [

        {validator: validatePassword, trigger: "blur"}
      ],
      status: [{required: true, message: '请输入帐号状态', trigger: 'blur'}],
      roleIds: [{required: true, message: '请输入角色', trigger: 'blur'}],


    }
);

// 初始化方法
const init = (id) => {
  visibleRef.value = true;
  getAllRoleHandler();
  if (id) {
    dataForm.value.id = id;
    getInfo(id);
  }

}
// 根据id查询详情
const getInfo = (id) => {
  baseService.get("/sys/user/" + id).then(res => {
    dataForm.value = res.data;
  })
}
const dataFormRef = ref()
// 提交
const submit = () => {
  dataFormRef.value.validate(valid => {
    if (valid) {



      if (dataForm.value.id) {
        // 修改
        baseService.put("/sys/user", dataForm.value).then(res => {
          proxy.$modal.msgSuccess("修改成功");
          visibleRef.value = false;
          emit("refreshDataList");
        })
      } else {
        //保存
        baseService.post("/sys/user", dataForm.value).then(res => {
          proxy.$modal.msgSuccess("新增成功");
          visibleRef.value = false;
          emit("refreshDataList");
        })
      }
    }
  });
}

const roleData = ref([]);
const getAllRoleHandler = () => {
  baseService.get("/system/role/lists").then(res => {
    roleData.value = res.data
  })
}
defineExpose({
  init
})
</script>