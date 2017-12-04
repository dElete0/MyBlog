package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @RequestMapping(path = {"","/index"})
    public String index(Model model){
        return "/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

    @RequestMapping("/register")
    public String register(){
        return "/register";
    }

    @RequestMapping(value = "/loginPost")
    public String login(Model model, HttpServletResponse httpResponse,
                        @RequestParam String username,@RequestParam String password,@RequestParam(value = "next",required = false)String next){
        Map<String,String> map = userService.login(username,password);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket",map.get("ticket"));
            cookie.setPath("/");
            httpResponse.addCookie(cookie);
            return "/index";
        }else {
            model.addAttribute("msg", map.get("msg"));
            return "/login";
        }
    }

    @RequestMapping("/registerPost")
    public String registerPost(Model model,HttpServletResponse httpServletResponse,
                           @RequestParam String username,@RequestParam String password1,
                           @RequestParam String password2,
                           @RequestParam(value = "next", required = false)String next){
        if(!password1.equals(password2)){
            model.addAttribute("msg","密码不一致");
        }
        Map<String,String> map = userService.register(username,password1);
        if(map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket",map.get("ticket"));
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
            return "/index";
        }else {
            model.addAttribute("msg",map.get("msg"));
            return "/register";
        }
    }
}
