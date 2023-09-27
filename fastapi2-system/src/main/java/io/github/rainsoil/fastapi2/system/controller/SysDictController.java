package io.github.rainsoil.fastapi2.system.controller;

import io.github.rainsoil.fastapi2.common.core.PageInfo;
import io.github.rainsoil.fastapi2.common.core.PageRequest;
import io.github.rainsoil.fastapi2.common.core.R;
import io.github.rainsoil.fastapi2.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastapi2.system.entity.SysDict;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 字段表 前端控制器
 *
 * @author luyanan
 * @since 2023-09-27
 */
@Tag(name = "用户管理", description = "用户数据增删改查")

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysDict")
public class SysDictController {

    @Operation(summary = "根据id查询")
    public R getById() {


        return R.ok();
    }

}
