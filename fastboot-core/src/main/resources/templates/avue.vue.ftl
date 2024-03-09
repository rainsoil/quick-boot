<template>
    <common-crud ref="CommonCrudTest" :crudOptionProps="crudOptionProps"></common-crud>
</template>

<script>
    import commonCrud from "@/components/commonCrud/CommonCrud.vue"

    export default {
        name: "${entity?uncap_first}",
        components: {commonCrud},
        data() {
            return {
                // 表配置
                crudOptionProps: {
                    name: '${entity?uncap_first}/index',
                    apiPrefix: '/${tableInfo.interfacePrefix?uncap_first}',
                    permissions: ${tableInfo.verifyPermissions},
                    delBtn: ${tableInfo.delBtn},
                    editBtn: ${tableInfo.editBtn},
                    addBtn: ${tableInfo.addBtn},
                    // 是否云原生
                    //functionTarget: '${entity?uncap_first}',
                    tableOption: {
                        column: [
                            <#list tableInfo.tableFields as field>

                            {
                                label: "${field.columnComment}",
                                prop: "${field.attrName}",
                                // 是否为搜索项
                                search: ${(field.isSearch == '0') ? string('true', 'false')},
                                // 是否必填
                                required: ${(field.isRequired == '0') ? string('true', 'false')},
                                // 类型
                                type: ${field.pageType},
                                <#if field.dictType??>
                                // 字典类型
                                dictType: "${field.dictType}",
                                </#if>
                                <#if field.dicUrl??>
                                // 接口地址
                                dicUrl: "${field.dicUrl}",
                                </#if>
                                //表单编辑的时候是否显示
                                editDisplay: ${(field.editDisplay == '0') ? string('true', 'false')},
                                //表单新增的时候是否显示
                                addDisplay: ${(field.addDisplay == '0') ? string('true', 'false')},
                                //表单是否显示
                                display: ${(field.display == '0') ? string('true', 'false')},
                                // 列表是否显示
                                hide: ${(field.hide == '0') ? string('true', 'false')},
                            },

                            </#list>

                        ]
                    },

                }

            }
        }, created() {

        }, computed() {

        }, methods: {}
    }
</script>

<style scoped>

</style>
