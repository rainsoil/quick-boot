package ${packag}.controller;

import cn.t200.quickboot.common.core.PageInfo;
import cn.t200.quickboot.common.core.PageRequest;
import cn.t200.quickboot.data.mybatisplus.PageVoHandler;
import cn.t200.quickboot.common.validation.AddGroup;
import cn.t200.quickboot.common.validation.UpdateGroup;
import org.springframework.security.access.prepost.PreAuthorize;
import ${packag}.dos.${className}Do;
import ${packag}.entity.${className}Entity;
import ${packag}.service.I${className}Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;
import java.util.List;

/**
*
*${tableComment!}
*
*
* @author ${author}
* @since ${date}
*/
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("<#if moduleName?? && moduleName != "">/${moduleName}</#if>/${className ?lower_case}")

 public class ${className}Controller {


  private final I${className}Service ${className?uncap_first}Service;

    /**
    * 分页查询
    *
    * @param ${className?uncap_first}Do 分页参数
    * @return 分页结果
    * @since  ${date}
    */
<#if  tableEntity.verifyPermission == 'Y' >
    @PreAuthorize("hasAuthority('${moduleName!}:${className ?lower_case}:list')")
</#if>
    @GetMapping("list")
    public PageInfo<${className}Do> page(${className}Do ${className?uncap_first}Do) {

        return ${className?uncap_first}Service.page(${className?uncap_first}Do, new PageVoHandler<${className}Entity, ${className}Do>() {
        @Override
        public void queryWrapperHandler(${className}Do vo, ${className}Entity ${className?uncap_first}Entity, LambdaQueryWrapper<${className}Entity> queryWrapper) {


        <#list searchFields as searchField >
            <#if  searchField.queryType == 'LIKE' >
                queryWrapper.like(StrUtil.isNotBlank(vo.get${searchField.javaField?cap_first}()),${className}Entity::get${searchField.javaField?cap_first}, vo.get${searchField.javaField?cap_first}());
                ${className?uncap_first}Entity.set${searchField.javaField?cap_first}(null);
            </#if>
        </#list>
}



        });

        }

                    /**
                    * 保存
                    *
                    * @param ${className?uncap_first}Do ${tableComment!}
                    * @return 是否成功
                    * @since  ${date}
                    */
        <#if  tableEntity.verifyPermission == 'Y' >
                    @PreAuthorize("hasAuthority('${moduleName!}:${className ?lower_case}:add')")
        </#if>
                    @PostMapping()
                    public Boolean save(@RequestBody @Validated(AddGroup.class) ${className}Do ${className?uncap_first}Do) {
                    return ${className?uncap_first}Service.saveVo(${className?uncap_first}Do);
                    }


                    /**
                    * 根据id修改
                    *
                    * @param ${className?uncap_first}Do ${tableComment!}
                    * @return 是否成功
                    * @since 2024/06/29
                    */
        <#if  tableEntity.verifyPermission == 'Y' >
                    @PreAuthorize("hasAuthority('${moduleName!}:${className ?lower_case}:edit')")
        </#if>
                    @PutMapping
                    public Boolean updateById(@RequestBody @Validated(UpdateGroup.class) ${className}Do ${className?uncap_first}Do) {
                    return ${className?uncap_first}Service.updateVoById(${className?uncap_first}Do);
                    }


                    /**
                    * 根据id查询
                    *
                    * @param id id
                    * @return ${tableComment!}
                    * @since  ${date}
                    */
        <#if  tableEntity.verifyPermission == 'Y' >
                    @PreAuthorize("hasAuthority('${moduleName!}:${className ?lower_case}:query')")
        </#if>
                    @GetMapping("/{id}")
                    public ${className}Do getById(@PathVariable("id") Long id) {
                    return ${className?uncap_first}Service.getVoById(id);
                    }


                    /**
                    * 根据ids 删除
                    *
                    * @param ids 多个以英文逗号(,)分割
                    * @return 是否成功
                    * @since  ${date}
                    */
        <#if  tableEntity.verifyPermission == 'Y' >
                    @PreAuthorize("hasAuthority('${moduleName!}:${className ?lower_case}:remove')")
        </#if>
                    @DeleteMapping()
                    public Boolean deleteByIds(@RequestBody List<Long> ids) {
                    return ${className?uncap_first}Service.deleteByIds(ids);
                    }
}