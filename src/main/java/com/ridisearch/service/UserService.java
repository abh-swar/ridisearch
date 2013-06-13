package com.ridisearch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: Abhinayak Swar
 * Date: 5/23/13
 * Time: 9:21 AM
 */
public class UserService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void testFunction() {
        if (jdbcTemplate.queryForList("select * from student").size() > 0) {
            System.out.println("Returned list :: " + jdbcTemplate.queryForList("select * from student").size());
        } else System.out.println("Failed!!");
    }
}
