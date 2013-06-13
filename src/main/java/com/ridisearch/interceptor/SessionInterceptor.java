package com.ridisearch.interceptor;

import com.ridisearch.utils.Variables;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 5/23/13
 * Time: 9:56 AM
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute(Variables.IS_LOGGED_IN) == null) {
            session.setAttribute(Variables.IS_LOGGED_IN,false);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        HttpSession session = httpServletRequest.getSession();
//        boolean isAdmin = session.getAttribute("isAdmin") != null ? false : true;
        String val = session.getAttribute("postVal") != null ? (String)session.getAttribute("postVal"): "DEFAULT";
        System.out.println("val = " + val);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
