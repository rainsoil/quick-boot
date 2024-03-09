package io.github.rainsoil.fastboot.generator.service.impl;

import io.github.rainsoil.fastboot.common.data.mybatis.BaseServiceImpl;
import io.github.rainsoil.fastboot.generator.entity.GDatasource;
import io.github.rainsoil.fastboot.generator.mapper.GDatasourceMapper;
import io.github.rainsoil.fastboot.generator.service.IGDatasourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 数据源管理 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024-01-16
 */
@RequiredArgsConstructor
@Service
public class GDatasourceServiceImpl extends BaseServiceImpl<GDatasourceMapper, GDatasource> implements IGDatasourceService {

}
