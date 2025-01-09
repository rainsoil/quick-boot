<template>
  <el-select
      v-model="selectedValue"
      :placeholder="placeholder"
      @change="handleChange"
      :clearable="clearable"
      :disabled="disabled"
      style="width: 240px"
  >
    <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
    />
  </el-select>
</template>

<script>
import {ref, computed, watch, onMounted} from "vue";
import {useDict} from "@/utils/dict.js"; // 请根据你的项目结构修改路径

export default {
  name: "DictionarySelect",
  props: {
    modelValue: {
      type: [String, Number],
      default: "",
    },
    dictType: {
      type: String,
      default: null,
    },
    dictList: {
      type: Array,
      default: () => [],
    },
    placeholder: {
      type: String,
      default: "请选择",
    },
    clearable: {
      type: Boolean,
      default: true,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    dicUrl: {
      type: String,
      default: null,
    },
  },
  setup(props, {emit}) {
    const selectedValue = ref(props.modelValue);

    const options = computed(() => {
      if (props.dictType) {
        const dictData = useDict(props.dictType);
        console.log(dictData)
        let valueRef = ref(dictData[props.dictType]);
        console.log(valueRef.value)
        return valueRef.value || [];
      } else if (props.dictList.length > 0) {
        return props.dictList;
      }
    });

    const handleChange = (value) => {
      emit("update:modelValue", value);
      emit("onChange", value); // 触发父组件传递的方法
    };

    watch(
        () => props.modelValue,
        (newValue) => {
          selectedValue.value = newValue;
        }
    );

    onMounted(() => {
      selectedValue.value = props.modelValue;
    });

    return {
      selectedValue,
      options,
      handleChange,
    };
  },
};
</script>
