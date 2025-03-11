<template>

  <c7-title label="预览图片"></c7-title>
  <c7-preview :urls="imageUrl"></c7-preview>

  <c7-title label="预览图片,并指定大小"></c7-title>

  <c7-preview :urls="imageUrl" width="100px" height="100px"></c7-preview>

  <c7-title label="预览文件"></c7-title>
  <c7-preview :urls="imageUrl" width="100px" height="100px" display-type="file"></c7-preview>


  <c7-title label="预览图片"></c7-title>
  <c7-preview :urls="imageUrl" cover-type="button"  button-text="预览"></c7-preview>


  <c7-title label="预览图片"></c7-title>
  <c7-preview :urls="imageUrl" cover-type="file"></c7-preview>


  <c7-title label="预览图片"></c7-title>
  <c7-preview :urls="imageUrl" cover-type="None"></c7-preview>
</template>

<script setup>
import {ref} from  'vue'
const imageUrl = ref("https://e-assets.gitee.com/gitee-community-web/_next/static/media/person.9b9c6c64.png," +
    "https://e-assets.gitee.com/gitee-community-web/_next/static/media/person.9b9c6c64.png,https://e-assets.gitee.com/gitee-community-web/_next/static/media/person.9b9c6c64.mp4")

</script>
