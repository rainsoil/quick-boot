package io.github.rainsoil.fastapi2.system.mapper;

import io.github.rainsoil.fastapi2.system.entity.SysUserRole;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@Mapper
public interface SysUserRoleMapper extends BaseBaseMapper<SysUserRole> {

}
