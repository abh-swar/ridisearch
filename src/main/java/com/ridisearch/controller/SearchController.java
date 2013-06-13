package com.ridisearch.controller;

import com.ridisearch.utils.Variables;
import com.sun.org.apache.xpath.internal.operations.Variable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/4/13
 * Time: 8:27 AM
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(ModelMap model, HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        boolean loggedIn = (Boolean) session.getAttribute(Variables.IS_LOGGED_IN);

        if (loggedIn) {
            //search for public and own private stuffs
        } else {
            //search for public stuffs
        }

        model.addAttribute("message", "Searched!!");
        return "search/index";
    }
}
