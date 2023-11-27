package io.github.rainsoil.fastapi2.system.mapper;

import io.github.rainsoil.fastapi2.system.entity.SysRole;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统角色表 Mapper 接口
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@Mapper
public interface SysRoleMapper extends BaseBaseMapper<SysRole> {

}
