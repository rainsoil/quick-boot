package com.su60.quickboot.generator.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.su60.quickboot.common.bean.BeanConvertUtils;
import com.su60.quickboot.generator.entity.GenTableColumnEntity;
import com.su60.quickboot.generator.dos.GenTableColumnDo;
import com.su60.quickboot.generator.mapper.GenTableColumnMapper;
import com.su60.quickboot.generator.service.IGenTableColumnService;
import com.su60.quickboot.data.mybatisplus.BaseServiceImpl2;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 代码生成业务表字段 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024/10/24
 */
@RequiredArgsConstructor
@Service

public class GenTableColumnServiceImpl extends BaseServiceImpl2<GenTableColumnMapper, GenTableColumnEntity, GenTableColumnDo> implements IGenTableColumnService {

	@Override
	public void saveBatch(List<GenTableColumnEntity> tableColumnEntities) {
		if (CollectionUtil.isEmpty(tableColumnEntities)) {
			return;
		}
		super.saveBatch(tableColumnEntities);
	}

	@Override
	public void deleteByTables(Collection<? extends Serializable> ids) {
		if (CollectionUtil.isEmpty(ids)) {
			return;
		}
		this.remove(new LambdaQueryWrapper<GenTableColumnEntity>()
				.in(GenTableColumnEntity::getTableId, ids));
	}

	@Override
	public void updateBatch(List<GenTableColumnDo> genTableColumnDos) {
		if (CollectionUtil.isEmpty(genTableColumnDos)) {
			return;
		}
		super.updateBatchById(BeanConvertUtils.convertListTo(genTableColumnDos, GenTableColumnEntity::new));
	}

	@Override
	public List<GenTableColumnEntity> listByTableIds(List<Long> tableIds) {
		if (CollectionUtil.isEmpty(tableIds)) {
			return new ArrayList<>();
		}
		return super.list(new LambdaQueryWrapper<GenTableColumnEntity>()
				.in(GenTableColumnEntity::getTableId, tableIds));
	}

    @Override
    public List<GenTableColumnEntity> listByTableId(Long tableId) {
		return super.list(new LambdaQueryWrapper<GenTableColumnEntity>()
				.eq(GenTableColumnEntity::getTableId, tableId));
    }
}

