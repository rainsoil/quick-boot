package com.su60.example;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户实现
 *
 * @author luyanan
 * @since 2024/04/20
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		String defaultUsername = "admin";
		String defaultPassword = "123456";
		if (!username.equals(defaultUsername)) {
			// 账号不存在
			throw new UsernameNotFoundException(username + " is not found");
		}
		return new LoginUser()
				.setUsername(defaultUsername)
				.setPassword("{bcrypt}" + passwordEncoder.encode(defaultPassword))
				.setUserId(1L);
	}


}
