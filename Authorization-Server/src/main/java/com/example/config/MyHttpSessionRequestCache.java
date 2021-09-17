package com.example.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.util.StringUtils;

public class MyHttpSessionRequestCache extends HttpSessionRequestCache {

    static final String SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";

    static final String REDIRECT = "redirect";

    private String frontBaseUrl;

    public MyHttpSessionRequestCache(String frontBaseUrl) {
        this.frontBaseUrl = frontBaseUrl;
    }

    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getRequestURI());
        if (Arrays.asList("/logout", "/error").contains(request.getRequestURI())) {
            return;
        } else if (request.getRequestURI().equals("/login")) {
            String redirect = request.getParameter(REDIRECT);
            if (StringUtils.hasText(redirect) && redirect.startsWith(frontBaseUrl)) {
                MySavedRequest defaultSavedRequest = new MySavedRequest(redirect);
                request.getSession().setAttribute(SAVED_REQUEST, defaultSavedRequest);
            }
            return;
        }
        super.saveRequest(request, response);
    }

}
