<!--form组件 -->

<template>

  <div class="app-container  ">


    <c7-title label="ep-title标签" text-size="h1"></c7-title>

    <c7-title label="普通用法" text-size="h3"></c7-title>
    <c7-title label="ep-title标签">
      <c7-button type="success" @click="alertMsg('点击事件')" click-event label="点击触发前端事件"></c7-button>
      <c7-button type="danger" url="/mockapi/crud/list" :datas="buttonClickRef" label="点击触发后端接口"
                 successMessage="后端接口返回成功"></c7-button>
    </c7-title>


    <c7-title label="不展示色块" text-size="h3"></c7-title>
    <c7-title label="ep-title标签-不展示色块" :is-show-color-block="false">

    </c7-title>

    <c7-title label="ep-title标签-修改色块颜色" color-block-color="#E65D6E" text-size="h1" :is-bold="false">

    </c7-title>

    <c7-title label="ep-title标签-修改色块颜色" color-block-color="#E65D6E" text-size="h2">

    </c7-title>


    <!-- c7-button按钮-->

    <c7-button label="触发前端事件" click-event @click="alertMsg('点击事件')"></c7-button>

    <c7-button label="触发后端接口" url="/mockapi/crud/list" :datas="buttonClickRef"

               successMessage="后端接口返回成功"></c7-button>

    <c7-button label="触发后端接口,自定义回调" url="/mockapi/crud/list" :datas="buttonClickRef"

               is-success-callback @success-callback="buttonSuccessCallback"></c7-button>

    <c7-button label="触发后端接口并启用confim" url="/mockapi/crud/list" :datas="buttonClickRef"
               confirm confirm-message="确认调用当前接口吗？"
               successMessage="后端接口返回成功"></c7-button>

    <c7-button label="跳转新的路由地址" href="/demo/crud"></c7-button>
    <!-- 卡片组件-->


    <div>
      <c7-card label="card标题" text-size="h1">


        <el-form>
          <el-row>
            <el-form-item prop="name" label="名称">
              <el-input v-model="cardFormRef.name" placeholder="请输入活动名称"></el-input>
            </el-form-item>
            <el-form-item prop="name" label="年龄">
              <el-input v-model="cardFormRef.age" placeholder="请输入年龄"></el-input>
            </el-form-item>
          </el-row>

        </el-form>

      </c7-card>
      <c7-button label="提交" click-event @click="cardFormSubmit"></c7-button>


      <h2>form表单</h2>
      <el-form>

        <el-row>

          <el-col :span="12">
            <el-form-item prop="name" label="名称">
              <el-input v-model="formRef.name" placeholder="请输入名称"></el-input>
            </el-form-item>
          </el-col>
          <c7-input label="名称" v-model="formRef.name" :span="8" label-width="140px"
                    placeholder="请输入名称"></c7-input>
        </el-row>
      </el-form>
    </div>
  </div>


</template>

<script setup>
import c7Card from "@/components/chu7/c7-card/index.vue"
import C7Button from "@/components/chu7/c7-button/index.vue";
import C7Title from "@/components/chu7/c7-title/index.vue";
import C7Input from "@/components/chu7/c7-input/index.vue"

const alertMsg = (msg) => {
  alert(msg)
};

const buttonClickRef = ref({
  id: 111
})

const cardFormRef = ref({
  name: '',
  age: ""
})


// ep-button 访问后台接口 成功回调
const buttonSuccessCallback = (res) => {
  console.log(res)
  alert(JSON.stringify(res))
}

// card的提交
const cardFormSubmit = () => {
  alertMsg(JSON.stringify(cardFormRef.value))
}

// form表单
const formRef = ref({
  name: '',
  age: ""
})
</script>

<style scoped>

</style>
