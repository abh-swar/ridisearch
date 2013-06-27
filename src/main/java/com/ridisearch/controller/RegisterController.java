package com.ridisearch.controller;

import com.ridisearch.domain.User;
import com.ridisearch.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/20/13
 * Time: 1:02 AM
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
    public String index() {
        return  "register/index";
    }

    @RequestMapping(value = "/saveUser", method = { RequestMethod.GET, RequestMethod.POST })
    public String saveUser(HttpServletRequest req, HttpServletResponse res, RedirectAttributes attributes) {
        String userName = req.getParameter("userName")!= null ? req.getParameter("userName") : "";
        String password1 = req.getParameter("password1")!= null ? req.getParameter("password1") : "";
        String password2 = req.getParameter("password2")!= null ? req.getParameter("password2") : "";
        String name = req.getParameter("name")!= null ? req.getParameter("name") : "";

        String message = "";
        String action = "";


        /**
         * 1. Check if the passwords match
         * 2. Check if a user by this userName exits first
         * 3. Then try to save the user
         * 4. If any error thrown show the appropriate message
         */
        if (!password1.equals(password2)) {
            message = "The two passwords do not match. Please try again.";
            action = "/register/index";
        } else if (registerService.userAlreadyExists(userName)) {
            message = "A user by this user name already exists. Please try another user name.";
            action = "/register/index";
        } else if (registerService.registerUser(userName,password1, name)
                && registerService.provideUserRole(userName)) {
            message = "Congratulations, " + name + " you have successfully been registered. You can now log in.";
            action = "/login/form";
        } else {
            message = "Something went wrong while saving the user. Try again or please contact your administrator.";
            action  = "/index";
        }
        attributes.addFlashAttribute("message",message);
        System.out.println("Redirecting to action = " + action);
        return  "redirect:/ridisearch"+action;
    }
}
