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
          v-for="item in dictMap.sex"
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
          v-for="item in dictMap.zhuanye"
          :key="item.value"
          :label="item.label"
          :value="item.value"
      />
    </el-select>
    <p>选中的值:{{ select2 }}</p>

    <p>--------------</p>
    <!-- 将div的内容循环10次-->
    <div v-for="i in 10" :key="i">
      <div v-for="item in dictList" :key="item.value">
        {{ item.label }} ---- {{ dictHook().getDictByValue("sex", '1') }}
      </div>
      <p>--------------</p>
    </div>
  </div>

</template>

<script setup lang="ts">
import {ref, computed, watch, onMounted} from 'vue'
import {dictHook} from "c7-plus";

const dictList = dictHook().getDict("sex");

const dictMap = ref(dictHook().getDicts(["sex", "zhuanye"]));

const select1 = ref("")


const select2 = ref("")

</script>
