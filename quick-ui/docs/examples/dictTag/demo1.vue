<!--

tag demo
-->

<template>

  <c7-dict-tag
      :options="dictList" v-model="defaultValue"
  />

  <c7-dict-tag
      :options="dictList" v-model="primaryValue"
  />

  <c7-dict-tag
      :options="dictList" v-model="successValue"
  />

  <c7-dict-tag
      :options="dictList" v-model="infoValue"
  />

  <c7-dict-tag
      :options="dictList" v-model="warningValue"
  />

  <c7-dict-tag
      :options="dictList" v-model="dangerValue"
  />

</template>

<script setup>
import {c7DictTag} from 'c7-plus'
import {ref} from 'vue'

const defaultValue = ref('1')

const primaryValue = ref('2')
const successValue = ref('3')
const infoValue = ref('4')
const warningValue = ref('5')
const dangerValue = ref('6')


const dictList = ref([
  {
    label: 'default',
    value: '1',
    elTagType: 'default'
  },
  {
    label: 'primary',
    value: '2',
    elTagType: 'primary'
  },
  {
    label: 'success',
    value: '3',
    elTagType: 'success'
  },
  {
    label: 'info',
    value: '4',
    elTagType: 'info'
  },
  {
    label: 'warning',
    value: '5',
    elTagType: 'warning'
  },
  {
    label: 'danger',
    value: '6',
    elTagType: 'danger'
  }
])

</script>


<style scoped>

</style>
