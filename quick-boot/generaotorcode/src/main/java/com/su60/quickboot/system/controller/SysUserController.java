package com.su60.quickboot.system.controller;

import cn.t200.quickboot.common.core.PageInfo;
import cn.t200.quickboot.common.core.PageRequest;
import cn.t200.quickboot.data.mybatisplus.PageVoHandler;
import cn.t200.quickboot.common.validation.AddGroup;
import cn.t200.quickboot.common.validation.UpdateGroup;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.su60.quickboot.system.dos.SysUserDo;
import com.su60.quickboot.system.entity.SysUserEntity;
import com.su60.quickboot.system.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;
import java.util.List;

/**
*
*用户信息表
*
*
* @author luyanan
* @since 2025/09/18
*/
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysuser")

 public class SysUserController {


  private final ISysUserService sysUserService;

    /**
    * 分页查询
    *
    * @param sysUserDo 分页参数
    * @return 分页结果
    * @since  2025/09/18
    */
    @SaCheckPermission("system:sysuser:list")
    @GetMapping("list")
    public PageInfo<SysUserDo> page(SysUserDo sysUserDo) {

        return sysUserService.page(sysUserDo, new PageVoHandler<SysUserEntity, SysUserDo>() {
        @Override
        public void queryWrapperHandler(SysUserDo vo, SysUserEntity sysUserEntity, LambdaQueryWrapper<SysUserEntity> queryWrapper) {


}



        });

        }

                    /**
                    * 保存
                    *
                    * @param sysUserDo 用户信息表
                    * @return 是否成功
                    * @since  2025/09/18
                    */
                    @SaCheckPermission("system:sysuser:add")
                    @PostMapping()
                    public Boolean save(@RequestBody @Validated(AddGroup.class) SysUserDo sysUserDo) {
                    return sysUserService.saveVo(sysUserDo);
                    }


                    /**
                    * 根据id修改
                    *
                    * @param sysUserDo 用户信息表
                    * @return 是否成功
                    * @since 2024/06/29
                    */
                    @SaCheckPermission("system:sysuser:edit")
                    @PutMapping
                    public Boolean updateById(@RequestBody @Validated(UpdateGroup.class) SysUserDo sysUserDo) {
                    return sysUserService.updateVoById(sysUserDo);
                    }


                    /**
                    * 根据id查询
                    *
                    * @param id id
                    * @return 用户信息表
                    * @since  2025/09/18
                    */
                    @SaCheckPermission("system:sysuser:query")
                    @GetMapping("/{id}")
                    public SysUserDo getById(@PathVariable("id") Long id) {
                    return sysUserService.getVoById(id);
                    }


                    /**
                    * 根据ids 删除
                    *
                    * @param ids 多个以英文逗号(,)分割
                    * @return 是否成功
                    * @since  2025/09/18
                    */
                    @SaCheckPermission("system:sysuser:remove")
                    @DeleteMapping()
                    public Boolean deleteByIds(@RequestBody List<Long> ids) {
                    return sysUserService.deleteByIds(ids);
                    }
}