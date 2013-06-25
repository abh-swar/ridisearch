package com.ridisearch.controller;

import com.ridisearch.domain.User;
import com.ridisearch.service.UserService;
import com.ridisearch.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
        req.getSession().setAttribute("postVal", "PostVal");
        return "user/index";
    }


    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(ModelMap model) {
        return "user/about";
    }

    @RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
    public String search(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
//        String message = "Successfully logged in. Welcome admin.";
//        modelMap.addAttribute("message",message);
        return  "user/searchResult";
    }

    @RequestMapping(value = "/profile", method = { RequestMethod.GET, RequestMethod.POST })
    public String profile(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
        User user = service.getUser(Long.parseLong(req.getSession().getAttribute(Constants.USER_ID).toString()));
        modelMap.addAttribute("user",user);
        return  "user/profile";
    }

    @RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
    public String edit(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
        long userId     = Long.parseLong(req.getParameter("id"));
        User user       = service.getUser(userId);
        String message;

        if (user == null) {
            message = "No user found. Please try again!";
            req.getSession().setAttribute("message",message);
        } else {
            modelMap.addAttribute("user",user);
        }
        return  "user/editProfile";
    }

    @RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
    public String save(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
        String userName     = req.getParameter("userName")!= null ? req.getParameter("userName") : "";
        String fullName     = req.getParameter("name")!= null ? req.getParameter("name") : "";
        String address      = req.getParameter("address")!= null ? req.getParameter("address") : "";
        String phoneNumber  = req.getParameter("phoneNumber")!= null ? req.getParameter("phoneNumber") : "";
        long userId         = req.getParameter("id")!= null ? Long.parseLong(req.getParameter("id")) : 0;

        String message  = "";
        if (service.saveEditedUser(userName,fullName,userId,address,phoneNumber)) {
            message = "User information was successfully edited";
        }  else {
            message = "Could not edit user information. Please contact your administrator.";
        }
        modelMap.addAttribute("message",message);
        return  "admin/index";
    }


}
