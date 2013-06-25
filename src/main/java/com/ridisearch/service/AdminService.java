package com.ridisearch.service;

import com.ridisearch.domain.Items;
import com.ridisearch.domain.User;
import com.ridisearch.utils.Constants;
import com.ridisearch.utils.UploadDownloadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/18/13
 * Time: 10:44 PM
 */
public class AdminService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;

    public boolean saveItem(Items items) {
        String userTableSql = "INSERT INTO ITEMS (item_name, stored_location, item_type, is_private, user_id, file) VALUES " +
                              "(?, ?, ?, ?, ?, ?)";
        int affectedRows = 0;
        try {
            affectedRows = jdbcTemplate.update(userTableSql, items.getItemName(), items.getStoredLocation(),
                            items.getItemType(), items.getIsPrivate(), items.getUser().getId(),
                            new ByteArrayInputStream(items.getFile())
                            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return affectedRows != 0;
    }

    public void populateItem(HttpServletRequest req, Items items, MultipartFile multipartFile, String access) throws IOException {
        String uploadPath = "";
        byte[] zippedFile = UploadDownloadUtils.zipBytes(multipartFile.getOriginalFilename(), multipartFile.getBytes());
        User user = userService.getUser(Long.parseLong(req.getSession().getAttribute(Constants.USER_ID).toString()));

        //create item object and save item in DB
        items.setIsPrivate(isPrivate(access));
        items.setUser(user);
        items.setItemName(multipartFile.getOriginalFilename());
        items.setItemType(multipartFile.getContentType());

//      Blob blob = new SerialBlob(zippedFile);
//      items.setFile(blob);

        items.setFile(zippedFile);
        uploadPath = UploadDownloadUtils.getUploadPath(access);

        //if is private item append /userName at the back
        uploadPath = isPrivate(access) ? uploadPath+ File.separator+user.getUserName() : uploadPath;
        items.setStoredLocation(uploadPath);
    }

    public boolean isPrivate(String access) {
        return "private".equals(access) && !"public".equals(access);
    }
}
