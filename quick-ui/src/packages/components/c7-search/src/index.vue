<template>
  <div class="form-layout-container">
    <div ref="formMainLeft">
      <el-form :ref="props.refName" :inline="true" :label-width="props.labelWidth">
        <el-row>
          <el-col :span="item.span?item.span:12" v-for="(item, index) in sortedColumns" :key="index">

            <el-form-item :label="item.label" :prop="item.prop" :required="item.required" :rules="item.rules">
              <!-- 如果配置使用自定义卡槽，则渲染对应插槽 -->
              <template v-if="item.type === 'slot'">
                <slot :name="item.slotName || ('slot_' + item.prop)"
                      :item="item"
                      :modelValue="props.modelValue[item.prop]"

                      @change="handleChange(item, $event)">
                </slot>
              </template>
              <!-- input输入框-->
              <el-input
                  v-if="(item.type === 'input' || !item.type) && item.display"
                  v-model="props.modelValue[item.prop]"
                  :placeholder="item.placeholder?item.placeholder:'请输入'+item.label"
                  clearable
                  @change="handleChange(item,props.modelValue[item.prop])"
              />
              <!-- 下拉-->
              <c7-select v-if="item.type === 'select'" v-model="props.modelValue[item.prop]" :dict-type="item.dictType"
                         :placeholder="item.placeholder?item.placeholder:'请输入'+item.label"
                         :data-list="item.dataList"
                         @change="handleChange(item,props.modelValue[item.prop])"

              ></c7-select>
              <!-- 级联-->
              <c7-cascader v-if="item.type === 'cascader'" v-model="props.modelValue[item.prop]"
                           :result-type="3"
                           :placeholder="item.placeholder?item.placeholder:'请输入'+item.label"
                           :data-list="item.dataList"
                           @change="handleChange(item,props.modelValue[item.prop])"

              ></c7-cascader>

              <!--date -->
              <c7-date-picker
                  v-if="datePickerTypes.indexOf(item.type)>0"
                  v-model="props.modelValue[item.prop]"
                  :type="item.type"
                  :value-format="item.valueFormat"
                  :placeholder="item.placeholder?item.placeholder:'请输入'+item.label"
                  :format="item.format"
                  :start-placeholder=item.startPlaceholder
                  :end-placeholder=item.endPlaceholder
                  :default-value="item.defaultValue"
                  :default-timer="item.defaultTime"
                  :range-separator=item.rangeSseparator
                  @change="handleChange(item,props.modelValue[item.prop])"
                  :style="'width:'+ (item.columnsWidth?item.columnsWidth:props.columnsWidth) "
              />
              <!-- datetime-->
              <!--其他   动态组件渲染，根据 JSON 配置的组件名称-->
              <!-- 动态组件渲染，根据 JSON 配置的组件名称 -->
              <!-- 动态渲染组件，并使用 v-bind 将所有 props 一次性传入 -->

            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleSearch()">搜索</el-button>
          <el-button icon="Refresh" @click="handleReset()">重置</el-button>
        </el-form-item>
      </el-form>
    </div>



  </div>

</template>
<script setup lang="ts">
import { IButton, IColumn, IColumnEnum} from './search.js'
import {ref, defineOptions, PropType, computed} from 'vue'

const emit = defineEmits(['update:modelValue', "handleSearch", "handleReset", "addBtnHandle", "deleteHandle", "exportHandle"])

defineOptions({
  name: 'c7TableSearch',
  inheritAttrs: false
})

const datePickerTypes = ref([
  'year', 'years', 'month', 'months', 'date', 'dates', 'datetime', 'week', 'datetimerange', 'daterange', 'monthrange', 'yearrange'
])

// 定义组件 Props
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

})


/**
 * change事件
 * @param item
 * @param value
 */
const handleChange = (item?: IColumn, value?: string) => {
  if (item.change && typeof item.change === 'function') {
    item.change(item, value);

  }
}

// 根据 order 排序：order 数值越大，排列越靠前
const sortedColumns = computed(() => {
  let colums = props.columns.slice().sort((a, b) => a.order - b.order)

  colums.forEach(c => {
    c.display = true;
  })
  return colums;
})



</script>
