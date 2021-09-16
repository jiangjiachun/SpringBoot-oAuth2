package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootOAuth2AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootOAuth2AuthorizationServerApplication.class, args);
    }

    /**
     * freemarker自动引入模板
     * 
     * @param freeMarkerConfigurer
     * @return
     */
    /*@Bean
    public CommandLineRunner init(FreeMarkerConfigurer freeMarkerConfigurer) {
        return (args) -> {
            Map<String, String> map = new HashMap<>();
            map.put("csrf", "/lib/csrf.ftl");
            freeMarkerConfigurer.getConfiguration().setAutoImports(map);
            freeMarkerConfigurer.getConfiguration().setLazyAutoImports(true);
        };
    }*/
}
