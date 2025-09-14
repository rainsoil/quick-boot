<template>

  <c7-upload upload-url="https://apifoxmock.com/m1/4742948-4395863-default/mockapi/upload"
             v-model="pic",
             file-type="mp4"

             file-size="1000"
             :headers="headers"
             delete-url="xxxx"

  ></c7-upload>
  <span>{{pic}}</span>

</template>
<script setup>
import {ref, watch} from 'vue'
const headers = ref({
  'Authorization': 'Bearer xxxx'
})

const pic = ref("")

</script>
