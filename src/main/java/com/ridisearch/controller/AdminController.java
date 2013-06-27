package com.ridisearch.controller;

import com.ridisearch.domain.Items;
import com.ridisearch.domain.MultipartFileUploadBean;
import com.ridisearch.domain.SearchHits;
import com.ridisearch.domain.User;
import com.ridisearch.service.*;
import com.ridisearch.utils.Constants;
import com.ridisearch.utils.UploadDownloadUtils;
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

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/18/13
 * Time: 10:44 PM
 */
@MultipartConfig
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService service;

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @Autowired
    SearchService searchService;

    @Autowired
    LuceneIndexService luceneIndexService;

    @Autowired
    LuceneSearchService luceneSearchService;

    private String message = "";



    @RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
    public String index(HttpServletRequest request) {
        long userId = (Long) request.getSession().getAttribute(Constants.USER_ID);
        List<Items> itemList = searchService.findItemsForUser(userId);
        request.setAttribute("itemList",itemList);
        return  "admin/index";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "admin/about";
    }


    @RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
    public String search(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) {
        String query = req.getParameter("query");
        List<SearchHits> listOfHits = new ArrayList<SearchHits>();

        try {
            luceneSearchService.searchIndex(query, listOfHits);
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("listOfHits", listOfHits);
        req.setAttribute("hitsCount", listOfHits.size());

        return "admin/searchResult";
    }

    @RequestMapping(value = "/addItems", method = { RequestMethod.GET, RequestMethod.POST })
    public String addItems() {
        return  "admin/addItem";
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
                String content  = sb.toString();

                service.populateItem(req,items, multipartFile, access);

                if (!service.itemAlreadyExistsForUser(items) && service.saveItem(items)) {
                    message             = "File successfully uploaded";
                    message             = luceneIndexService.index(items,multipartFile,content);

                } else {
                    message = "Could not upload file. You probably already have a file by this name.";
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
        return "redirect:/ridisearch/admin/addItems";

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

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException {

        long itemId     = Long.parseLong(req.getParameter("id"));
        long userId     = (Long) req.getSession().getAttribute(Constants.USER_ID);
        Map<String,String> itemMap  = searchService.getUserItemDetails(itemId, userId);
        byte[] itemBytes            = searchService.downloadUserFile(itemId, userId);

        UploadDownloadUtils.download(itemMap, itemBytes, res);
        return "admin/index";
    }

    @RequestMapping(value = "/updateItem", method = { RequestMethod.GET, RequestMethod.POST })
    public String updateItem(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException {

        Long itemId     = Long.parseLong(req.getParameter("id"));
        long userId     = (Long) req.getSession().getAttribute(Constants.USER_ID);

        Items item  = searchService.getItem(itemId, userId);
        req.setAttribute("item",item);
        return "admin/updateItem";
    }

    @RequestMapping(value = "/saveUpdatedItem",method = { RequestMethod.GET, RequestMethod.POST })
    public String saveUpdatedItem(HttpServletRequest req, RedirectAttributes attributes) throws SQLException, IOException {

        Long itemId         = Long.parseLong(req.getParameter("id"));
        String itemName     = req.getParameter("itemName") != null ? req.getParameter("itemName") : "file.txt";
        boolean isPrivate   = req.getParameter("access").equals("private");

        if (searchService.saveUpdatedItem(itemId,itemName,isPrivate)) {
            message = "Item successfully updated!";
        } else {
            message = "Item could not be updated!";
        }

        attributes.addFlashAttribute("message",message);
        return "redirect:/ridisearch/admin/index";
    }


    @RequestMapping(value = "/deleteItem",method = { RequestMethod.GET, RequestMethod.POST })
    public String deleteItem(HttpServletRequest req, RedirectAttributes attributes) throws SQLException, IOException {

        Long itemId       = Long.parseLong(req.getParameter("id"));
        Boolean isPrivate = req.getParameter("private").equals("true");

        //if public delete from lucene index
        if (!isPrivate) {
            luceneIndexService.deleteLuceneIndex(itemId.toString());
        }

        if (searchService.deleteItem(itemId)) {
            message = "Item successfully deleted!";

        } else {
            message = "Item could not be deleted!";
        }

        attributes.addFlashAttribute("message",message);
        return "redirect:/ridisearch/admin/index";
    }

}
