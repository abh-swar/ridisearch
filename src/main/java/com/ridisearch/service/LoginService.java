package com.ridisearch.service;

import com.ridisearch.domain.Role;
import com.ridisearch.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/4/13
 * Time: 8:53 AM
 */
public class LoginService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private List<Role> roleList;
    private List<User> userList = new ArrayList<User>();


    public User getUser(String userName, String password) {
        String sql = "select * from user where user_name = ? and password = MD5(?) and is_registered=true";
        User user;
        try {
            user =(User)jdbcTemplate
                    .queryForObject(sql, new Object[] {userName, password}, new BeanPropertyRowMapper(User.class));
        } catch (Exception ex) {
           ex.printStackTrace();
           user = null;
        }
        return user;
    }

    public List<Role> getRoles(Long userId) {
        String sql = "select role_name from role where id in (select role_id from user_role where user_id=?);";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, userId);
            roleList = new ArrayList<Role>();
            for (Map<String, Object> row : rows) {
                Role role =  new Role();
                role.setRoleName((String) row.get("role_name"));
                System.out.println("role = " + role.getId() + " " + role.getRoleName());
                roleList.add(role);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return roleList;
    }

    public void setLog(User user, String action) {
        String sql = "INSERT INTO user_log (user_name, name, user_id, action) VALUES (?,?,?,?)";
        try {
            jdbcTemplate.update(sql, user.getUserName(), user.getName(), user.getId(), action);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
