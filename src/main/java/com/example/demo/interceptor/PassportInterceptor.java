package com.example.demo.interceptor;

import com.example.demo.dao.LoginTicketDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.HostHolder;
import com.example.demo.model.LoginTicket;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor{
    @Autowired
    private LoginTicketDao loginTicketDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,Object o)
        throws Exception{
        String ticket = null;
        if(httpServletRequest.getCookies()!=null){
            for(Cookie cookie : httpServletRequest.getCookies()){
                if("ticket".equals(cookie.getName())){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if(ticket != null){
            LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
            if(loginTicket==null || loginTicket.getExpired().before(new Date())){
                return true;
            }
            User user = userDao.selectById(loginTicket.getUserId());
            hostHolder.setUsers(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse
                           httpServletResponse, Object o, ModelAndView modelAndView)
        throws Exception{
        if(modelAndView!=null){
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e)throws Exception{
        hostHolder.clear();
    }
}
