package com.ridisearch.service;

import com.ridisearch.domain.Items;
import com.ridisearch.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
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

    public List<Items> findSixPrivateItems() {
        String sql              = "SELECT * FROM items WHERE is_private=false LIMIT 6";
        List<Items> itemList    = new ArrayList<Items>();

        List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Items items = new Items();
            long userId = ((Integer) (row.get("user_id")));
            User user   = userService.getUser(userId);

            items.setUser(user);
            items.setItemName((String) row.get("item_name"));
            items.setStoredLocation((String) row.get("stored_location"));
            items.setItemType((String) row.get("item_type"));
            items.setIsPrivate((Boolean) row.get("is_private"));

            itemList.add(items);
        }
        return itemList;
    }
}
