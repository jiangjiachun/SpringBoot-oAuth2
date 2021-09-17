package com.example.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class MyOAuth2RedirectUrlCache extends OncePerRequestFilter {

    static final String REDIRECT = "redirect";

    static final String SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";

    private String frontBaseUrl;

    private String baseURI;

    public MyOAuth2RedirectUrlCache(String baseURI, String frontBaseUrl) {
        this.baseURI = baseURI;
        this.frontBaseUrl = frontBaseUrl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().startsWith(baseURI)) {
            String redirect = request.getParameter("redirect");
            if (StringUtils.hasText(redirect) && redirect.startsWith(frontBaseUrl)) {
                MySavedRequest defaultSavedRequest = new MySavedRequest(redirect);
                request.getSession().setAttribute(SAVED_REQUEST, defaultSavedRequest);
            }
        }
        filterChain.doFilter(request, response);
    }

}
