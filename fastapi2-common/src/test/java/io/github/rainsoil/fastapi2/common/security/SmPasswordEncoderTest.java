package io.github.rainsoil.fastapi2.common.security;


import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SmPasswordEncoderTest {

    @Test
    public void encode() {

        String str = "123456";
        PasswordEncoder passwordEncoder = new SmPasswordEncoder();
        String encode = passwordEncoder.encode(str);
        System.out.println(encode);
        System.out.println(passwordEncoder.matches(str, encode));
    }
}