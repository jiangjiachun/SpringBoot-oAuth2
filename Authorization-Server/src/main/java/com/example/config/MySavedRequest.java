package com.example.config;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.security.web.savedrequest.SavedRequest;

public class MySavedRequest implements SavedRequest {

    /**
     * 
     */
    private static final long serialVersionUID = 1949200086455548595L;
    
    private String redirectUrl;
    

    public MySavedRequest(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public List<Cookie> getCookies() {
        return null;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public List<String> getHeaderValues(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaderNames() {
        return null;
    }

    @Override
    public List<Locale> getLocales() {
        return null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

}
