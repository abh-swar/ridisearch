package com.ridisearch.controller;

import com.ridisearch.domain.SearchHits;
import com.ridisearch.service.LuceneSearchService;
import com.ridisearch.service.SearchService;
import com.ridisearch.utils.Constants;
import com.ridisearch.utils.UploadDownloadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/4/13
 * Time: 8:27 AM
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    LuceneSearchService luceneSearchService;


    @RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
    public String index(ModelMap model, HttpServletRequest req, HttpServletResponse res) {
        //this is just for genereal search for public stuffs

        String query = req.getParameter("query");
        List<SearchHits> listOfHits = new ArrayList<SearchHits>();

        try {
            luceneSearchService.searchIndex(query, listOfHits);
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("listOfHits", listOfHits);
        req.setAttribute("hitsCount", listOfHits.size());

        return "search/generealSearch";
    }


}
