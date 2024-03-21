# QuickBoot

## 简介

- QuickBoot是一个轻量级的、前后端分离的快速开发框架。
- 框架采用Springboot、MybatisPlus、Vue3、ElementPlus、Avue框架开发的一套权限管理系统，通过对前后端的一系列封装,可以达到基于json就可以完成简单crud功能

<br/>

## 具有如下特点:

- 友好的代码注释和规范
- 前后端分离,通过jwt进行数据交互。
- 灵活的权限控制，可以控制到菜单和按钮，甚至是字段。
- 后端对mybatisplus进行二次封装，代码简洁，Service直接实现即可完成增删改查方法
- 前端对ElementPlus和avue进行二次封装，前端页面只需要配置json文件即可满足百分之90的页面功能。
- 采用国密加密方式。

<br/>

<br/>

## 功能列表

- ~~用户管理~~
- ~~角色管理~~
- ~~菜单管理~~
- ~~字典管理~~
- 开发管理
  
  - 代码生成器管理
    - ~~数据源管理~~
    - 模板管理
    - ~~代码生成~~
    - 项目名修改
  - 文件管理
- ~~定时任务~~
- 新增权限管理框架为sa-token

<br/>

## 例子:

<br/>

### 角色管理

```
<template>
  <common-crud ref="CommonCrudTest" :crudOptionProps="crudOptionProps" @listAfter="listAfter"></common-crud>
</template>

<script>
import commonCrud from "@/components/commonCrud/CommonCrud.vue"
import commonCrudCompontentTest from "@/views/util/commonCrudCompontentTest.vue"
import {baseUrl} from "../../../config/env";

export default {
  name: "sysRole",
  components: {commonCrud},
  data() {
    return {

      // 表配置
      crudOptionProps: {
        name: 'sys/role',
        apiPrefix: '/sysRole',
        permissionOption:{
          enable:true,

        },
        // 是否云原生
        functionTarget: 'sysRole',
        tableOption: {
          column: [{
            label: "角色名称",
            prop: "roleName",
            search: true,
            required: true

          }, {
            label: "角色编码",
            prop: "roleCode",
            search: true,
            editDisabled: true,
            required: true
          },
            {
              label: "备注",
              prop: "remark",
              search: false,
            },
            {
              label: "菜单",
              prop: 'menus',
              type: 'tree',
              hide: true,
              multiple: true,
              // collapseTags: true,
              // maxCollapseTags: 3,
              // collapseTagsTooltip: true,
              leafOnly: true,
              parent: true,
              dicUrl: baseUrl + '/system/sysMenu/tree',
              dicMethod: "post",
            }
          ]
        },
        buttonList: [
          // {
          //   menuType: "menu",
          //   name: "路由跳转",
          //   icon: "View",
          //   mode: 'router',
          //   permission:'sys:menu:add',
          //   info: {url: '/simpleCrud', props: 'kfc', value: '19090902212'}
          // },
        ]

      }

    }
  }, created() {
    console.log("commoncrud created")
  }, computed() {
    return {
      'search-reset': this.searchChange2,
    }
  }, methods: {
    listAfter(data) {
      console.log("listAfter2", data)
    },

    addBefore2(form) {

      alert(JSON.stringify(form))
      console.log("addBefore")
    },
    addAfter2(data) {

      console.log(data)
      alert(JSON.stringify(data))
    },
    updateBefore2(form) {
      alert(JSON.stringify(form))
    },

  }
}
</script>

<style scoped>

</style>
```

<br/>

### 字典管理

```
<template>
  <common-crud ref="CommonCrudTest" :crudOptionProps="crudOptionProps"></common-crud>
</template>

<script>
import commonCrud from "@/components/commonCrud/CommonCrud.vue"

export default {
  name: "sysDict",
  components: {commonCrud},
  data() {
    return {
      // 表配置
      crudOptionProps: {
        name: 'sys/dict',
        apiPrefix: '/sysDict',
        // 是否云原生
        functionTarget: 'sysDict',
        permissionOption: {
          enable: true
        },
        tableOption: {
          menuWidth: 320,
          column: [

            {
              label: "字典类型",
              prop: "type",
              search: true,
              required: true,

            },


            {
              label: "字典描述",
              prop: "description",
              search: false,
              required: false,

            },


          ]
        },
        // 按钮
        buttonList: [

          {
            menuType: "menu",
            name: "字典项配置",
            icon: "View",
            mode: 'dialog',
            component: '../../views/admin/sysdict/sysDictItem',
            permission: "sys:dict:item",

            info: {
              prop: "id",
              // 组件标题
              title: ' 字典项配置',
              //是否初始化init方法
              isInit: true
            }
          }
        ]

      }

    }
  }, created() {

  }, computed() {

  }, methods: {}
}
</script>

<style scoped>

</style>
```

<br/>

## License

[MIT](http://opensource.org/licenses/MIT)

Copyright (c) 2017-present,QuickBoot

