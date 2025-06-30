<template>

  <c7Button :clickFunction="buttonClickHandler" btn-type="add"></c7Button>

  <c7Button :clickFunction="buttonClickHandler" btn-type="edit"></c7Button>

  <c7Button :clickFunction="buttonClickHandler" btn-type="delete"></c7Button>

  <c7Button :clickFunction="buttonClickHandler" btn-type="query"></c7Button>

  <c7Button :clickFunction="buttonClickHandler" btn-type="refresh"></c7Button>

  <c7Button :clickFunction="buttonClickHandler" btn-type="upload"></c7Button>

  <c7Button :clickFunction="buttonClickHandler" btn-type="download">下载</c7Button>


</template>

<script setup>
import {ref} from 'vue'
import {c7Button} from "c7-plus";
import {apiClick, apiClick2} from './api.js'

const buttonClickHandler = () => {
  alert('点击了按钮')
}
</script>

<style scoped>

</style>
