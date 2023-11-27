package io.github.rainsoil.fastapi2.system.mapper;

import io.github.rainsoil.fastapi2.system.entity.SysUser;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@Mapper
public interface SysUserMapper extends BaseBaseMapper<SysUser> {

}
