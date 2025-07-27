<template>
  <template v-if="sortedColumns.length > 0">
    <el-col
        :span="getColumnSpan(item)"
        v-for="(item, index) in sortedColumns"
        :key="index"
    >
      <el-form-item
          :label="item.label"
          :prop="item.prop"
          :required="item.required ?? false"
          :rules="item.rules"
      >
        <!-- 自定义插槽 -->
        <template v-if="item.type === 'slot'">
          <slot
              :name="item.slotName || ('slot_' + item.prop)"
              :item="item"
              :modelValue="formData[item.prop]"
              @change="handleChange(item, $event)"
          ></slot>
        </template>
        <!-- 直接使用el-input -->
        <el-input
            v-else-if="(item.type === 'input' || !item.type) && item.display"
            v-model="formData[item.prop]"
            :placeholder="getPlaceholder(item)"
            :disabled="item.disabled"
            @input="(value:any) => handleInput(item, value)"
        />

        <!-- 直接使用el-input -->
        <el-input-number
            v-else-if="(item.type === 'input-number' || !item.type) && item.display"
            v-model="formData[item.prop]"
            :min="item.min"
            :max="item.max"
            :precision="item.precision"
            :placeholder="getPlaceholder(item)"
            :disabled="item.disabled"
            @input="(value:any) => handleInput(item, value)"
        />

        <!-- 下拉选择 -->
        <c7-select
            v-else-if="item.type === 'select'"
            v-model="formData[item.prop]"
            :placeholder="item.placeholder || '请输入' + item.label"
            :data-list="item.dataList"
            @change="handleChange(item, formData[item.prop])"
        />

        <!-- 级联-->
        <c7-cascader
            v-else-if="item.type === 'cascader'"
            v-model="formData[item.prop]"
            :result-type="item.resultType"
            :placeholder="item.placeholder || '请输入' + item.label"
            :data-list="item.dataList"
            @change="handleChange(item, formData[item.prop])"
        />


        <!-- 日期选择器 -->
        <c7-date-picker
            v-else-if="datePickerTypes.includes(item.type)"
            v-model="formData[item.prop]"
            :type="item.type"
            :value-format="item.valueFormat"
            :placeholder="item.placeholder || '请输入' + item.label"
            :format="item.format"
            :start-placeholder="item.startPlaceholder"
            :end-placeholder="item.endPlaceholder"
            :default-value="item.defaultValue"
            :default-timer="item.defaultTime"
            :range-separator="item.rangeSeparator"
            @change="handleChange(item, formData[item.prop])"
        />


        <!-- checkbox -->
        <c7-checkbox
            v-else-if="item.type === 'checkbox'"
            v-model="formData[item.prop]"
            :data-list="item.dataList"
            :separator="item.separator"
            @change="handleChange(item, formData[item.prop])"
        />
        <!--upload -->

        <c7-upload
            v-else-if="item.type === 'upload'"
            v-model="formData[item.prop]"
            :uploadUrl="item.uploadUrl"
            :deleteUrl="item.deleteUrl"

            :limit="item.limit"
            :file-type="item.fileType"
            :file-size="item.fileSize"
            :is-show-tip="item.isShowTip"
            @change="handleChange(item, formData[item.prop])"
        />
        <!--radio -->
        <c7-radio
            v-else-if="item.type === 'radio'"
            v-model="formData[item.prop]"
            :data-list="item.dataList"
            :separator="item.separator"
            @change="handleChange(item, formData[item.prop])"
        />
      </el-form-item>


    </el-col>
  </template>
</template>

<script setup lang="ts">
import {computed, defineProps, ref, watch, defineEmits, PropType} from 'vue'
import {IColumn, FormColumn} from '../types/JsonFormTypes'
// import {c7Select, c7Cascader, c7DatePicker, c7Checkbox, c7Upload,c7Radio} from 'c7-plus'
// 原导入方式
// import {c7Select, c7Cascader, c7DatePicker, c7Checkbox, c7Upload, c7Radio} from 'c7-plus'

// 修改后的相对路径导入方式
import c7Select from '../../c7-select/index'
import c7Cascader from '../../c7-cascader/index'
import c7DatePicker from '../../c7-date-picker/index'
import c7Checkbox from '../../c7-checkbox/index'
import c7Upload from '../../c7-upload/index'
import c7Radio from '../../c7-radio/index'

defineOptions({
  name: 'c7JsonForm'
})
const emit = defineEmits(['update:modelValue'])

// 定义表单数据模型
const props = defineProps({
  columns: {
    type: Array as PropType<FormColumn[]>,
    default: () => []
  },
  modelValue: {
    type: Object as PropType<Record<string, any>>,
    default: () => ({})
  },
})
// 表单数据存储
const formData = ref<Record<string, any>>({})
// 监听 localModel 的变化，触发 update:modelValue 事件
watch(
    formData,
    (newVal) => {
      // 避免直接修改 props.modelValue，使用浅拷贝
      if (JSON.stringify(newVal) !== JSON.stringify(props.modelValue)) {
        emit('update:modelValue', {...newVal})
      }
    },
    {deep: true}
)
// 监听 props.modelValue 的变化，同步到 localModel
watch(
    () => props.modelValue,
    (newVal) => {
      // 避免直接修改 localModel，使用浅拷贝
      if (JSON.stringify(newVal) !== JSON.stringify(formData.value)) {
        formData.value = {...newVal}
      }
    },
    {deep: true}
)

// 输入处理
const handleInput = (column: IColumn, value: string) => {
  // 1. 更新表单数据
  formData.value[column.prop] = value

  // 2. 触发字段自定义change事件
  if (typeof column.change === 'function') {
    column.change({
      prop: column.prop,
      value: value,
      formData: formData.value
    })
  }
}

// 优化后的计算属性
const sortedColumns = computed(() => {
  return props.columns
      .filter(column => column.visible !== false)
      .map((item, index) => ({
        ...item,
        order: item.order ?? index,
        span: item.span ?? 8,
        display: item.display ?? true,
      }))
      .sort((a, b) => a.order - b.order)
})


// 获取列跨度
const getColumnSpan = (column: FormColumn) => {
  return column.span ?? 8
}

// 获取占位符文本
const getPlaceholder = (column: FormColumn) => {
  return column.placeholder ?? `请输入${column.label}`
}
// 日期选择器支持的类型列表
const datePickerTypes = ref([
  'year', 'years', 'month', 'months', 'date', 'dates', 'datetime', 'week', 'datetimerange', 'daterange', 'monthrange', 'yearrange'
])
/**
 * change 事件处理
 */
const handleChange = (item?: IColumn, value?: any) => {
  if (item?.change && typeof item.change === 'function') {
    item.change(item, value)
  }
}
</script>

<style scoped>
/* 添加基础样式 */
.form-item {
  margin-bottom: 16px;
}

/* 响应式布局优化 */
@media (max-width: 768px) {
  .el-col {
    width: 100%;
    margin-bottom: 12px;
  }
}
</style>
