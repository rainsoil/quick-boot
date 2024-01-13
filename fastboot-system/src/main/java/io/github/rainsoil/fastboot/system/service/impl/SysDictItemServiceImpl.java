package io.github.rainsoil.fastboot.system.service.impl;

import io.github.rainsoil.fastboot.system.entity.SysDictItem;
import io.github.rainsoil.fastboot.system.mapper.SysDictItemMapper;
import io.github.rainsoil.fastboot.system.service.ISysDictItemService;
import io.github.rainsoil.fastboot.common.data.mybatis.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
/**
 * <p>
 * 字典项表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@RequiredArgsConstructor
@Service
public class SysDictItemServiceImpl extends BaseServiceImpl<SysDictItemMapper, SysDictItem> implements ISysDictItemService {

}
