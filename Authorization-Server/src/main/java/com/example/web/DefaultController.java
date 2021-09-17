package com.example.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {

    /**
     * 登录页面
     * 
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 首页
     * 
     * @return
     */
    @GetMapping({ "/", "/index" })
    public String index() {
        return "index";
    }

    /**
     * 退出确认页面
     * 
     * @return
     */
    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @ResponseBody
    @GetMapping("authenticated")
    public Map<String, Object> authenticated(CsrfToken token) {
        Map<String, Object> map = new HashMap<>();
        map.put("csrf", token);
        return map;
    }
}
