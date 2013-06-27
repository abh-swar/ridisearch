package com.ridisearch.controller;

import com.ridisearch.domain.Items;
import com.ridisearch.service.SearchService;
import com.ridisearch.utils.UploadDownloadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
        List<Items> itemList = searchService.findSixPublicItems();
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

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException {
//        long itemId = Long.parseLong(req.getParameter("id"));
//
//        Map<String,String> itemMap  = searchService.getPublicItemDetails(itemId);
//        byte[] itemBytes            = searchService.downloadPublicFile(itemId);
//
//        UploadDownloadUtils.download(itemMap, itemBytes, res);
        searchService.commonDownloadUtil(req,res);

        return "dashboard/index";
    }


}
