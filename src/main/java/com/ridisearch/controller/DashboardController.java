package com.ridisearch.controller;

import com.ridisearch.domain.Items;
import com.ridisearch.domain.SearchHits;
import com.ridisearch.service.LuceneSearchService;
import com.ridisearch.service.SearchService;
import com.ridisearch.utils.UploadDownloadUtils;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Autowired
    LuceneSearchService luceneSearchService;


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
        searchService.commonDownloadUtil(req,res);
        return "dashboard/index";
    }


    @RequestMapping(value = "/autoComplete",method = RequestMethod.GET)
    public @ResponseBody String autoComplete(HttpServletRequest req) throws SQLException, IOException {
        System.out.println("In autoComplete...");
        String query = req.getParameter("query");
        List<SearchHits> searchHitsList =  new ArrayList<SearchHits>();
        try {
            luceneSearchService.searchIndex(query,searchHitsList);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String searchHits = "";
        for (SearchHits hits : searchHitsList) {
            searchHits = searchHits + hits.getFileName() + ",";
        }

        searchHits = searchHits.substring(0, searchHits.length() - 1);
        System.out.println("searchHits = " + searchHits);
        return searchHits;
    }


}
