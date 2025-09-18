<template>

    <q-modal :visible="visibleRef" mode="dialog" :title="(!dataForm.id)?'新增':'修改'"
             @close="handleClose" @submit="submit">


        <el-form :model="dataForm" :rules="rules" ref="dataFormRef" label-width="100px">

                    </el-form>
    </q-modal>
</template>

<script setup>
    import qModal from '@/components/qModal/index.vue'
    import {reactive, ref} from "vue";
    import baseService from "@/service/baseService.ts";


    const {proxy} = getCurrentInstance();
    const emit = defineEmits(["refreshDataList"]);
    const visibleRef = ref(false);
    const dataForm = ref({
        id: "",


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
    const init = ( id) => {
        visibleRef.value = true;
        if (id) {
            dataForm.value.id = id;
            getInfo(id);
        }

    }
    // 根据id查询详情
    const getInfo = (id) => {
        baseService.get("/system/sysuser/" + id).then(res => {
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
                    baseService.put("/system/sysuser", dataForm.value).then(res => {
                        proxy.$modal.msgSuccess("修改成功");
                        visibleRef.value = false;
                        emit("refreshDataList");
                    })
                } else {
                    //保存
                    baseService.post("/system/sysuser", dataForm.value).then(res => {
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