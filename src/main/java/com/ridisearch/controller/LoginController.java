package com.ridisearch.controller;

import com.ridisearch.domain.User;
import com.ridisearch.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/3/13
 * Time: 9:18 AM
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest req, HttpServletResponse res) {
        //todo check if valid credentials, if true goto dashboard and set in session loggedin is true
        //todo else go back to the non logged-in dashboard
        User user = loginService.getUser("aswar@deerwalk.com","P@ssw0rd");
        loginService.getRoles(user.getId());

        System.out.println("user = " + user.getId() + " " + user.getName());
        return "";
    }
}
