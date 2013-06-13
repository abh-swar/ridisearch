package com.ridisearch.service;

import com.ridisearch.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 6/4/13
 * Time: 8:53 AM
 */
public class LoginService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private List<String> roleList = new ArrayList<String>();
    private List<User> userList = new ArrayList<User>();


    public User getUser(String userName, String password) {
        String sql = "select * from user where user_name = ? and password = MD5(?)";
        User user =(User)jdbcTemplate
                    .queryForObject(sql, new Object[] {userName, password}, new BeanPropertyRowMapper(User.class));
        return user;
    }

    public List<String> getRoles(int userId) {
        String sql = "select role_name from role where id in (select role_id from user_role where user_id=?);";


        if (jdbcTemplate.queryForList("select * from student").size() > 0) {
            System.out.println("Returned list :: " + jdbcTemplate.queryForList("select * from student").size());
        } else System.out.println("Failed!!");

        return roleList;
    }
}
