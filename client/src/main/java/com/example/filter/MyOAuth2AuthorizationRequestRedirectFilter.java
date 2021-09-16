package com.example.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class MyOAuth2AuthorizationRequestRedirectFilter extends OncePerRequestFilter {

    public final String REDIRECT = "redirect";

    private String frontIndexPath;

    private String baseURI;

    public MyOAuth2AuthorizationRequestRedirectFilter(String baseURI, String frontIndexPath) {
        this.baseURI = baseURI;
        this.frontIndexPath = frontIndexPath;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().startsWith(baseURI)) {
            String redirect = request.getParameter("redirect");
            if (StringUtils.hasText(redirect) && redirect.startsWith(frontIndexPath)) {
                MySavedRequest defaultSavedRequest = new MySavedRequest(redirect);
                request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", defaultSavedRequest);
            }
        }
        filterChain.doFilter(request, response);
    }

}
