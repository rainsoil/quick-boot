<template>

  <el-select
      v-if="props.type === 'select'"
      v-model="selectedValue"
      :placeholder="placeholder"
      @change="handleChange"
      :clearable="clearable"
      :disabled="disabled"
      :remote-method="remoteMethod"
      :multiple="multiple"
      filterable
      :remote="remote"
      :loading="loading"
      :style="'width:'+ props.width"
  >
    <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
    />
  </el-select>

  <el-radio-group
      v-if="props.type === 'radio'"
      v-model="selectedValue"
      :clearable="clearable"
      :disabled="disabled"
      @change="handleChange"
      :style="'width:'+ props.width"

  >
    <el-radio v-for="item in options"
              :key="item.value"
              :label="item.value"
              :value="item.value">{{ item.label }}
    </el-radio>
  </el-radio-group>
  <el-checkbox-group

      v-if="props.type === 'checkbox'"
      v-model="selectedValueArrays"
      :clearable="clearable"
      :disabled="disabled"
      @change="handleChange"
      :style="'width:'+ props.width"

  >
    <el-checkbox v-for="item in options"
                 :key="item.value"
                 :label="item.value"
                 :value="item.value">{{ item.label }}
    </el-checkbox>
  </el-checkbox-group>

  <el-cascader
      v-if="props.type === 'cascader'"
      v-model="selectedValue"
      :options="options"
      :props="cascaderProps"
      :remote="remote"
      :remote-method="remoteMethod"
      @change="handleChange"
      filterable
      placeholder="请选择"
  ></el-cascader>


</template>

<script>
import {ref, computed, watch, onMounted} from "vue";
import {useDict} from "@/utils/dict.js";
import baseService from "@/service/baseService.js"; // 请根据你的项目结构修改路径
import request from '@/utils/request.js'

export default {
  name: "DictionarySelect",
  props: {
    modelValue: {
      type: String,
      default: "",
    },
    // 类型
    type: {
      type: String,
      default: "select",
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
    width: {
      type: String,
      default: '240px'
    },
    // 是否远程搜索
    remote: {
      type: Boolean,
      default: false
    },
    // 是否多选
    multiple: {
      type: Boolean,
      default: false
    },
    format: {
      type: String,
      default: 'YYYY-MM-DD'
    },
    valueFormat: {
      type: String,
      default: 'YYYY-MM-DD'
    },
    defaultValue: {
      type: String,
      default: ''
    },
    checkStrictly:{
      type: Boolean,
      default: false
    }
  },
  setup(props, {emit}) {
    const selectedValue = ref(props.modelValue);
    const selectedValueArrays = ref([])

    let loadOptions = async (value) => {
    };
    let options = ref([]);

    const cascaderProps = ref({
      value: 'value',
      label: 'label',
      children: 'children',
      checkStrictly: props.checkStrictly,
    });


    if (!props.dicUrl) {
      options = computed(() => {
        if (props.dictType) {
          const dictData = useDict(props.dictType);
          let valueRef = ref(dictData[props.dictType]);
          return valueRef.value || [];
        } else if (props.dictList.length > 0) {
          return props.dictList;
        }
      });
    } else {


      loadOptions = async (value) => {

        const res = await request.get(props.dicUrl + "?value=" + props.modelValue);
        if (props.type != 'cascader') {
          let resData = res.data;
          // 将数据转换为options
          resData.forEach((item) => {
            options.value.push({
              value: item.value,
              label: item.label,
            });
          });
        } else {
          options.value = res.data;
        }


      };
    }
    const loading = ref(false)

    const handleChange = (value) => {
      // emit("update:modelValue", value);
      if (props.type == 'cascader') {
        emit("update:modelValue", value[value.length - 1]);
      } else if (props.type == 'checkbox' || props.type == 'daterange' || props.type == 'monthrange' || props.type == 'yearrange' || props.type == 'datetdatetimerangeime') {
        // selectedValue.value = props.modelValue;
        emit("update:modelValue", value.join(","));
      } else {

        emit("update:modelValue", value);
        // selectedValueArrays.value = props.modelValue.split(",");
      }
      emit("onChange", value); // 触发父组件传递的方法
    };

    // watch(
    //     () => props.modelValue,
    //     (newValue) => {
    //       selectedValue.value = newValue;
    //     }
    // );
    // 监听 modelValue 变化
    watch(() => props.modelValue, (newValue) => {
      selectedValue.value = newValue;
      if (props.type == 'checkbox') {
        selectedValueArrays.value = newValue.split(",");
      } else if (props.type == 'cascader') {
        if (newValue) {
          //  获取父类
          baseService.get("/system/menu/tree/parent/" + newValue).then(res => {
            selectedValue.value = res.data;
          })
        }
      } else {
        if (newValue && props.remote) {
          fetchData(newValue);
        }
      }
      //
      //
      // if (props.type !== 'cascader') {
      //   if (newValue && props.remote) {
      //     fetchData(newValue);
      //   }
      // } else {
      //
      //
      // }
console.log(selectedValueArrays)
    });
// 获取数据
    const fetchData = async (query = '') => {
      try {
        const response = await request.get(props.dicUrl, {
          params: {query}
        });

        let resData = response.data;
        // 将数据转换为options
        resData.forEach((item) => {
          options.value.push({
            value: item.value,
            label: item.label,
          });
        });
      } catch (error) {
        console.error('Failed to fetch data:', error);
      }
    };
    onMounted(() => {

      if (props.dicUrl) {

        loadOptions(props.modelValue);
      }

      if (props.type == 'checkbox') {
        if (props.modelValue) {
          selectedValueArrays.value = props.modelValue.split(",");

        }
      } else {
        selectedValue.value = props.modelValue;
      }


    });
    // 远程搜索
    const remoteMethod = (query) => {

      loading.value = true
      baseService.get(props.dicUrl, {
        query: query
      }).then(res => {
        options.value = res.data;
        loading.value = false
      })
    }

    return {
      selectedValue,
      selectedValueArrays,
      options,
      handleChange,
      props,
      loading,

      remoteMethod,
      cascaderProps
    };
  },
};
</script>
