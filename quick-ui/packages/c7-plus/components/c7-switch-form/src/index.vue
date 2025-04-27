<template>
  <div class="app-container">

    <div v-for="(item, index) in showIndexs" :key="index">
      <div v-if="modelValue == item.name">
        <el-page-header @back="close(item.closeIndex)" v-if="item.header">
          <template #content>
            <span class="text-large font-600 mr-3"> {{item.title}} </span>
          </template>
          <div class="mt-4 text-sm font-bold">
            <slot :name="item.name" v-if="modelValue == item.name"></slot>
          </div>
        </el-page-header>
<!--        <c7-layer :title="item.title" @close="close(item.closeIndex)" v-if="item.header">
          <slot :name="item.name" v-if="modelValue == item.name"></slot>
        </c7-layer>-->
        <slot :name="item.name" v-if="modelValue == item.name && !item.header"></slot>
      </div>
    </div>

  </div>
</template>
<script setup>
import {defineEmits, defineProps, defineOptions} from 'vue'
import { ArrowLeft } from '@element-plus/icons-vue'

defineOptions({
  name: 'c7SwitchForm',
})
const emit = defineEmits(['update:modelValue']);
const props = defineProps({
  showIndexs: {
    type: Array,
    default: [],
    required: true
  },
  modelValue: {
    type: [String],
    default: "",
  },
})

const close = (closeIndex) => {

  emit("update:modelValue", closeIndex);
}

</script>
