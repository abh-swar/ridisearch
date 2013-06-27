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
import java.io.InputStream;

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
        System.out.println("affectedRows = " + affectedRows);
        return affectedRows != 0;
    }


    public long getItemId(Items items) {
        String sql = "SELECT id FROM ITEMS where item_name=? and stored_location=? and item_type=? and is_private=? and user_id=? LIMIT 1";
        long userId = 0;
        try {
            userId = jdbcTemplate.queryForLong(sql, items.getItemName(), items.getStoredLocation(),
                    items.getItemType(), items.getIsPrivate(), items.getUser().getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userId;

    }

    public void populateItem(HttpServletRequest req, Items items, MultipartFile multipartFile, String access) throws IOException {
        String uploadPath = "";
        byte[] zippedFile = UploadDownloadUtils.zipBytes(multipartFile.getOriginalFilename(), multipartFile.getBytes());
        User user = userService.getUser(Long.parseLong(req.getSession().getAttribute(Constants.USER_ID).toString()));
        System.out.println("user ====>>>> " + user.getId());
        //create item object and save item in DB
        items.setIsPrivate(isPrivate(access));
        items.setUser(user);
        items.setItemName(multipartFile.getOriginalFilename());
        items.setItemType(multipartFile.getContentType());

//      Blob blob = new SerialBlob(zippedFile);
//      items.setFile(blob);

//        items.setFile(zippedFile);
        items.setFile(multipartFile.getBytes());
        uploadPath = UploadDownloadUtils.getUploadPath(access);

        System.out.println("access = " + access);
        //if is private item append /userName at the back
        uploadPath = isPrivate(access) ? uploadPath+ File.separator+user.getUserName()+File.separator +items.getItemName()
                     : uploadPath;
        items.setStoredLocation(uploadPath);
    }

    public boolean isPrivate(String access) {
        return ("private".equals(access) && !"public".equals(access));
    }

    public boolean itemAlreadyExistsForUser(Items items) {
        String sql = "SELECT count(*) FROM ITEMS where item_name=? and stored_location=? and item_type=? and is_private=? and user_id=?";
        long count = 0;
        try {
            count = jdbcTemplate.queryForLong(sql, items.getItemName(), items.getStoredLocation(),
                    items.getItemType(), items.getIsPrivate(), items.getUser().getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("count ===>> " + count);
        return count > 0;
    }
}
