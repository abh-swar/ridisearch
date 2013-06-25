package com.ridisearch.controller;

import com.ridisearch.utils.Constants;
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
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest req, HttpServletResponse res, RedirectAttributes attributes) {
        HttpSession session = req.getSession();

        session.setAttribute(Constants.IS_ADMIN,null);
        session.setAttribute(Constants.IS_LOGGED_IN,null);
        session.setAttribute(Constants.USER_ID,null);

        session.invalidate();
        String message = "You have successfully been logged out.";
        attributes.addFlashAttribute("message",message);
        System.out.println(" Redirecting to /ridisearch/index "+ message );
        return "redirect:/ridisearch/index";
    }
}
