package com.example.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.filter.MyOidcProviderConfigurationEndpointFilter;

@EnableWebSecurity
public class WebSecurityConfig {

    @Resource
    private DataSource dataSource;

    private static String ISSUER_URL;

    private static String FRONT_BASE_URL = "http://127.0.0.1:8888";

    @Value("${issuer-url}")
    public void setIssuerUrl(String url) {
        ISSUER_URL = url;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new JdbcUserDetailsManagerConfigurer<>().dataSource(dataSource).passwordEncoder(encoder)
                .getUserDetailsService();
    }

    @Configuration
    @Order(1)
    public static class AuthorizationServerConfigurationAdapter extends WebSecurityConfigurerAdapter {

        protected void configure(HttpSecurity http) throws Exception {
            OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
            http.formLogin(withDefaults());
            if (providerSettings().getIssuer() != null) {
                http.addFilterBefore(new MyOidcProviderConfigurationEndpointFilter(providerSettings()),
                        AbstractPreAuthenticatedProcessingFilter.class);
            }
        }
    }

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/resources/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated());
            http.formLogin(form -> form.loginPage("/login").permitAll());
            http.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll());
            http.requestCache(cache -> cache.requestCache(new MyHttpSessionRequestCache(FRONT_BASE_URL)));
            http.cors(withDefaults());
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(FRONT_BASE_URL));
        // ??????????????????csrf???cors???????????????????????????hedaers['x-csrf-token']
        // ajax??????????????????setRequestHeader??????CSRF??????
        configuration.setAllowedHeaders(Arrays.asList("x-csrf-token"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public static ProviderSettings providerSettings() {
        return ProviderSettings.builder().issuer(ISSUER_URL).build();
    }

}
