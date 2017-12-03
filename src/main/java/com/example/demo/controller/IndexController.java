package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(Model model, HttpServletResponse httpServletResponse,
                        @RequestParam String username, @RequestParam String password,
                        @RequestParam(value = "next",required = false)String next){
        Map<String,String> map = userService.login(username,password);
        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket"));
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);

            if(!StringUtils.isEmpty(next)){
                return "redirect:" + next;
            }

            return "redirect:/";
        }else {
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }
    }

    @RequestMapping("/register")
    public String register(Model model,HttpServletResponse httpServletResponse,
                           @RequestParam String username,@RequestParam String password,
                           @RequestParam(value = "next", required = false)String next){
        Map<String,String> map = userService.register(username,password);
        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket"));
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
            if(!StringUtils.isEmpty(next)){
                return "redirect:"+next;
            }else {
                return "redirect:/";
            }
        }else {
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }
    }
}
