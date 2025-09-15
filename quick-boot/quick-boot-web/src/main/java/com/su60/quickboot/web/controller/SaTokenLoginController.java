package com.su60.quickboot.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.su60.quickboot.common.core.R;
import com.su60.quickboot.core.security.LoginUserUtils;
import com.su60.quickboot.web.service.SaTokenLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Sa-Token 登录控制器
 *
 * @author luyanan
 * @since 2024/12/19
 */
@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class SaTokenLoginController {

	private final SaTokenLoginService saTokenLoginService;

	/**
	 * 用户登录
	 *
	 * @param username 用户名
	 * @param password 密码
	 * @return 登录结果
	 */
	@PostMapping("/login")
	public R<Map<String, Object>> login(@RequestParam String username,
										@RequestParam String password) {
		try {
			// 执行登录
			String token = saTokenLoginService.login(username, password);

			// 构建返回结果
			Map<String, Object> result = new HashMap<>();
			result.put("access_token", token);
			result.put("tokenTimeout", StpUtil.getTokenTimeout());
			return R.ok(result);
		} catch (Exception e) {
			log.error("登录失败: {}", e.getMessage(), e);
			return R.failed(e.getMessage());
		}
	}

	/**
	 * 用户登录（JSON格式）
	 *
	 * @param loginRequest 登录请求
	 * @return 登录结果
	 */
	@PostMapping("/json")
	public R<Map<String, Object>> loginJson(@RequestBody Map<String, String> loginRequest) {
		String username = loginRequest.get("username");
		String password = loginRequest.get("password");
		return login(username, password);
	}

	/**
	 * 用户登录（兼容原有接口）
	 *
	 * @param loginRequest 登录请求
	 * @return 登录结果
	 */
	@PostMapping("/auth")
	public R<Map<String, Object>> auth(@RequestBody Map<String, String> loginRequest) {
		String username = loginRequest.get("username");
		String password = loginRequest.get("password");
		return login(username, password);
	}

	/**
	 * 用户登出
	 *
	 * @return 登出结果
	 */
	@PostMapping("/logout")
	public R<Void> logout() {
		try {
			saTokenLoginService.logout();
			return R.ok();
		} catch (Exception e) {
			log.error("登出失败: {}", e.getMessage(), e);
			return R.failed("登出失败");
		}
	}

	/**
	 * 检查登录状态
	 *
	 * @return 登录状态
	 */
	@GetMapping("/check")
	public R<Map<String, Object>> checkLogin() {
		try {
			Map<String, Object> result = new HashMap<>();
			result.put("isLogin", StpUtil.isLogin());
			if (StpUtil.isLogin()) {
				result.put("loginId", StpUtil.getLoginId());
				result.put("loginDevice", StpUtil.getLoginDevice());
				result.put("tokenTimeout", StpUtil.getTokenTimeout());
				result.put("sessionTimeout", StpUtil.getSessionTimeout());
				// Sa-Token 1.37.0 中这些方法可能不存在，暂时注释
				// result.put("loginTime", StpUtil.getLoginTime());
				// result.put("lastActivityTime", StpUtil.getLastActivityTime());
			}
			return R.ok(result);
		} catch (Exception e) {
			log.error("检查登录状态失败: {}", e.getMessage(), e);
			return R.failed("检查登录状态失败");
		}
	}

	/**
	 * 获取当前登录用户信息
	 *
	 * @return 用户信息
	 */
	@GetMapping("/user")
	public R<Object> getCurrentUser() {
		try {
			if (!StpUtil.isLogin()) {
				return R.failed("用户未登录");
			}

			Object user = LoginUserUtils.getUser();
			return R.ok(user);
		} catch (Exception e) {
			log.error("获取当前用户信息失败: {}", e.getMessage(), e);
			return R.failed("获取用户信息失败");
		}
	}

	/**
	 * 踢人下线
	 *
	 * @param loginId 用户ID
	 * @return 操作结果
	 */
	@PostMapping("/kickout/{loginId}")
	public R<Void> kickout(@PathVariable Long loginId) {
		try {
			StpUtil.kickout(loginId);
			return R.ok();
		} catch (Exception e) {
			log.error("踢人下线失败: {}", e.getMessage(), e);
			return R.failed("踢人下线失败");
		}
	}

	/**
	 * 踢人下线（指定设备）
	 *
	 * @param loginId 用户ID
	 * @param device  设备标识
	 * @return 操作结果
	 */
	@PostMapping("/kickout/{loginId}/{device}")
	public R<Void> kickout(@PathVariable Long loginId, @PathVariable String device) {
		try {
			StpUtil.kickout(loginId, device);
			return R.ok();
		} catch (Exception e) {
			log.error("踢人下线失败: {}", e.getMessage(), e);
			return R.failed("踢人下线失败");
		}
	}
}
