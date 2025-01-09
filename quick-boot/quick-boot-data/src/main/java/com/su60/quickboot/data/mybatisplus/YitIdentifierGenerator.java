package com.su60.quickboot.data.mybatisplus;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.github.yitter.idgen.YitIdHelper;

/**
 * Yit生成策略
 *
 * @author luyanan
 * @since 2023/09/16
 **/
public class YitIdentifierGenerator implements IdentifierGenerator {
	@Override
	public Number nextId(Object entity) {
		return YitIdHelper.nextId();
	}
}
