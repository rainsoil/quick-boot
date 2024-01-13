package io.github.rainsoil.fastboot.common.security;


import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.CipherMode;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SmPasswordEncoderTest {

	@Test
	public void encode() {

		String str = "fastBoot123#";
		PasswordEncoder passwordEncoder = new SmPasswordEncoder();
		String encode = passwordEncoder.encode(str);
		System.out.println(encode);
		System.out.println(passwordEncoder.matches(str, encode));
	}

	@Test
	public void sm4() {

		String str = "0123456789abcdeffedcba9876543210";

		SM4 sm4 = new SM4(HexUtil.decodeHex(str));
		String s2 = sm4.encryptBase64("123456".getBytes(StandardCharsets.UTF_8));
		System.out.println(s2);
		String pd = "t1F4JaIV9ecW3UfkRanSew%3D%3D";
		String s = sm4.decryptStr(URLUtil.decode(pd));
		System.out.println(s);
//		"13c71106a5aad6efddd2476f44565f37"
	}
}