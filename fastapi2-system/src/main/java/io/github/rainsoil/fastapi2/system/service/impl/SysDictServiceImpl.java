package io.github.rainsoil.fastapi2.system.service.impl;

import io.github.rainsoil.fastapi2.system.entity.SysDict;
import io.github.rainsoil.fastapi2.system.mapper.SysDictMapper;
import io.github.rainsoil.fastapi2.system.service.ISysDictService;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@RequiredArgsConstructor
@Service
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

}
