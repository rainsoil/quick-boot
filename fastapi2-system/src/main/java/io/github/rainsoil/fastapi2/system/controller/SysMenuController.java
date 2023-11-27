package io.github.rainsoil.fastapi2.system.controller;

import io.github.rainsoil.fastapi2.common.core.PageInfo;
import io.github.rainsoil.fastapi2.common.core.PageRequest;
import io.github.rainsoil.fastapi2.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastapi2.system.entity.SysMenu;
import io.github.rainsoil.fastapi2.system.service.ISysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 系统菜单表 前端控制器
 *
 * @author luyanan
 * @since 2023-11-27
 */
@Tag(name = "系统菜单表", description = "系统菜单表")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysMenu")
public class SysMenuController {

    private final ISysMenuService iSysMenuService;

    /**
     * 分页
     *
     * @param pageRequest 分页参数
     * @return 分页返回
     * @since 2023-11-27
     */
    @Operation(summary = "分页")
    @PostMapping("page")
    public PageInfo<SysMenu> page(@RequestBody PageRequest<SysMenu> pageRequest) {
        PageInfo<SysMenu> pageInfo = iSysMenuService.page(pageRequest, new PageHandler<SysMenu>() {
        });
        return pageInfo;
    }

    /**
     * 保存
     *
     * @param sysMenu 实体类
     * @return 是否成功
     * @since 2023-11-27
     */
    @PostMapping("")
    @Operation(summary = "保存")
    public Boolean save(@RequestBody SysMenu sysMenu) {
        return this.iSysMenuService.save(sysMenu);
    }

    /**
     * 修改
     *
     * @param sysMenu 实体类
     * @return 是否成功
     * @since 2023-11-27
     */
    @Operation(summary = "修改")
    @PutMapping("")
    public Boolean update(@RequestBody SysMenu sysMenu) {
        return this.iSysMenuService.updateById(sysMenu);

    }

    /**
     * 根据id删除
     *
     * @param id id
     * @return 是否成功
     * @since 2023-11-27
     */
    @DeleteMapping()
    @Operation(summary = "根据id删除")
    public Boolean remove(@RequestParam(value = "id", required = true) Long id) {
        return this.iSysMenuService.removeById(id);
    }

}
