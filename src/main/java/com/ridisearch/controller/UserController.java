package com.ridisearch.controller;

import com.ridisearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 5/23/13
 * Time: 8:52 AM
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest req, HttpServletResponse res) {
        String val = req.getSession().getAttribute("test") != null ? (String)req.getSession().getAttribute("test") : "DEFAULT";
        System.out.println("val = " + val);
        service.testFunction();
        req.getSession().setAttribute("postVal", "PostVal");
        return "user/index";
    }




}
