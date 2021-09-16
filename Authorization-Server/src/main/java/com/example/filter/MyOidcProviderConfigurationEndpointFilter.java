package com.example.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType;
import org.springframework.security.oauth2.core.oidc.OidcProviderConfiguration;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.http.converter.OidcProviderConfigurationHttpMessageConverter;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

public class MyOidcProviderConfigurationEndpointFilter extends OncePerRequestFilter {
    /**
     * The default endpoint {@code URI} for OpenID Provider Configuration requests.
     */
    private static final String DEFAULT_OIDC_PROVIDER_CONFIGURATION_ENDPOINT_URI = "/.well-known/openid-configuration";

    private final ProviderSettings providerSettings;
    private final RequestMatcher requestMatcher;
    private final OidcProviderConfigurationHttpMessageConverter providerConfigurationHttpMessageConverter = new OidcProviderConfigurationHttpMessageConverter();

    public MyOidcProviderConfigurationEndpointFilter(ProviderSettings providerSettings) {
        Assert.notNull(providerSettings, "providerSettings cannot be null");
        this.providerSettings = providerSettings;
        this.requestMatcher = new AntPathRequestMatcher(DEFAULT_OIDC_PROVIDER_CONFIGURATION_ENDPOINT_URI,
                HttpMethod.GET.name());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!this.requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // ÍË³öµØÖ·
        String logout = UrlUtils.buildFullRequestUrl(request.getScheme(), request.getServerName(),
                request.getServerPort(), "/logout", null);

        OidcProviderConfiguration providerConfiguration = OidcProviderConfiguration.builder()
                .issuer(this.providerSettings.getIssuer())
                .authorizationEndpoint(
                        asUrl(this.providerSettings.getIssuer(), this.providerSettings.getAuthorizationEndpoint()))
                .tokenEndpoint(asUrl(this.providerSettings.getIssuer(), this.providerSettings.getTokenEndpoint()))
                .tokenEndpointAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue())
                .tokenEndpointAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue())
                .jwkSetUrl(asUrl(this.providerSettings.getIssuer(), this.providerSettings.getJwkSetEndpoint()))
                .responseType(OAuth2AuthorizationResponseType.CODE.getValue())
                .grantType(AuthorizationGrantType.AUTHORIZATION_CODE.getValue())
                .grantType(AuthorizationGrantType.CLIENT_CREDENTIALS.getValue())
                .grantType(AuthorizationGrantType.REFRESH_TOKEN.getValue()).subjectType("public")
                .idTokenSigningAlgorithm(SignatureAlgorithm.RS256.getName()).scope(OidcScopes.OPENID)
                .claim("end_session_endpoint", logout).build();

        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        this.providerConfigurationHttpMessageConverter.write(providerConfiguration, MediaType.APPLICATION_JSON,
                httpResponse);
    }

    private static String asUrl(String issuer, String endpoint) {
        return UriComponentsBuilder.fromUriString(issuer).path(endpoint).build().toUriString();
    }
}
