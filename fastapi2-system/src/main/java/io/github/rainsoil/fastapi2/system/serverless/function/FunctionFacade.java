package io.github.rainsoil.fastapi2.system.serverless.function;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import io.github.rainsoil.fastapi2.common.core.PageInfo;
import io.github.rainsoil.fastapi2.common.core.PageRequest;
import io.github.rainsoil.fastapi2.common.data.mybatis.IBaseService;
import io.github.rainsoil.fastapi2.common.exception.WarningException;
import io.github.rainsoil.fastapi2.common.spring.SpringContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 函数调用门面
 *
 * @author luyanan
 * @since 2023/12/27
 **/
@Component
public class FunctionFacade {


	public Object invoke(FunctionRequest request) {
//		sysDictServiceImpl
		String beanName = request.getFunctionTarget() + "ServiceImpl";

		Object bean = SpringContextHolder.getBean(beanName);
		if (null == bean || !(bean instanceof IBaseService)) {
			return null;
		}
		IBaseService baseService = (IBaseService) bean;
		// 方法
		String functionMethod = request.getFunctionMethod();
		// 参数
		List<String> functionParams = request.getFunctionParams();

		// 分页
		if (functionMethod.equals("get")) {
			PageRequest pageRequest = null;
			if (CollectionUtil.isEmpty(functionParams)) {
				pageRequest = new PageRequest();
				pageRequest.setCurrent(1L);
				pageRequest.setSize(10);
			} else {
				pageRequest = JSON.parseObject(functionParams.get(0), PageRequest.class);

			}
			PageInfo page = baseService.page(pageRequest);
			return page;
		}
		if (functionMethod.equals("save")) {
			// 保存
			if (CollectionUtil.isEmpty(functionParams)) {
				throw new WarningException("参数不能为空");
			}
			String paramStr = functionParams.get(0);
			Class aClass = baseService.currentModelClass();
			Object entity = JSON.parseObject(paramStr, aClass);
			boolean save = baseService.save(entity);
			return save;
		}
		// 修改
		if (functionMethod.equals("update")) {
			if (CollectionUtil.isEmpty(functionParams)) {
				throw new WarningException("参数不能为空");
			}
			String paramStr = functionParams.get(0);
			Class aClass = baseService.currentModelClass();
			Object entity = JSON.parseObject(paramStr, aClass);
			boolean update = baseService.updateById(entity);
			return update;
		}
		// 删除

		if (functionMethod.equals("delete")) {
			if (CollectionUtil.isEmpty(functionParams)) {
				throw new WarningException("参数不能为空");
			}
			TableInfo tableInfo = TableInfoHelper.getTableInfo(baseService.getEntityClass());
			List<?> ids = JSON.parseArray(JSON.toJSONString(functionParams), tableInfo.getKeyType());
			boolean b = baseService.removeByIds(ids, true);
			return b;
		}

		if (functionMethod.equals("list")) {
			// 列表获取数据
			if (CollectionUtil.isEmpty(functionParams)) {
				throw new WarningException("参数不能为空");
			}
			String paramStr = functionParams.get(0);
			Class aClass = baseService.currentModelClass();
			Object entity = JSON.parseObject(paramStr, aClass);

			List list = baseService.list(new LambdaQueryWrapper(entity));
			return list;
		}
		if (functionMethod.equals("info")) {
			// 根据id查询数据
			// 列表获取数据
			if (CollectionUtil.isEmpty(functionParams)) {
				throw new WarningException("参数不能为空");
			}
			String paramStr = functionParams.get(0);
			Object byId = baseService.getById(paramStr);
			return byId;
		}

		return null;
	}


}
