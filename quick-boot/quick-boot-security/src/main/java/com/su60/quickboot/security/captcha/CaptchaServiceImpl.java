package com.su60.quickboot.security.captcha;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.util.IdUtil;
import com.su60.quickboot.security.config.SpringSecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * 验证码服务器层
 *
 * @author luyanan
 * @since 2024/07/16
 **/
@Slf4j
@RequiredArgsConstructor
@Service
public class CaptchaServiceImpl implements CaptchaService {

	private final SpringSecurityProperties securityProperties;

	private final CacheManager cacheManager;

	/**
	 * 验证码存储的key
	 *
	 * @since 2024/07/17
	 */

	private static final String KEY = "CAPTCHA:";


	@Override
	public CaptchaVo create() {
		SpringSecurityProperties.CaptchaProperties captchaProperties = securityProperties.getCaptcha();
		if (null == captchaProperties) {
			throw new NullPointerException("验证码配置不能为空");
		}
		CaptchaTypeEnum captchaType = captchaProperties.getType();
		Integer height = captchaProperties.getHeight();
		Integer width = captchaProperties.getWidth();
		Integer codeCount = captchaProperties.getCodeCount();
		Integer lineCount = captchaProperties.getLineCount();
		AbstractCaptcha captcha = null;
		if (CaptchaTypeEnum.CIRCLE == captchaType) {
			captcha = CaptchaUtil.createCircleCaptcha(width, height);
		} else if (CaptchaTypeEnum.GIF == captchaType) {
			captcha = CaptchaUtil.createGifCaptcha(width, height, codeCount);
		} else if (CaptchaTypeEnum.LINE == captchaType) {
			captcha = CaptchaUtil.createLineCaptcha(width, height, codeCount, lineCount);
		} else if (CaptchaTypeEnum.SHEAR == captchaType) {
			captcha = CaptchaUtil.createShearCaptcha(width, height, codeCount, lineCount);
		} else {
			throw new IllegalArgumentException("Invalid captcha type: " + captchaType);
		}
		captcha.setGenerator(new MathGenerator(1));
		captcha.setTextAlpha(1);

		String code = captcha.getCode();
		String imageBase64Data = captcha.getImageBase64Data();
		// 保存验证码

		String key = IdUtil.fastSimpleUUID();
		log.debug("生成验证码,key:{},code:{}", key, code);

		cacheManager.getCache(KEY + "#" + (captchaProperties.getExpireTime() * 60)).put(key, code);
		return new CaptchaVo()
				.setUuid(key)
				.setCaptchaEnabled(true)
				.setImg(imageBase64Data);
	}


	@Override
	public boolean check(String key, String code) {
		CodeGenerator codeGenerator = new MathGenerator();
		SpringSecurityProperties.CaptchaProperties captchaProperties = securityProperties.getCaptcha();
		if (null == captchaProperties) {
			throw new NullPointerException("验证码配置不能为空");
		}
		Cache cache = cacheManager.getCache(KEY + "#" + (captchaProperties.getExpireTime() * 60));
		Cache.ValueWrapper valueWrapper = cache.get(key);
		if (null == valueWrapper || null == valueWrapper.get()) {
			return false;
		}
		boolean verify = codeGenerator.verify(valueWrapper.get().toString(), code);
		cache.evict(key);
		return verify;
	}
}
