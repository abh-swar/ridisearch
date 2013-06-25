package com.ridisearch.controller;

import com.ridisearch.domain.Items;
import com.ridisearch.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/4/13
 * Time: 7:48 AM
 */
@Controller
public class DashboardController {
    @Autowired
    SearchService searchService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        List<Items> itemList = searchService.findSixPrivateItems();
        request.setAttribute("itemList",itemList);

		return "dashboard/index";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "dashboard/about";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact() {
        return "dashboard/contact";
    }
}
