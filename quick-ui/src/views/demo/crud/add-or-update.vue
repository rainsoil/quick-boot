<template>

  <q-modal :visible="visibleRef" mode="dialog" :title="(!dataForm.id)?'新增':'修改'"
           @close="handleClose"
           @submit="submit">

    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" label-width="120px">

      <el-row>
        <!--        <el-col :span="12">
                  <el-form-item label="字典标签" prop="dictLabel">
                    <el-input v-model="dataForm.dictLabel" placeholder="请输入字典标签"/>
                  </el-form-item>
                  <el-form-item label="回显样式" prop="listClass">
                    <q-dict-select v-model="dataForm.listClass" dictType="sys_dict_list_class" type="select">
                    </q-dict-select>

                </el-col>-->


        <el-col :span="12">
          <el-form-item label="input" prop="input">
            <el-input v-model="dataForm.input" placeholder="请输入input"/>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="inputNumber" prop="inputNumber">
            <el-input-number v-model="dataForm.inputNumber" placeholder="请输入inputNumber"/>
          </el-form-item>
        </el-col>


      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="radio" prop="radio">
            <q-dict-select v-model="dataForm.radio" dictType="sys_common_status" type="radio">
            </q-dict-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="select" prop="select">
            <q-dict-select v-model="dataForm.select" dictType="sys_common_status" type="select">
            </q-dict-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="remoteSelect" prop="remoteSelect">
            <q-dict-select v-model="dataForm.remoteSelect" :remote="true" dic-url="/demo/crud/remoteSelect">
            </q-dict-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="tree" prop="tree">
            <q-dict-select v-model="dataForm.tree" type="cascader" dic-url="/demo/crud/tree">
            </q-dict-select>
            <!--            <el-cascader-->
            <!--                v-model="selectedValue"-->
            <!--                :options="options"-->
            <!--                :props="pops2"-->
            <!--                placeholder="请选择"-->
            <!--            ></el-cascader>-->
          </el-form-item>
        </el-col>

      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="image" prop="image">

            <file-upload v-model="dataForm.image" :limit="4" classify="demo"></file-upload>
          </el-form-item>
        </el-col>

      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="file" prop="file">
            <file-upload classify="demofile" v-model="dataForm.file" :limit="3"
                         :file-type="'.doc,.docx,.pdf'"></file-upload>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="checkBox" prop="checkBox">
            <q-dict-select v-model="dataForm.checkBox" dictType="sys_common_status" type="checkbox">
            </q-dict-select>

          </el-form-item>

        </el-col>
        <el-col :span="12">
          <el-form-item label="datetime" prop="datetime">
            <q-date-picker v-model="dataForm.datetime" type="datetime"></q-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="18">
          <el-form-item label="dataPicker" prop="dataPicker">
            <!--            <el-date-picker-->
            <!--                v-model="dataForm.dataTimePicker"-->
            <!--                type="daterange"-->
            <!--                placeholder="Pick a Date"-->
            <!--                format="YYYY/MM/DD"-->
            <!--                value-format="YYYY-MM-DD"-->
            <!--            />-->
            <q-date-picker v-model="dataForm.dataPicker" type="daterange" :join="true"></q-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="18">
          <el-form-item label="dataTimePicker" prop="dataTimePicker">
            <!--            <q-dict-select v-model="dataForm.dataTimePicker" type="datetime" value-format="YYYY/MM/DD HH:mm:ss" format="YYYY-MM-DD HH:mm:ss" >-->
            <!--            </q-dict-select>-->
            <q-date-picker v-model="dataForm.dataTimePicker" type="datetime"></q-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>


  </q-modal>

</template>


<script setup>
import qModal from '@/components/qModal/index.vue'
import {reactive, ref} from "vue";
import QDictSelect from "@/components/q-dict-select/index.vue";
import baseService from "@/service/baseService.js";
import FileUpload from "@/components/FileUpload/index.vue";
import QDatePicker from "@/components/q-date-picker/index.vue";


const {proxy} = getCurrentInstance();
const emit = defineEmits(["refreshDataList"]);

const visibleRef = ref(false);

// 接口url
const interfaceUrl = "/demo/crud";

// 表单数据
const dataForm = ref({
  id: undefined,
  dataPicker: ""
});

// 数据校验
const rules = ref({
      // roleName: [{required: true, message: '请输入角色名称', trigger: 'blur'}],
    }
)

const handleClose = () => {
  visibleRef.value = false;
};
const init = (id) => {
  visibleRef.value = true;
  dataForm.value.id = id;
  getTreeData();
  if (id) {
    getInfo(id);
  }

}
const getInfo = (id) => {
  baseService.get(interfaceUrl + "/" + id).then(res => {
    dataForm.value = res.data;
  })
}
const dataFormRef = ref()
const submit = () => {
  console.log(dataForm.value)
  dataFormRef.value.validate(valid => {
    if (valid) {
      (dataForm.value.id != undefined ? baseService.put : baseService.post)(interfaceUrl, dataForm.value).then((res) => {
        proxy.$modal.msgSuccess("操作成功");
        visibleRef.value = false;
        emit("refreshDataList");
      });
    }
  });
}

//  demo
const selectedValue = ref([]);
const options = ref([]);
const pops2 = ref({
  value: 'id',
  label: 'name',
  children: 'children'
});

const getTreeData = () => {
  baseService.get("/demo/crud/tree").then(res => {
    options.value = res.data;
  })
}

defineExpose({
  init
})

</script>
