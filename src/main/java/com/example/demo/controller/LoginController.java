package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(Model model, HttpServletResponse httpServletResponse,
                        @RequestParam String username, @RequestParam String password,
                        @RequestParam(value = "next",required = false)String next){
        Map<String,String> map = new HashMap<>();
        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket"));
        }
    }
}
