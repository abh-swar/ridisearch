package com.ridisearch.controller;

import com.ridisearch.domain.User;
import com.ridisearch.service.AdminService;
import com.ridisearch.service.LoginService;
import com.ridisearch.service.UserService;
import com.ridisearch.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/18/13
 * Time: 10:44 PM
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService service;

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    private String message = "";

    @RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
    public String index() {
        return  "admin/index";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "admin/about";
    }


    @RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
    public String search(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
        return  "admin/searchResult";
    }

    @RequestMapping(value = "/addItems", method = { RequestMethod.GET, RequestMethod.POST })
    public String addItems(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
        return  "admin/searchResult";
    }

    @RequestMapping(value = "/panel", method = { RequestMethod.GET, RequestMethod.POST })
    public String showAdminPanel(ModelMap modelMap) {
        List<User> userList = userService.findAllUsers();
        userList = userList == null ? new ArrayList<User>() : userList;
        modelMap.addAttribute("userList",userList);
        return  "admin/panel";
    }

    @RequestMapping(value = "/addUser", method = { RequestMethod.GET, RequestMethod.POST })
    public String addUser() {
        return "/admin/addUser";
    }

    @RequestMapping(value = "/saveNewUser", method = { RequestMethod.GET, RequestMethod.POST })
    public String saveNewUser(HttpServletRequest req, RedirectAttributes attributes) {
        User user   = userService.newUser(req);
        if (userService.saveNewUser(user)) {
            long userId = loginService.getUser(user.getUserName(),user.getPassword()).getId();
            String[] roles = req.getParameterValues("roles");
            for (String role : roles) {
                if (role.equals(Constants.ROLE_ADMIN)) {
                    userService.saveUserRole(Constants.ROLE_ADMIN_ID, userId);
                } else if (role.equals(Constants.ROLE_USER)) {
                    userService.saveUserRole(Constants.ROLE_USER_ID, userId);
                }
            }
            message = "User successfully added";
        } else {
            message = "User could not be added. Check log for details!";
        }
        attributes.addFlashAttribute("message", message);
        return "redirect:/ridisearch/admin/panel";
    }



    @RequestMapping(value = "/deleteUser", method = { RequestMethod.GET, RequestMethod.POST })
    public String deleteUser(HttpServletRequest req, RedirectAttributes attributes) {
        long userId = req.getParameter("id")!= null ? Long.parseLong(req.getParameter("id")) : 0;
        if (userId != 0 && userService.deleteUser(userId)) {
            message = "User successfully deleted";
        } else {
            message = "User could not be deleted. Check log for details!";
        }
        attributes.addFlashAttribute("message", message) ;
        return "redirect:/ridisearch/admin/panel";
    }

    @RequestMapping(value = "/edit", method = { RequestMethod.GET, RequestMethod.POST })
    public String edit(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
        long userId     = Long.parseLong(req.getParameter("id"));
        User user       = userService.getUser(userId);

        if (user == null) {
            message = "No user found. Please try again!";
            req.getSession().setAttribute("message",message);
        } else {
            modelMap.addAttribute("user",user);
        }
        return  "admin/editProfile";
    }

    @RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
    public String save(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
        String userName      = req.getParameter("userName")!= null ? req.getParameter("userName") : "";
        String fullName      = req.getParameter("name")!= null ? req.getParameter("name") : "";
        String address       = req.getParameter("address")!= null ? req.getParameter("address") : "";
        String phoneNumber   = req.getParameter("phoneNumber")!= null ? req.getParameter("phoneNumber") : "";
        long userId          = req.getParameter("id")!= null ? Long.parseLong(req.getParameter("id")) : 0;

        if (userService.saveEditedUser(userName,fullName,userId,address,phoneNumber)) {
            message = "User information was successfully edited";
        }  else {
            message = "Could not edit user information. Check the log for details.";
        }
        modelMap.addAttribute("message",message);

        return  "admin/index";
    }



    @RequestMapping(value = "/profile", method = { RequestMethod.GET, RequestMethod.POST })
    public String profile(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
        User user = userService.getUser(Long.parseLong(req.getSession().getAttribute(Constants.USER_ID).toString()));
        modelMap.addAttribute("user",user);
        return  "admin/profile";
    }


}
