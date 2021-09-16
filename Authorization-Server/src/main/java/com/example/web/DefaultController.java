package com.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {

    /**
     * ��¼ҳ��
     * 
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * ��ҳ
     * 
     * @return
     */
    @GetMapping({ "/", "/index" })
    public String index() {
        return "index";
    }

    /**
     * �˳�ȷ��ҳ��
     * 
     * @return
     */
    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @ResponseBody
    @PostMapping("authenticated")
    public boolean authenticated() {
        return true;
    }
}
