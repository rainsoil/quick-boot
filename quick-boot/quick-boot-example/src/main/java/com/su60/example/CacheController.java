package com.su60.example;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存测试类
 *
 * @author luyanan
 * @since 2024/04/24
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("cache")
public class CacheController {

	private final CacheService cacheService;


	@PermitAll
	@GetMapping("cache")
	public String getCache() {
		return cacheService.getFromDb();
	}
}
