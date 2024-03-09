package io.github.rainsoil.fastboot.common.validation;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import io.github.rainsoil.fastboot.common.spring.SpringContextHolder;
import lombok.experimental.UtilityClass;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 获取消息的工具类
 *
 * @author luyanan
 * @since 2024/01/28
 **/
@UtilityClass
public class MessageUtils {


	/**
	 * 获取消息
	 *
	 * @param code 编码
	 * @param args 参数
	 * @return
	 * @since 2024/01/28
	 */
	public String getMessage(String code, Object... args) {
		if (StrUtil.isBlank(code)) {
			return code;
		}
		String reg = "\\{(.*?)\\}";
		if (code.matches(reg)) {

			String s = ReUtil.get(reg, code, 0);
			s = s.substring(1, s.length() - 1);
			MessageSource messageSource = SpringContextHolder.getBean(MessageSource.class);
			return messageSource.getMessage(s, args, LocaleContextHolder.getLocale());
		}
		return code;
	}


}
