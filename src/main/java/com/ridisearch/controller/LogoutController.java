package com.ridisearch.controller;

import com.ridisearch.domain.User;
import com.ridisearch.service.LoginService;
import com.ridisearch.service.UserService;
import com.ridisearch.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/19/13
 * Time: 8:16 PM
 */
@Controller
@RequestMapping("/logout")
public class LogoutController {
    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest req, RedirectAttributes attributes) {

        HttpSession session = req.getSession();
        long userId         = session.getAttribute(Constants.USER_ID) != null ? (Long) session.getAttribute(Constants.USER_ID) : 0;

        if (userId > 0) {
            User user = userService.getUser(userId);

            session.setAttribute(Constants.IS_ADMIN,null);
            session.setAttribute(Constants.IS_LOGGED_IN,null);
            session.setAttribute(Constants.USER_ID,null);

            session.invalidate();

            //log the event
            loginService.setLog(user, "LOGGED_OUT");

            String message = "You have successfully been logged out.";
            attributes.addFlashAttribute("message", message);
        }
        return "redirect:/ridisearch/index";
    }
}
