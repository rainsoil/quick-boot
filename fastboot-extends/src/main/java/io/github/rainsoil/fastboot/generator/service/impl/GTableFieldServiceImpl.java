package io.github.rainsoil.fastboot.generator.service.impl;

import io.github.rainsoil.fastboot.common.data.mybatis.BaseServiceImpl;
import io.github.rainsoil.fastboot.generator.entity.GTableField;
import io.github.rainsoil.fastboot.generator.mapper.GTableFieldMapper;
import io.github.rainsoil.fastboot.generator.service.IGTableFieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 表字段实体类 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024-01-16
 */
@RequiredArgsConstructor
@Service
public class GTableFieldServiceImpl extends BaseServiceImpl<GTableFieldMapper, GTableField> implements IGTableFieldService {

}
