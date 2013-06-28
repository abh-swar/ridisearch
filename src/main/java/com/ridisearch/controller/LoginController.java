package com.ridisearch.controller;

import com.ridisearch.domain.Role;
import com.ridisearch.domain.User;
import com.ridisearch.service.LoginService;
import com.ridisearch.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String showForm() {
        return "login/form";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String index(HttpServletRequest req, HttpServletResponse res, RedirectAttributes attributes) {
        String userName = req.getParameter("userName")!= null ? req.getParameter("userName") : "";
        String password = req.getParameter("password")!= null ? req.getParameter("password") : "";

        String message = "";
        String action = "";

        boolean isAdmin = false;

        User user = loginService.getUser(userName,password);
        if (user != null) {
            System.out.println("user.getName() = " + user.getName() + "userID " + user.getId());
            List<Role> roleList = loginService.getRoles(user.getId());
            HttpSession session = req.getSession();
            session.setAttribute(Constants.IS_LOGGED_IN, true);
            session.setAttribute(Constants.USER_ID,user.getId());
            //check in each role if the user is an admin user
            for (Role role : roleList) {
                System.out.println(role.getRoleName());
                if (role.getRoleName().equals(Constants.ROLE_ADMIN)) {
                    isAdmin = true;
                    break;
                }
            }
            System.out.println("isAdmin ===== " + isAdmin);
            if (isAdmin) {
                session.setAttribute(Constants.IS_ADMIN,true);
                message = "Successfully logged in. Welcome admin.";
                action  = "/admin/index";
            } else {
                session.setAttribute(Constants.IS_ADMIN,false);
                message = "Successfully logged in. Welcome user";
                action  = "/user/index";
            }

            //log the event
            loginService.setLog(user, "LOGGED_IN");

        } else {
            message = "Wrong credentials. Please provide the correct credentials or contact your Administrator.";
            action  = "/login/form";        /*redirect to Dashboard's index action*/

        }
//        req.setAttribute("message", message);
        attributes.addFlashAttribute("message",message);
        System.out.println(" Redirecting to : " + action);
        return "redirect:/ridisearch"+action;

    }

}
