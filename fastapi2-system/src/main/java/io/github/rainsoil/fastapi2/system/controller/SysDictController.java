package io.github.rainsoil.fastapi2.system.controller;

import io.github.rainsoil.fastapi2.common.core.R;
import io.github.rainsoil.fastapi2.system.entity.SysDict;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字段表 前端控制器
 *
 * @author luyanan
 * @since 2023-09-27
 */
@Tag(name = "字典管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysDict")
public class SysDictController {

    /**
     * 根据id查询
     *
     * @return
     * @since 2023/10/06
     */
    @Operation(summary = "根据id查询")
    @GetMapping
    public R getById() {
        return R.ok("成功");
    }

    /**
     * 详情
     *
     * @return 字典值
     * @since 2023/10/06
     */
    @Operation(summary = "详情")
    @GetMapping("info")
    public SysDict info() {
        return new SysDict()
                .setId(1L)
                .setType("AAA");
    }

}
