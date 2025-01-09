<template>


  <form-layout>
    <template #form>
      <el-form :model="props.modelValue" ref="queryRef" :inline="true" :label-width="props.labelWidth">
        <el-form-item :label="item.label" :prop="item.prop" v-for="(item, index) in columns" :key="index">
          <el-input
              v-if="item.type === 'input' || !item.type"
              v-model="props.modelValue[item.prop]"
              :placeholder="item.placeholder?item.placeholder:'请输入'+item.label"
              clearable
              :style="'width:'+ (item.columnsWidth?item.columnsWidth:props.columnsWidth) "
          />

          <q-dict-select v-model="props.modelValue[item.prop]" :dictType="item.dictType" v-if="item.type === 'dict'"
                         :placeholder="item.placeholder?item.placeholder:'请输入'+item.label"
                         :style="'width:'+ (item.columnsWidth?item.columnsWidth:props.columnsWidth) "
          />
          <el-date-picker
              v-if="item.type === 'date' || item.type === 'week' || item.type === 'dates' || item.type === 'year'  || item.type === 'years' || item.type === 'month'  || item.type === 'months' "
              v-model="props.modelValue[item.prop]"
              :type="item.type"
              :placeholder="item.placeholder?item.placeholder:'请输入'+item.label"
              :format="item.format"
              :value-format="item.valueFormat"
              :default-value="item.defaultValue"
              :style="'width:'+ (item.columnsWidth?item.columnsWidth:props.columnsWidth) "
          />

          <el-date-picker
              v-if="item.type === 'daterange' || item.type === 'monthrange' || item.type === 'yearrange' "
              v-model="props.modelValue[item.prop]"
              unlink-panels
              :type="item.type"
              :format="item.format"
              :value-format="item.valueFormat"
              :default-value="item.defaultValue"
              :placeholder="item.placeholder?item.placeholder:'请输入'+item.label"
              @change="handleDateChange(item, $event)"
              :style="'width:'+ (item.columnsWidth?item.columnsWidth:props.columnsWidth) "
          />

        </el-form-item>
        <slot name="form"></slot>

        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleSearch()">搜索</el-button>
          <el-button icon="Refresh" @click="handleReset()">重置</el-button>
        </el-form-item>
      </el-form>


    </template>

    <template #extra>
      <el-button v-if="props.addBtn"
                 :type="props.addBtnType"
                 plain
                 :icon="props.addBtnIcon"
                 @click="addBtnHandle()"
                 v-hasPermi="props.addBtnPerms?[props.addBtnPerms]:[]"
      >{{ props.addBtnText }}
      </el-button>
      <el-col :span="1.5">
        <el-button
            v-if="props.delBtn"
            :type="props.delBtnType"
            plain
            :icon="props.delBtnIcon"
            :disabled="props.delMultiple"
            @click="deleteHandle()"
            v-hasPermi="props.delBtnPerms?[props.delBtnPerms]:[]"
        >{{ props.delBtnText }}
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            v-if="props.exportBtn"
            :type="props.exportBtnType"
            plain
            :icon="props.exportBtnIcon"
            @click="exportHandle()"
            v-hasPermi="props.exportBtnPerms?[props.exportBtnPerms]:[]"
        >{{ props.exportBtnText }}
        </el-button>
      </el-col>
      <slot name="extra"></slot>
    </template>
  </form-layout>
</template>


<script setup>
import {computed} from 'vue';


const props = defineProps({
  columns: {
    type: Array,
    default: () => [],
  },
  labelWidth: {
    type: String,
    default: '100px'
  },
  columnsWidth: {
    type: String,
    default: '240px'
  },
  addBtn: {
    type: Boolean,
    default: true
  },
  addBtnText: {
    type: String,
    default: '新增'
  },
  addBtnPerms: {
    type: String,
    default: ''
  },
  addBtnIcon: {
    type: String,
    default: 'Plus'
  },
  addBtnType: {
    type: String,
    default: 'primary'
  },

  delBtn: {
    type: Boolean,
    default: true
  },
  delBtnText: {
    type: String,
    default: '删除'
  },
  delBtnPerms: {
    type: String,
    default: ''
  },
  delBtnIcon: {
    type: String,
    default: 'Delete'
  },
  delBtnType: {
    type: String,
    default: 'danger'
  },
  delMultiple: {
    type: Boolean,
    default: false
  },
  exportBtn: {
    type: Boolean,
    default: false
  },
  exportBtnText: {
    type: String,
    default: '导出'
  },
  exportBtnPerms: {
    type: String,
    default: ''
  },
  exportBtnIcon: {
    type: String,
    default: 'Upload'
  },
  exportBtnType: {
    type: String,
    default: 'primary'
  },


  modelValue: {}
})
const emit = defineEmits(['update:modelValue', "handleSearch", "handleReset", "addBtnHandle", "deleteHandle", "exportHandle"])
const modelValue = ref({})
// 搜索函数
const handleSearch = () => {
  // console.log(props.modelValue)
  emit("handleSearch")
  emit('update:modelValue', props.modelValue)
}

// 重置函数
const handleReset = () => {
  emit("handleReset")
}
const formatter = (item, value) => {
  if (item.formatter && typeof item.formatter === 'function') {
    return item.formatter(value);
  }
  return value;
};

const handleDateChange = (item, value) => {
  if (item.formatter && typeof item.formatter === 'function') {
    const formattedValue = item.formatter(value);
    props.modelValue[item.model] = formattedValue;
  }
};

const addBtnHandle = () => {
  emit("addBtnHandle")
}
const deleteHandle = () => {
  emit("deleteHandle")
}

const exportHandle = () => {
  emit("exportHandle")
}
const queryRef = ref();
const resetField = () => {
  if (queryRef.value) {
    queryRef.value.resetFields();
  }
}
defineExpose({
  resetField
})
</script>