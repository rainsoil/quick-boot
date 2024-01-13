package io.github.rainsoil.fastboot.common.data.mybatis;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseServiceImpl;
import io.github.rainsoil.fastboot.common.bean.BeanConvertUtils;
import io.github.rainsoil.fastboot.common.core.PageInfo;
import io.github.rainsoil.fastboot.common.core.PageRequest;

import java.util.Map;

/**
 * 基础Service实现
 *
 * @param <T> 实体类泛型
 * @param <M> mapper类
 * @author luyanan
 * @since 2023/09/16
 **/
public class BaseServiceImpl<M extends BaseBaseMapper<T>, T> extends MPJBaseServiceImpl<M, T> implements IBaseService<T> {
	@Override
	public PageInfo<T> page(PageRequest<T> pageRequest, PageHandler<T> pageHandler) {
		IPage iPage = getPage(pageRequest);
		LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();
		T param = pageRequest.getParam();
		if (null == pageHandler) {
			pageHandler = new PageHandler<T>() {
			};
		}
		pageHandler.queryWrapperHandler(param, queryWrapper);
		queryWrapper.setEntity(param);

		IPage resultPage = this.page(iPage, queryWrapper);
		return pageHandler.getPageInfo(resultPage);
	}

	@Override
	public <V> PageInfo<V> pageVo(PageRequest pageRequest, Class<V> voClass, PageVoHandler<T, V> pageVoHandler) {
		IPage<T> page = getPage(pageRequest);
		LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();
		V v = pageVoHandler.initVo(voClass);
		Object param = pageRequest.getParam();
		T t = BeanConvertUtils.instantiateClass(currentModelClass());
		if (null != param) {

			if (param instanceof Map) {
				t = BeanUtil.fillBeanWithMap((Map<?, ?>) param, t, false);
			} else {
				T finalT = t;
				t = BeanConvertUtils.convertTo(pageRequest.getParam(), () -> finalT);
			}
		}
		queryWrapper.setEntity(t);
		pageVoHandler.queryWrapperHandler(v, t, queryWrapper);
		IPage<T> resultPage = this.page(page, queryWrapper);
		return pageVoHandler.getPageInfo(resultPage, voClass);
	}


}
