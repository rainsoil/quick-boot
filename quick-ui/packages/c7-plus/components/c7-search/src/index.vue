<template>
  <div class="form-layout-container">
    <div ref="formMainLeft">
      <el-form :ref="props.refName" :label-width="props.labelWidth">
        <el-row>
          <el-col :span="item.span || 8" v-for="(item, index) in sortedColumns" :key="index">
            <el-form-item
                :label="item.label"
                :prop="item.prop"
                :required="item.required"
                :rules="item.rules"
            >
              <!-- 自定义插槽 -->
              <template v-if="item.type === 'slot'">
                <slot
                    :name="item.slotName || ('slot_' + item.prop)"
                    :item="item"
                    :modelValue="localModel[item.prop]"
                    @change="handleChange(item, $event)"
                ></slot>
              </template>

              <!-- 输入框 -->
              <el-input
                  v-else-if="(item.type === 'input' || !item.type) && item.display"
                  v-model="localModel[item.prop]"
                  :placeholder="item.placeholder || '请输入' + item.label"
                  clearable
                  @change="handleChange(item, localModel[item.prop])"
              />

              <!-- 下拉选择 -->
              <c7-select
                  v-else-if="item.type === 'select'"
                  v-model="localModel[item.prop]"
                  :dict-type="item.dictType"
                  :placeholder="item.placeholder || '请输入' + item.label"
                  :data-list="item.dataList"
                  @change="handleChange(item, localModel[item.prop])"
              />

              <!-- 级联选择 -->
              <c7-cascader
                  v-else-if="item.type === 'cascader'"
                  v-model="localModel[item.prop]"
                  :result-type="3"
                  :placeholder="item.placeholder || '请输入' + item.label"
                  :data-list="item.dataList"
                  @change="handleChange(item, localModel[item.prop])"
              />

              <!-- 日期选择器 -->
              <c7-date-picker
                  v-else-if="datePickerTypes.includes(item.type)"
                  v-model="localModel[item.prop]"
                  :type="item.type"
                  :value-format="item.valueFormat"
                  :placeholder="item.placeholder || '请输入' + item.label"
                  :format="item.format"
                  :start-placeholder="item.startPlaceholder"
                  :end-placeholder="item.endPlaceholder"
                  :default-value="item.defaultValue"
                  :default-timer="item.defaultTime"
                  :range-separator="item.rangeSeparator"
                  :style="'width:' + (item.columnsWidth || props.columnsWidth)"
                  @change="handleChange(item, localModel[item.prop])"
              />
            </el-form-item>
          </el-col>

          <!-- 操作按钮 -->
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleSearch">搜索</el-button>
            <el-button icon="Refresh" @click="handleReset">重置</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { IButton, IColumn } from './search.js'
import { ref, defineOptions, PropType, computed, watch } from 'vue'

const emit = defineEmits(['update:modelValue', 'handleSearch', 'handleReset', 'addBtnHandle', 'deleteHandle'])

defineOptions({
  name: 'c7TableSearch',
  inheritAttrs: false
})

// 日期选择器支持的类型列表
const datePickerTypes = ref([
  'year', 'years', 'month', 'months', 'date', 'dates', 'datetime', 'week', 'datetimerange', 'daterange', 'monthrange', 'yearrange'
])

// 定义 Props
const props = defineProps({
  refName: {
    type: String,
    default: 'queryRef'
  },
  labelWidth: {
    type: String,
    default: '100px'
  },
  columns: {
    type: Array as PropType<IColumn[]>,
    default: () => []
  },
  modelValue: {
    type: Object as PropType<Record<string, any>>,
    default: () => ({})
  },
  columnsWidth: {
    type: String,
    default: '100%'
  },
  buttons: {
    type: Object as PropType<{
      enable: boolean,
      addBtn: IButton,
      deleteBtn: IButton
    }>,
    default: () => ({
      enable: true,
      addBtn: {
        enable: true,
        label: '新增',
        display: true,
        disabled: false,
        type: 'primary',
        icon: 'plus'
      },
      deleteBtn: {
        enable: true,
        label: '删除',
        display: true,
        disabled: false,
        type: 'danger',
        icon: 'Delete'
      }
    })
  }
})

// 内部响应式变量 localModel
const localModel = ref({ ...props.modelValue })

// 监听 props.modelValue 的变化，同步到 localModel
watch(
    () => props.modelValue,
    (newVal) => {
      // 避免直接修改 localModel，使用浅拷贝
      if (JSON.stringify(newVal) !== JSON.stringify(localModel.value)) {
        localModel.value = { ...newVal }
      }
    },
    { deep: true }
)

// 监听 localModel 的变化，触发 update:modelValue 事件
watch(
    localModel,
    (newVal) => {
      // 避免直接修改 props.modelValue，使用浅拷贝
      if (JSON.stringify(newVal) !== JSON.stringify(props.modelValue)) {
        emit('update:modelValue', { ...newVal })
      }
    },
    { deep: true }
)

/**
 * change 事件处理
 */
const handleChange = (item?: IColumn, value?: any) => {
  if (item?.change && typeof item.change === 'function') {
    item.change(item, value)
  }
}

// 根据 order 排序 columns
const sortedColumns = computed(() => {
  const columns = props.columns.slice().sort((a, b) => a.order - b.order)
  columns.forEach(c => c.display = true)
  return columns
})

// 按钮事件
const handleSearch = () => emit('handleSearch')
const handleReset = () => {
  localModel.value = {}
  emit('update:modelValue', {})
  emit('handleReset')
}
</script>

<style scoped>
.form-layout-container {
  padding: 20px;
}

/* 可以根据需求添加更多样式 */
</style>
