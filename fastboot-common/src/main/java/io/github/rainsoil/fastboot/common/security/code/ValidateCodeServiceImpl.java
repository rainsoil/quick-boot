package io.github.rainsoil.fastboot.common.security.code;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import io.github.rainsoil.fastboot.common.security.config.SpringSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 验证码
 *
 * @author luyanan
 * @since 2024/01/13
 **/
@RequiredArgsConstructor
public class ValidateCodeServiceImpl implements ValidateCodeService {

	private final CacheManager cacheManager;

	private final SpringSecurityProperties securityProperties;

	/**
	 * 存储验证码生成时间的key
	 *
	 * @since 2024/01/13
	 */

	private static final String VALIDATE_CODE_CACHE_TIME_KEY = "validate:code:cache_time";

	/**
	 * 存储验证码code的key
	 *
	 * @since 2024/01/13
	 */

	private static final String VALIDATE_CODE_CACHE_CODE_KEY = "validate:code:cache_code";

	@Override
	public String createCode(String uuid, HttpServletResponse response) {


		//定义图形验证码的长、宽、验证码字符数、干扰元素个数
		CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
//CircleCaptcha captcha = new CircleCaptcha(200, 100, 4, 20);
// 重新生成code
		captcha.createCode();
		String code = captcha.getCode();


		String imageBase64 = captcha.getImageBase64Data();

		System.out.println(code);

		// 存储生成时间

		cacheManager.getCache(VALIDATE_CODE_CACHE_TIME_KEY).put(uuid, new Date());
		// 存储code

		cacheManager.getCache(VALIDATE_CODE_CACHE_CODE_KEY).put(uuid, code);

		try {
//			ImageIO.write(captcha.getImage(), "JPEG", response.getOutputStream());
			byte[] imageBytes = captcha.getImageBytes();
			FileUtil.writeBytes(imageBytes, "E:/aaa.jpg");
			IoUtil.write(response.getOutputStream(), false, imageBytes);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return imageBase64;
	}

	@Override
	public void match(String code, String uuid) {
		if (StrUtil.isBlank(code) || StrUtil.isBlank(uuid)) {
			throw new ValidateCodeException("验证码不能为空");
		}
		SpringSecurityProperties.ValidateCode validateCode = securityProperties.getValidatecode();

		//  有效时间
		Integer effectiveTime = validateCode.getEffectiveTime();
		Date createTime = cacheManager.getCache(VALIDATE_CODE_CACHE_TIME_KEY).get(uuid, Date.class);
		String cacheCode = cacheManager.getCache(VALIDATE_CODE_CACHE_CODE_KEY).get(uuid, String.class);
		if (null == createTime || StrUtil.isBlank(cacheCode)) {
			throw new ValidateCodeException("验证码已经过期");
		}
		if (DateUtil.offsetMinute(createTime, effectiveTime).getTime() < System.currentTimeMillis()) {
			throw new ValidateCodeException("验证码已经过期");
		}
		if (!code.equals(cacheCode)) {
			throw new ValidateCodeException("验证码不正确");
		}

//		cacheManager.getCache(VALIDATE_CODE_CACHE_TIME_KEY).evict(uuid);
//		cacheManager.getCache(VALIDATE_CODE_CACHE_CODE_KEY).evict(uuid);
	}
}
