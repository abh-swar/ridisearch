package com.ridisearch.service;

import com.ridisearch.domain.Items;
import com.ridisearch.domain.User;
import com.ridisearch.utils.UploadDownloadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/26/13
 * Time: 1:54 AM
 */
public class SearchService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    private LobHandler lobHandler = new DefaultLobHandler();


    public List<Items> findSixPublicItems() {
        String sql              = "SELECT * FROM items WHERE is_private=false LIMIT 6";
        List<Items> itemList    = new ArrayList<Items>();

        List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Items items = new Items();
            long userId = ((Integer) (row.get("user_id")));
            User user   = userService.getUser(userId);

            items.setUser(user);
            Integer uId = (Integer) row.get("id");
            items.setId(Long.parseLong(uId.toString()));
            items.setItemName((String) row.get("item_name"));
            items.setStoredLocation((String) row.get("stored_location"));
            items.setItemType((String) row.get("item_type"));
            items.setIsPrivate((Boolean) row.get("is_private"));

            itemList.add(items);
        }
        return itemList;
    }

    public List<Items> findItemsForUser(long id) {
        String sql              = "SELECT * FROM items WHERE user_id=? ORDER BY id DESC";
        List<Items> itemList    = new ArrayList<Items>();

        List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql,id);
        for (Map row : rows) {
            Items items = new Items();
            long userId = ((Integer) (row.get("user_id")));
            User user   = userService.getUser(userId);

            items.setUser(user);
            Integer uId = (Integer) row.get("id");
            items.setId(Long.parseLong(uId.toString()));
            items.setItemName((String) row.get("item_name"));
            items.setStoredLocation((String) row.get("stored_location"));
            items.setItemType((String) row.get("item_type"));
            items.setIsPrivate((Boolean) row.get("is_private"));

            itemList.add(items);
        }
        return itemList;
    }

    public byte[] downloadPublicFile(long itemId) {
        String sql = "SELECT file FROM items WHERE id=? and is_private=false";

        List<Map<String,byte[]>> list = jdbcTemplate.query(sql,
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        Map results = new HashMap();
                        byte[] blobBytes = lobHandler.getBlobAsBytes(rs, "file");
                        results.put("BLOB", blobBytes);
                        return results;
                    }
                },
                itemId);

        System.out.println("list.size() = " + list.size());
        System.out.println("BLOB \n\n = " + list.get(0).get("BLOB"));

        return list.get(0).get("BLOB");

    }

    public byte[] downloadUserFile(long itemId, long userId) {
        String sql = "SELECT file FROM items WHERE id=? and user_id=?";

        List<Map<String,byte[]>> list = jdbcTemplate.query(sql,
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        Map results = new HashMap();
                        byte[] blobBytes = lobHandler.getBlobAsBytes(rs, "file");
                        results.put("BLOB", blobBytes);
                        return results;
                    }
                },
                itemId,userId);

        System.out.println("list.size() = " + list.size());
        System.out.println("BLOB \n\n = " + list.get(0).get("BLOB"));

        return list.get(0).get("BLOB");

    }

    public Map<String, String> getPublicItemDetails(long itemId) {
        String sql = "select item_type, item_name from items where id=?";
        Map<String, String> contentMap  = new HashMap<String, String>();
        List<Map<String,Object>> rows   = jdbcTemplate.queryForList(sql,itemId);
        for (Map row : rows) {
            contentMap.put("itemName", (String) row.get("item_name"));
            contentMap.put("itemType", (String) row.get("item_type"));
        }
        return contentMap;
    }

    public Map<String, String> getUserItemDetails(long itemId, long userId) {
        String sql = "select item_type, item_name from items where id=? and user_id=?";
        Map<String, String> contentMap  = new HashMap<String, String>();
        List<Map<String,Object>> rows   = jdbcTemplate.queryForList(sql,itemId,userId);
        for (Map row : rows) {
            contentMap.put("itemName", (String) row.get("item_name"));
            contentMap.put("itemType", (String) row.get("item_type"));
        }
        return contentMap;
    }

    public Items getItem(long itemId, long userId) {
        String sql = "select item_type,item_name,is_private,user_id from items where id=? and user_id=?";
        Map<String, String> contentMap  = new HashMap<String, String>();
        List<Map<String,Object>> rows   = jdbcTemplate.queryForList(sql,itemId,userId);
        Items item = new Items();
        for (Map row : rows) {
            User user   = userService.getUser(userId);

            item.setUser(user);
            item.setItemName((String) row.get("item_name"));
            item.setItemType((String) row.get("item_type"));
            item.setId(itemId);
            item.setIsPrivate((Boolean) row.get("is_private"));
        }
        //returns only one item since id is the primary key and there can only be one row
        return item;
    }

    public boolean saveUpdatedItem(Long itemId, String itemName, boolean isPrivate) {
        String userTableSql = "UPDATE items SET item_name=?, is_private=? WHERE id=?";
        int affectedRows = 0;
        try {
            affectedRows = jdbcTemplate.update(userTableSql, itemName,isPrivate,itemId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return affectedRows != 0;

    }

    public boolean deleteItem(Long itemId) {
        String userTableSql = "DELETE FROM items WHERE id=? LIMIT 1";
        int affectedRows = 0;
        try {
            affectedRows = jdbcTemplate.update(userTableSql,itemId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return affectedRows != 0;

    }



    public void commonDownloadUtil(HttpServletRequest req, HttpServletResponse res) throws IOException, SQLException {
        try {
            long itemId = Long.parseLong(req.getParameter("id"));

            Map<String,String> itemMap  = getPublicItemDetails(itemId);
            byte[] itemBytes            = downloadPublicFile(itemId);

            UploadDownloadUtils.download(itemMap, itemBytes, res);

        }  catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
