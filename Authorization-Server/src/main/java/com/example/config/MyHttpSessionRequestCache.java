package com.example.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

public class MyHttpSessionRequestCache extends HttpSessionRequestCache {

    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if (Arrays.asList("/logout", "/error", "/login").contains(request.getRequestURI())) {
            return;
        }
        super.saveRequest(request, response);
    }

}
