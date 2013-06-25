package com.ridisearch.interceptor;

import com.ridisearch.utils.Constants;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/19/13
 * Time: 11:16 PM
 */
public class UserInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        boolean isLoggedId;
        isLoggedId = session.getAttribute(Constants.IS_LOGGED_IN) != null ?(Boolean)session.getAttribute(Constants.IS_LOGGED_IN):false;
        System.out.println("isLoggedIn = " + isLoggedId);

        if (!isLoggedId) {
            httpServletResponse.sendRedirect("/ridisearch/error/index");
            return false;
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
