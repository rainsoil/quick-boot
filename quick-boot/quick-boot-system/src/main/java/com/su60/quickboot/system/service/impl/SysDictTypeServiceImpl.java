package com.su60.quickboot.system.service.impl;

import com.su60.quickboot.common.exception.WarningException;
import com.su60.quickboot.system.entity.SysDictTypeEntity;
import com.su60.quickboot.system.dos.SysDictTypeDo;
import com.su60.quickboot.system.mapper.SysDictTypeMapper;
import com.su60.quickboot.system.service.ISysDictDataService;
import com.su60.quickboot.system.service.ISysDictTypeService;
import com.su60.quickboot.data.mybatisplus.BaseServiceImpl2;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024/08/07
 */
@RequiredArgsConstructor
@Service

public class SysDictTypeServiceImpl extends BaseServiceImpl2<SysDictTypeMapper, SysDictTypeEntity, SysDictTypeDo> implements ISysDictTypeService {

	private final ISysDictDataService sysDictDataService;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean deleteByIds(Collection<? extends Serializable> ids) {
		for (Serializable id : ids) {
			SysDictTypeEntity sysDictType = this.getById(id);
			if (null == sysDictType) {
				throw new WarningException(100010);
			}
			// 检查是否有未删除的字典项
			boolean has = sysDictDataService.hasData(sysDictType.getDictType());
			if (has) {
				throw new WarningException(100009, sysDictType.getDictType());
			}
		}
		return super.deleteByIds(ids);
	}


	@Override
	public boolean removeByIds(Collection<?> list) {

		return super.removeByIds(list);
	}

	@Override
	public List<SysDictTypeEntity> listAll() {
		return this.list();
	}
}

