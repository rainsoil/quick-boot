<template>
  <div class="app-container">

    <div v-for="(item, index) in showIndexs" :key="index">
      <div v-if="modelValue == item.name">
        <c7-layer :title="item.title" @close="close(item.closeIndex)" v-if="item.header">
          <slot :name="item.name" v-if="modelValue == item.name"></slot>
        </c7-layer>
        <slot :name="item.name" v-if="modelValue == item.name && !item.header"></slot>
      </div>
    </div>

  </div>
</template>
<script setup>

import {defineEmits, defineProps, defineOptions} from 'vue'

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
