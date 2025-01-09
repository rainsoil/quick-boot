package com.su60.quickboot.security.captcha;

import com.su60.quickboot.common.core.R;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码控制层
 *
 * @author luyanan
 * @since 2024/07/16
 **/
@Slf4j
@RequiredArgsConstructor
@RestController
public class CaptchaController {

	private final CaptchaService captchaService;

	/**
	 * 创建验证码
	 *
	 * @return 验证码结果
	 * @since 2024/07/17
	 */

	@PermitAll
	@GetMapping("captchaImage")
	public R<CaptchaVo> create() {
		CaptchaVo captchaVo = captchaService.create();
		return R.ok(captchaVo);
	}


}
