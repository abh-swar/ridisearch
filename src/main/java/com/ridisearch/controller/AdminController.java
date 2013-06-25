package com.ridisearch.controller;

import com.ridisearch.domain.Items;
import com.ridisearch.domain.MultipartFileUploadBean;
import com.ridisearch.domain.User;
import com.ridisearch.service.AdminService;
import com.ridisearch.service.LoginService;
import com.ridisearch.service.UserService;
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
import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

                service.populateItem(req,items, multipartFile, access);

                if (service.saveItem(items)) {
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
        return "redirect:/ridisearch/admin/addItems";

    }




//    @RequestMapping(value = "/saveTextItem", method = { RequestMethod.GET, RequestMethod.POST })
//    public String saveTextItem(HttpServletRequest req, HttpServletResponse res, RedirectAttributes attributes) throws IOException {
//        // Check that we have a file upload request
//        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
//        if (isMultipart) {
//            String uploadPath = "";
//            String access = req.getParameter("access") != null ? req.getParameter("access") : "";
//
//            if ("private".equals(access)) {
//                uploadPath = Constants.privateFilePath;
//            } else if ("public".equals(access)) {
//                uploadPath = Constants.publicFilePath;
//            }
//
//            try{
//
//                Map<String,String> parameters = new HashMap<String,String>();
//
//                DiskFileItemFactory factory = new DiskFileItemFactory();
//                ServletFileUpload upload = new ServletFileUpload(factory);
////            String uploadPath = req.getSession().getServletContext().getRealPath("/songs/");
//                System.out.println("uploadPath: " + uploadPath);
//                String filename = "";
//
//
//                List<FileItem> items = upload.parseRequest(req);
//                System.out.println("items = " + items.size());
//                for(FileItem item: items){
//                    System.out.println(" IN loop..");
//                    if(item.isFormField()){
//                        System.out.println("item.getName() = " + item.getName());
//                        //build bean properties param
//                        parameters.put(item.getFieldName(), item.getString());
//                    }else {
//
//                        filename = System.currentTimeMillis()+ FilenameUtils.getName(item.getName());
//
//                        System.out.println("filename = " + filename);
//                        File f = new File(uploadPath+"/"+filename);
//                        factory.setRepository(f);
//
//                        InputStream fileContent = item.getInputStream();
//                        OutputStream out = new FileOutputStream(f);
//
//                        byte buf[] = new byte[10240];
//                        int len;
//                        while((len=fileContent.read(buf))>0){
//                            out.write(buf,0,len);
//                        }
//                        fileContent.close();
//                        out.flush();
//                        out.close();
//                    }
//                }
//
//
////            BeanUtils.populate(song, parameters);
////            song.setFileName(filename);
//
//                message = "File successfully uploaded!!";
//            }catch(Exception e){
//                message = "File could not be properly uploaded!!";
//                e.printStackTrace();
//            }
//
////        try {
//////            UploadDownloadUtils.uploadFile(req,path);
////
////            String fileName = null;
////            try{
//////                HttpServletRequestWrapper request = new HttpServletRequestWrapper(req);
////                for (Part part : req.getParts()) {
////                    InputStream is = req.getPart(part.getName()).getInputStream();
////                    String temp = ((ApplicationPart)part).getFilename();
////                    if(temp==null || temp.trim().length()==0){
////                        return fileName;
////                    }
//////                fileName = MD5helper.getMD5Hash(String.valueOf(System.currentTimeMillis())) + "." + FileUtility.getExtensionFile(new File(temp));
////                    fileName = temp+System.currentTimeMillis();
////                    File newPath = new File(filePath);
////
////                    if (!newPath.exists()) {
////                        newPath.mkdirs();
////                    }
////                    File uploadedFile = new File(path + File.separator + fileName);
////                    FileOutputStream os = new FileOutputStream(uploadedFile);
//////                FileUtility.copyFile(is, os);
//////                os.write(is.read());
////                    IOUtils.copy(is, os);
////                    System.out.println("File Successfully uploaded to " + path);
////                }
////            }catch(Exception e){
////            }
//////            return fileName;
////            message = "File successfully uploaded!!";
//////            final Part filePart     = req.getPart("file");
//////            UploadDownloadUtils.processRequest(req,res,path, filePart);
////        } catch (Exception e) {
////            message = "File could not be properly uploaded!!";
////            e.printStackTrace();
////        }
//            attributes.addFlashAttribute("message", message);
//
//        } else {
//            message = "Not a multipart request";
//        }
//        return  "redirect:/ridisearch/admin/addItems";
//
//    }

//    @RequestMapping(value = "/downloadItem", method = { RequestMethod.GET, RequestMethod.POST })
//    public String downloadItem(HttpServletRequest req, HttpServletResponse res, ModelMap modelMap) throws IOException {
//        String requestedFile = req.getPathInfo();
//        String requestedUrl = req.getServletPath();
//        String action = "";
//        if (requestedFile == null) {
//            res.sendError(HttpServletResponse.SC_NOT_FOUND, "");
//            message = "File not found";
//            return "";
//        }
//
//        File file = new File(filePath, URLDecoder.decode(requestedFile, "UTF-8"));
//        if (!file.exists()) {
//            if (requestedUrl.startsWith("/image")) {
//                file = new File(req.getServletContext().getRealPath("/") + "images/s.jpg");
//            } else {
//                res.sendError(HttpServletResponse.SC_NOT_FOUND);
//                return " ";
//            }
//        }
//
//        String contentType = req.getServletContext().getMimeType(file.getName());
//
//        if (contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        res.reset();
//        res.setBufferSize(DEFAULT_BUFFER_SIZE);
//        res.setContentType(contentType);
//        res.setHeader("Content-Length", String.valueOf(file.length()));
//        res.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
//
//        BufferedInputStream input = null;
//        BufferedOutputStream output = null;
//
//        try {
//            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
//            output = new BufferedOutputStream(res.getOutputStream(), DEFAULT_BUFFER_SIZE);
//
//            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//            int length;
//            while ((length = input.read(buffer)) > 0) {
//                output.write(buffer, 0, length);
//            }
//        } finally {
//            UploadDownloadUtils.close(output);
//            UploadDownloadUtils.close(input);
//        }
//        return "";
//    }




//    private Song buildSong(HttpServletRequest request){
//        Song song = new Song();
//        try{
//
//            Map<String,String> parameters = new HashMap<String,String>();
//
//            FileItemFactory factory = new DiskFileItemFactory();
//            ServletFileUpload upload = new ServletFileUpload(factory);
//            String uploadPath = request.getSession().getServletContext().getRealPath("/songs/");
//            System.out.println("uploadPath: " + uploadPath);
//            String filename = "";
//
//
//            List<FileItem> items = upload.parseRequest(request);
//
//            for(FileItem item: items){
//                if(item.isFormField()){
//                    //build bean properties param
//                    parameters.put(item.getFieldName(), item.getString());
//                }else {
//
//                    filename = System.currentTimeMillis()+ FilenameUtils.getName(item.getName());
//
//
//                    File f = new File(uploadPath+"/"+filename);
//
//                    InputStream fileContent = item.getInputStream();
//                    OutputStream out = new FileOutputStream(f);
//
//                    byte buf[] = new byte[1024];
//                    int len;
//                    while((len=fileContent.read(buf))>0){
//                        out.write(buf,0,len);
//                    }
//                    fileContent.close();
//                    out.flush();
//                    out.close();
//                }
//            }
//
//
////            BeanUtils.populate(song, parameters);
////            song.setFileName(filename);
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return song;
//    }

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
