package com.example.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.filter.MyOAuth2AuthorizationRequestRedirectFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private ClientRegistrationRepository clientRegistrationRepository;

    @Value("${front.index.path}")
    private String frontIndexPath;

    private String authorizationBaseUrl = "/oauth2/authorization";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * http .authorizeRequests(authorizeRequests ->
         * authorizeRequests.anyRequest().authenticated() ) .oauth2Login(oauth2Login ->
         * oauth2Login.loginPage("/oauth2/authorization/messaging-client-oidc"))
         * .oauth2Client(withDefaults()); return http.build();
         */
        http.authorizeRequests((authorizeRequests) -> authorizeRequests.anyRequest().authenticated());
        http.oauth2Client(withDefaults());
        // oauth2退出配置
        http.logout(logout -> logout.clearAuthentication(true).logoutSuccessHandler(oidcLogoutSuccessHandler()));
        // oauth2登录配置
        http.oauth2Login(
                oauth2 -> oauth2.authorizationEndpoint(authorization -> authorization.baseUri(authorizationBaseUrl)));
        // 认证错误处理，返回403状态
        http.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
        /*
         * OAuth2LoginConfigurer<HttpSecurity> auth2LoginConfigurer =
         * http.getConfigurer(OAuth2LoginConfigurer.class);
         * auth2LoginConfigurer.successHandler(new
         * MySavedRequestAwareAuthenticationSuccessHandler());
         */

        // 登录重定向地址缓存到session
        http.addFilterBefore(new MyOAuth2AuthorizationRequestRedirectFilter(authorizationBaseUrl, frontIndexPath),
                OAuth2AuthorizationRequestRedirectFilter.class);

        // 跨域请求，如果spring mvc配置了cors自动获取，否则获取CorsConfigurationSource
        http.cors(withDefaults());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(frontIndexPath));
        // 如果同时开启csrf和cors，需要设置可读取的hedaers['x-csrf-token']
        // ajax请求通过设置setRequestHeader传递CSRF令牌
        configuration.setAllowedHeaders(Arrays.asList("x-csrf-token"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(
                this.clientRegistrationRepository);
        return oidcLogoutSuccessHandler;
    }
}
