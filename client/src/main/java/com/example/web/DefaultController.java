package com.example.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {

    @Value("${front.index.path}")
    private String frontIndexPath;

    @GetMapping("/")
    public String root() {
//        return "index";
        return "redirect:" + frontIndexPath;
    }

    @GetMapping("/index")
    public String index(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OAuth2User oauth2User) {
        return "index";
    }

    @ResponseBody
    @GetMapping("authorized/user")
    public Map<String, Object> authorized(@AuthenticationPrincipal OAuth2User oauth2User, CsrfToken csrf) {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("user", oauth2User);
        info.put("csrf", csrf);
        return info;
    }
}