<!-- 字典值-->

<template>

  <div>

    <el-select
        v-model="select1"
        placeholder="Select"
        size="large"
        style="width: 240px"
    >
      <el-option
          v-for="item in dicts['sex']"
          :key="item.value"
          :label="item.label"
          :value="item.value"
      />
    </el-select>
    <p>选中的值:{{ select1 }}</p>

    <el-select
        v-model="select2"
        placeholder="Select"
        size="large"
        style="width: 240px"
    >
      <el-option
          v-for="item in dicts.zhuanye"
          :key="item.value"
          :label="item.label"
          :value="item.value"
      />
    </el-select>
    <p>选中的值:{{ select2 }}</p>

    <p>--------------</p>
    <!-- 将div的内容循环10次-->
    <div v-for="i in 10" :key="i">
      <div v-for="item in dicts.sex" :key="item.value">
        {{ item.label }} ---- {{ getDictByValue("sex", '1') }}
      </div>
      <p>--------------</p>
    </div>
  </div>

</template>

<script setup lang="ts">
import {ref, computed, watch, onMounted} from 'vue'
import {useDict} from "c7-plus";

const {dicts,getDictByValue} = useDict("sex", "zhuanye");

const select1 = ref("")


const select2 = ref("")

</script>
