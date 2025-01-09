package com.su60.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 缓存service
 *
 * @author luyanan
 * @since 2024/04/24
 **/
@Slf4j
@Service
public class CacheService {


	@Cacheable("cache#30")
	public String getFromDb() {
		log.info("从数据库中查询");
		return "test";
	}

}
