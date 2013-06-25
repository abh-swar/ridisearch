package com.ridisearch.service;

import com.ridisearch.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 5/23/13
 * Time: 9:21 AM
 */
public class UserService {
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


    public User getUser(long id) {
        String sql = "select * from user where id=? and is_registered=true";
        User user;
        try {
            user =(User)jdbcTemplate
                    .queryForObject(sql, new Object[] {id}, new BeanPropertyRowMapper(User.class));
        } catch (Exception ex) {
            ex.printStackTrace();
            user = null;
        }
        return user;
    }

    public boolean saveEditedUser(String userName, String name, long id, String address, String phoneNumber) {
        String userTableSql = "UPDATE USER SET name=?, user_name=?, address=?, phone_number=? WHERE id=?";
        int affectedRows = 0;
        try {
            if (id == 0) throw new Exception("Id cannot be 0");
            affectedRows = jdbcTemplate.update(userTableSql, name, userName, address,phoneNumber,id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return affectedRows != 0;
    }

    public List<User> findAllUsers() {
        String sql  = "SELECT * FROM USER";
        List<User> userList;
        try {
            userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
        } catch (Exception ex) {
           ex.printStackTrace();
           userList = null;
        }
        return userList;
    }

    public boolean saveNewUser(User user) {
        String userTableSql = "INSERT INTO USER (name, user_name, address, phone_number, is_registered, password) VALUES (?, ?, ?, ?, true, MD5(?))";
        int affectedRows = 0;
        try {
            if (!userAlreadyExists(user.getUserName())) {
                affectedRows = jdbcTemplate.update(userTableSql, new Object[]{user.getName(), user.getUserName(), user.getAddress(), user.getPhoneNumber(), user.getPassword()});
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return affectedRows != 0;
    }

    public boolean deleteUser(long id) {
        String sql = "DELETE FROM user WHERE id = ? LIMIT 1";
        int affectedRows = 0;
        try {
            if (deleteRoles(id))  {
                affectedRows = jdbcTemplate.update(sql,id);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return affectedRows != 0;
    }

    private boolean deleteRoles(long id) {
        String sql = "DELETE FROM user_role WHERE user_id = ?";
        int affectedRows = 0;
        try {
            affectedRows = jdbcTemplate.update(sql,id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return affectedRows != 0;
    }


    public boolean saveUserRole(Long roleId, Long userId) {
        boolean success = false;
        String sql = "INSERT INTO USER_ROLE (user_id, role_id) values (?,?)";
        try {
            jdbcTemplate.update(sql,userId,roleId);
            success = true;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        return success;
    }


    public User newUser(HttpServletRequest req) {
        String userName      = req.getParameter("userName")!= null ? req.getParameter("userName") : "";
        String fullName      = req.getParameter("name")!= null ? req.getParameter("name") : "";
        String address       = req.getParameter("address")!= null ? req.getParameter("address") : "";
        String phoneNumber   = req.getParameter("phoneNumber")!= null ? req.getParameter("phoneNumber") : "";
        String password      = req.getParameter("password")!= null ? req.getParameter("password") : userName+"!@#$";

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setName(fullName);
        user.setAddress(address);
        user.setPhoneNumber(phoneNumber);

        return user;

    }
}
