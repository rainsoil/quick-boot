package com.su60.quickboot.data.cache.condition;

import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 缓存触发器
 *
 * @author luyanan
 * @since 2024/04/21
 **/
public class CacheCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Environment environment = context.getEnvironment();
		BindResult<CacheType> specified = Binder.get(environment).bind("spring.cache.type", CacheType.class);
		if (!specified.isBound()) {
			return false;
		}
		CacheType cacheType = specified.get();
		return cacheType.equals(CacheType.CAFFEINE);
	}
}
