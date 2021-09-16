package com.example.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class FreeMarkerConfig {

    @Resource
    public FreeMarkerConfigurer freeMarkerConfigurer;

    @Bean
    public void set() {
        Map<String, String> map = new HashMap<>();
        map.put("csrf", "/lib/csrf.ftl");
        freeMarkerConfigurer.getConfiguration().setAutoImports(map);
        freeMarkerConfigurer.getConfiguration().setLazyAutoImports(true);
    }
}
