package com.ridisearch.controller;

import com.ridisearch.domain.Items;
import com.ridisearch.domain.MultipartFileUploadBean;
import com.ridisearch.domain.User;
import com.ridisearch.service.AdminService;
import com.ridisearch.service.UserService;
import com.ridisearch.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

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

    @Autowired
    AdminService adminService;

    private String message;

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

    @RequestMapping(value = "/addItems", method = { RequestMethod.GET, RequestMethod.POST })
    public String addItems() {
        return  "user/addItem";
    }

    @RequestMapping(value = "/saveItem", method = RequestMethod.POST)
    public String uploadtestProcess(HttpServletRequest req, MultipartFileUploadBean file, BindingResult bindingResult,
                                    Model model, RedirectAttributes attributes) throws IOException, NoSuchAlgorithmException, SQLException {
        // binding check
        StringBuilder sb = new StringBuilder();
        List<MultipartFile> files = file.getFiles();
        Items items = new Items();
        String access = req.getParameter("access") != null ? req.getParameter("access") : "";
        System.out.println("files.size() = " + files.size());

        for(MultipartFile multipartFile :files) {
            try {

                System.out.println("multipartFile = " + multipartFile.getSize());
                sb.append(String.format("File: %s, contains: %s<br/>\n",multipartFile.getOriginalFilename(),
                        new String(multipartFile.getBytes())));

                adminService.populateItem(req,items, multipartFile, access);

                if (adminService.saveItem(items)) {
                    //index content, userid, filename, id,stored_location,item_type
                    String content = sb.toString();

//                    System.out.println("content = " + content);
                    model.addAttribute("content", content);
                    message = "File successfully uploaded";
                } else {
                    message = "Could not upload file";
                }
            } catch (MaxUploadSizeExceededException ex) {
                ex.printStackTrace();
                message = "File cannot be bigger than 15MB in size.";
            } catch (Exception ex) {
                ex.printStackTrace();
                message = "Something went wrong while uploading...";
            }

        }
        attributes.addFlashAttribute("message",message);
        return "redirect:/ridisearch/user/addItems";

    }


}
