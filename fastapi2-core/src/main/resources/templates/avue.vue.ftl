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
                    apiPrefix: '/${entity?uncap_first}',
                    // 是否云原生
                    functionTarget: '${entity?uncap_first}',
                    tableOption: {
                        column: [
                            <#list table.fields as field>
                            <#if field.keyIdentityFlag>
                            <#elseif field.convert>
                            {
                                label: "${field.comment}",
                                prop: "${field.propertyName}",
                                search: false,
                                required: false
                            },

                            </#if>

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
