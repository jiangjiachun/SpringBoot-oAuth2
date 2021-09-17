package com.example;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@SpringBootApplication
public class SpringBootOAuth2AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootOAuth2AuthorizationServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(JdbcUserDetailsManager jdbcUserDetailsManager) {
        return (args) -> {
            UserDetails userDetails = null;
            try {
                userDetails = jdbcUserDetailsManager.loadUserByUsername("admin");
            } catch (Exception e) {
            }
            if (userDetails == null) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("user");

                PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

                userDetails = new User("admin", encoder.encode("123456"), Arrays.asList(grantedAuthority));
                jdbcUserDetailsManager.createUser(userDetails);
            }
        };
    }
}
