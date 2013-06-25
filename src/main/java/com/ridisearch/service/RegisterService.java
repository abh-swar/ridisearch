package com.ridisearch.service;

import com.ridisearch.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/20/13
 * Time: 1:09 AM
 */
public class RegisterService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean userAlreadyExists(String userName) {
        String sql = "select count(*) from user where user_name = ?";
        int userCount = 0;
        try {
            userCount = jdbcTemplate.queryForInt(sql,userName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userCount != 0;
    }

    public boolean registerUser(String userName, String password, String name) {
        String userTableSql = "INSERT INTO USER (name, user_name, password, is_registered) VALUES (?, ?, MD5(?),true)";
        int affectedRows = 0;
        try {
            affectedRows = jdbcTemplate.update(userTableSql, name, userName, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return affectedRows != 0;
    }

    /**
     * Select userId by this username and then insert a row in user_role to provide this user
     * a ROLE_USER a as role
     */
    public boolean provideUserRole(String userName) {
        String sql = "select id from user where user_name = ?";
        long userId = 0;
        long numberOfRolesInserted =0;
        try {
            userId = jdbcTemplate.queryForObject(sql, new Object[] { userName }, Long.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (userId != 0) {
            sql = "INSERT INTO USER_ROLE (user_id, role_id) values (?,2)";
            numberOfRolesInserted = jdbcTemplate.update(sql,userId);
        }
        return numberOfRolesInserted != 0;
    }
}
