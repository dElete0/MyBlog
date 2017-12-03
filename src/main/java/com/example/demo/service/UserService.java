package com.example.demo.service;

import com.example.demo.dao.LoginTicketDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.LoginTicket;
import com.example.demo.model.User;
import com.example.demo.util.MyBlogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LoginTicketDao loginTicketDao;

    public User getUserById(int userId){
        return userDao.selectById(userId);
    }
    public User getUserByName(String name){
        return userDao.selectByName(name);
    }
    public void addUser(User user){
        userDao.insertUser(user);
    }

    public Map<String,String> register(String username,String password){
        Map<String,String> map = new HashMap<>();
        Random random = new Random();
        if (StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        User u = userDao.selectByName(username);
        if(u!=null){
            map.put("msg","用户名已被占用");
            return map;
        }
        User user = new User(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dm.png"
                ,random.nextInt(1000)));
        user.setPassword(MyBlogUtil.MD5(password+user.getSalt()));
        user.setRole("user");
        userDao.insertUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public Map<String,String> login(String username,String password){
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        User u = userDao.selectByName(username);
        if(u==null){
            map.put("msg","用户名不存在");
            return map;
        }
        if(!MyBlogUtil.MD5(password+u.getSalt()).equals(u.getPassword())){
            map.put("msg","密码错误");
            return map;
        }
        String ticket = addLoginTicket(u.getId());
        map.put("ticket",ticket);
        return map;
    }

    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        Date date = new Date();
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString()
                .replaceAll("-",""));
        loginTicketDao.insertLoginTicket(loginTicket);
        return loginTicket.getTicket();
    }
}
