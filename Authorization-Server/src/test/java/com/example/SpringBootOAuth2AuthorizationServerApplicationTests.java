package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

//@SpringBootTest
class SpringBootOAuth2AuthorizationServerApplicationTests {
    
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    
//	@Test
	void contextLoads() {
	    System.out.println(passwordEncoder.encode("secret"));
	    System.out.println(passwordEncoder.matches("secret", "{bcrypt}$2a$10$Jiu1H4mv1lY1lC2AIFwdd.QHfaAVJfE0nZa7lMX3zbggmhd0Mqarq"));
	}

	@Test
	void passwordEncoder() {
	    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	    System.out.println(encoder.encode("123456"));
	}
}
