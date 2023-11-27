package io.github.rainsoil.fastapi2.system.service.impl;

import io.github.rainsoil.fastapi2.system.entity.FlywaySchemaHistory;
import io.github.rainsoil.fastapi2.system.mapper.FlywaySchemaHistoryMapper;
import io.github.rainsoil.fastapi2.system.service.IFlywaySchemaHistoryService;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@RequiredArgsConstructor
@Service
public class FlywaySchemaHistoryServiceImpl extends BaseServiceImpl<FlywaySchemaHistoryMapper, FlywaySchemaHistory> implements IFlywaySchemaHistoryService {

}
