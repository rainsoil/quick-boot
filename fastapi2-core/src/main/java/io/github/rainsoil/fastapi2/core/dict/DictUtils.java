package io.github.rainsoil.fastapi2.core.dict;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 *
 * @author luyanan
 * @since 2023/12/11
 **/
public class DictUtils {

	private DictUtils() {
		init();
	}

	public static void init() {

	}

	public static <E extends Enum<E>> void register(Class<? extends Enum<E>> dictEnums) {


		Enum<E>[] enumConstants = dictEnums.getEnumConstants();
		List<Dict> dicts = new ArrayList<>();
		for (Enum<E> enumConstant : enumConstants) {
			IDictEnums enums = (IDictEnums) enumConstant;
			String name = enums.getType().name();
			String value1 = enums.getValue();
			String label = enums.getLabel();
			Dict dict = new Dict()
					.setLabel(label)
					.setName(name)
					.setValue(value1);
			dicts.add(dict);
			List<Dict> cacheDict = cacheMap.get(name);
			if (CollectionUtil.isEmpty(cacheDict)) {
				cacheDict = new ArrayList<>();
			}
			cacheDict.add(dict);
			cacheMap.put(name, cacheDict);
		}


	}

	@Data
	@Accessors(chain = true)
	public static class Dict {
		private String name;
		private String label;
		private String value;
	}

	private static Map<String, List<Dict>> cacheMap = new HashMap<>();


	public static List<Dict> getDict(String type) {
		init();
		return cacheMap.get(type);
	}

	public static Dict getValue(String type, String value) {
		init();
		if (StrUtil.isBlank(type) || StrUtil.isBlank(value)) {
			return null;
		}
		return Opt.ofNullable(cacheMap.get(type)).map(a -> a.stream().filter(b -> b.getValue().equals(value)).findFirst().orElse(null)).orElse(null);
	}


}
