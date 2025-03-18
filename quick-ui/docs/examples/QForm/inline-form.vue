<template>
    <QForm ref="formRef" inline :model="FormValue" :form-options="formOptions" :buttons="['search', 'reset']"
        @search="onSearch">
    </QForm>
</template>

<script setup lang="ts" name="base">
import { ref } from "vue";
import { QForm } from "c7-plus";

const opts1 = [{ label: "选项1", value: "1" }, { label: "选项2", value: "2" }];

const opts = ref(opts1);
const formRef = ref<any>(null);
const FormValue = ref({
});
const formOptions = ref([
    { type: "input", label: "姓名", prop: "name" },
    { type: "select", label: "爱好", prop: "region", options: opts.value, },
    { type: "date-picker", label: "日期", prop: "count4", attrs: { type: "date" }, },
]);

const onSearch = () => {
    console.log(FormValue.value);
};
</script>
<style lang="scss" scoped></style>
