package io.github.rainsoil.fastapi2.web.exception;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import io.github.rainsoil.fastapi2.common.core.R;
import io.github.rainsoil.fastapi2.common.exception.BaseException;
import io.github.rainsoil.fastapi2.common.exception.ErrorException;
import io.github.rainsoil.fastapi2.common.exception.GlobalMsgCode;
import io.github.rainsoil.fastapi2.common.exception.WarningException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 全局异常
 *
 * @author luyanan
 * @since 2023/11/29
 **/
@RequiredArgsConstructor
@Slf4j
@RestController
@RestControllerAdvice
public class GlobalExceptionHandler {
	private final MessageSource messageSource;


	/**
	 * 参数校验
	 *
	 * @param e 异常
	 * @return cn.com.genhao2.fastapi.common.core.R
	 * @since 2022/10/14
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R methodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();

		ObjectError objectError = allErrors.get(0);
		String defaultMessage = objectError.getDefaultMessage();
		Integer code = GlobalMsgCode.BAD_REQUEST;
		String msg = defaultMessage;
		if (NumberUtil.isNumber(defaultMessage)) {
			code = Integer.valueOf(defaultMessage);
			msg = getMessage(code, e, defaultMessage);
		}
		log.error("全局异常信息 code:{},ex={}", code, msg);
		return R.failed(code, msg);
	}

	/**
	 * 参数校验异常
	 *
	 * @param e 异常
	 * @return cn.com.genhao2.fastapi.common.core.R
	 * @since 2022/10/14
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(ConstraintViolationException.class)
	public R constraintViolationException(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
		StringBuilder builder = new StringBuilder();
		while (iterator.hasNext()) {
			ConstraintViolation<?> constraint = iterator.next();
			builder.append(constraint.getMessage());
			break;
		}
		String defaultMessage = builder.toString();
		Integer code = GlobalMsgCode.BAD_REQUEST;
		String msg = defaultMessage;
		if (NumberUtil.isNumber(defaultMessage)) {
			code = Integer.valueOf(defaultMessage);
			msg = getMessage(code, e, defaultMessage);
		}
		log.error("全局异常信息 code:{},ex={}", code, msg);
		return R.failed(code, msg);
	}

	/**
	 * 全局异常.
	 *
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(ErrorException.class)
	@ResponseStatus(HttpStatus.OK)
	public R errrorException(ErrorException e) {
		Integer code = e.getCode();

		String message = getMessage(code, e, null);

		log.error("全局异常信息 code:{},ex={}", code, message, e);
		return R.failed(code, message);
	}


	/**
	 * 全局异常.
	 *
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(WarningException.class)
	@ResponseStatus(HttpStatus.OK)
	public R warningException(WarningException e) {
		Integer code = e.getCode();

		String message = getMessage(code, e);

		log.warn("全局异常信息 code:{},ex={}", code, message);
		return R.failed(code, message);
	}

	/**
	 * 全局异常.
	 *
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(BaseException.class)
	@ResponseStatus(HttpStatus.OK)
	public R baseException(BaseException e) {
		Integer code = e.getCode();

		String message = getMessage(code, e);

		log.error("全局异常信息 ex={}", message, e);
		return R.failed(code, message);
	}

	/**
	 * AccessDeniedException
	 *
	 * @param e the e
	 * @return R
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(AccessDeniedException.class)
	public R handleAccessDeniedException(AccessDeniedException e) {
		log.error("拒绝授权异常信息 ex={}", e.getMessage());
		return R.failed(HttpStatus.FORBIDDEN.value(), "权限不足，不允许访问");
	}

	/**
	 * validation Exception
	 *
	 * @param exception exception
	 * @return R
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({BindException.class})
	public R handleBodyValidException(BindException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		// 插入log 的逻辑
		return R.failed(HttpStatus.BAD_REQUEST.value(), fieldErrors.get(0).getDefaultMessage());
	}

	/**
	 * 避免 404 重定向到 /error 导致NPE ,ignore-url 需要配置对应端点
	 *
	 * @return R
	 */
	@DeleteMapping("/error")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public R noHandlerFoundException() {
		return R.failed(HttpStatus.NOT_FOUND.getReasonPhrase());
	}

	/**
	 * 全局异常.
	 *
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public R handleGlobalException(Exception e) {
		log.error("全局异常信息 ex={}", e.getMessage(), e);
		return R.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getLocalizedMessage());
	}

	/**
	 * 获取消息
	 *
	 * @param code 消息编码
	 * @param e    异常
	 * @return java.lang.String
	 * @since 2022/10/14
	 */
	private String getMessage(Integer code, Exception e) {
		return getMessage(code, e, null);
	}

	/**
	 * 获取消息
	 *
	 * @param code    消息编码
	 * @param e       异常
	 * @param message 默认消息
	 * @return java.lang.String
	 * @since 2022/10/13
	 */
	private String getMessage(Integer code, Exception e, String message) {

		try {
			message = messageSource.getMessage(code + "", null, LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException noSuchMessageException) {

		}

		if (StrUtil.isBlank(message)) {
			message = e.getLocalizedMessage();

		}
		return message;
	}
}
