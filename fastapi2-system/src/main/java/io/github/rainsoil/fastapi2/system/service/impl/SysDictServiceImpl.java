package io.github.rainsoil.fastapi2.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.rainsoil.fastapi2.system.entity.SysDict;
import io.github.rainsoil.fastapi2.system.entity.SysDictItem;
import io.github.rainsoil.fastapi2.system.mapper.SysDictMapper;
import io.github.rainsoil.fastapi2.system.service.ISysDictItemService;
import io.github.rainsoil.fastapi2.system.service.ISysDictService;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

	private final ISysDictItemService sysDictItemService;

	@Override
	public List<SysDictItem> getByType(String type, Boolean style) {

		List<SysDictItem> list = sysDictItemService.list(new LambdaQueryWrapper<SysDictItem>()
				.eq(SysDictItem::getType, type).orderByAsc(SysDictItem::getSort));
		if (style) {
			for (SysDictItem sysDictItem : list) {
				if (style && StrUtil.isNotBlank(sysDictItem.getStyle())) {
					sysDictItem.setLabel(sysDictItem.getLabel() + "(" + sysDictItem.getStyle() + ")");
				}
			}
		}

		return list;
	}
}
