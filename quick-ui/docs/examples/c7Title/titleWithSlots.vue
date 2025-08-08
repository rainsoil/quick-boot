<template>
  <div>
    <c7-title label="带操作按钮的标题" label-color="#4CAF50">
      <el-button type="primary">新增</el-button>
      <el-button type="success">查询</el-button>
      <el-button type="info">重置</el-button>
    </c7-title>
    
    <c7-title label="带搜索框的标题" label-size="h3" label-color="#409eff">
      <el-input 
        v-model="searchText" 
        placeholder="请输入搜索内容" 
        style="width: 200px;"
        clearable>
        <template #append>
          <el-button type="primary">搜索</el-button>
        </template>
      </el-input>
    </c7-title>
  </div>
</template>
<script setup>
import {c7Title} from "c7-plus";
import {ref} from 'vue';

const searchText = ref('');
</script>
